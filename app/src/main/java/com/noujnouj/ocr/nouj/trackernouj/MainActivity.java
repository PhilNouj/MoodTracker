package com.noujnouj.ocr.nouj.trackernouj;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Calendar;
import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC_WAKEUP;

public class MainActivity extends AppCompatActivity {

    Context c = this;
    public static final String TAG = MainActivity.class.getSimpleName();

    private ViewGroup mMainLayout;
    private ImageView mCurrentBackground;
    private ImageView mCurrentSmiley;
    protected int mCurrentMoodIndex;
    private String mMoodComment;

    private ArrayList<Mood> mMoodList;

    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        set the default mood index to Happy (3)
        */
        mCurrentMoodIndex = 3;

        setTheTimeToUpdateTables();

        //mMainLayout = (ViewGroup) ((ViewGroup) this .findViewById(android.R.id.content)).getChildAt(0);
        mCurrentBackground = findViewById(R.id.activity_main_current_background);
        mCurrentSmiley = findViewById(R.id.activity_main_current_smiley);
        Button messageBtn = findViewById(R.id.activity_main_message_btn);
        Button historyBtn = findViewById(R.id.activity_main_history_btn);

        initMoodList();

        mCurrentBackground.setBackgroundColor(getResources().getColor(mMoodList.get(mCurrentMoodIndex).getMoodColor())); // Display XML elements related to the latest mood index (default or selected by the user)
        mCurrentSmiley.setImageDrawable(getResources().getDrawable(mMoodList.get(mCurrentMoodIndex).getMoodSmiley()));
        // mMainLayout.setBackground(getResources().getDrawable(mMoodList.get(mCurrentMoodIndex).getMoodSmiley()));

        /*
        On swipe implementation
        */
        mCurrentBackground.setOnTouchListener(new OnSwipeTouchListener(this) {

            SoundsUtil soundsUtil = new SoundsUtil(c);

            public void onSwipeTop() {
                incrementMoodIndex();
                soundsUtil.playSound(mCurrentMoodIndex);
                mCurrentBackground.setBackgroundColor(getResources().getColor(mMoodList.get(mCurrentMoodIndex).getMoodColor()));
                mCurrentSmiley.setImageDrawable(getResources().getDrawable(mMoodList.get(mCurrentMoodIndex).getMoodSmiley()));
            }
            public void onSwipeRight() {
            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
                decrementMoodIndex();
                soundsUtil.playSound(mCurrentMoodIndex);
                mCurrentBackground.setBackgroundColor(getResources().getColor(mMoodList.get(mCurrentMoodIndex).getMoodColor()));
                mCurrentSmiley.setImageDrawable(getResources().getDrawable(mMoodList.get(mCurrentMoodIndex).getMoodSmiley()));
            }
        });

        /*
        On click listener for optional daily comments - Dialog box
        */
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog();
            }
        });

        /*
        On click listener for History activity with display of previous moods (Recycler View)
        */
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStop() {
        super.onStop();
        mMoodList.get(mCurrentMoodIndex).setMoodComment(mMoodComment);
        SaveMoodHelper smh = new SaveMoodHelper(Prefs.getInstance(this), mMoodList.get(mCurrentMoodIndex));
        smh.saveMood();
        mMoodComment = null;
    }

    /* Create 5 moods
    @param moodIndex The mood index number
    @param moodColor The mood color
    @param moodSmiley The mood smiley
     */
    public void initMoodList() {
        Mood mood1 = new Mood(0, R.color.disappointed_faded_red, R.drawable.smiley_disappointed);
        Mood mood2 = new Mood(1, R.color.sad_warm_grey, R.drawable.smiley_sad);
        Mood mood3 = new Mood(2, R.color.normal_cornflower_blue_65, R.drawable.smiley_normal);
        Mood mood4 = new Mood(3, R.color.happy_light_sage, R.drawable.smiley_happy);
        Mood mood5 = new Mood(4, R.color.super_happy_banana_yellow, R.drawable.smiley_super_happy);

        mMoodList = new ArrayList<>();
        mMoodList.add(mood1);
        mMoodList.add(mood2);
        mMoodList.add(mood3);
        mMoodList.add(mood4);
        mMoodList.add(mood5);
    }

    /*
    OnSwipeUp Increment mood index until 4
    @param mCurrentMoodIndex The current mood index
     */
    private void incrementMoodIndex() {
        if (mCurrentMoodIndex < 4) { ++mCurrentMoodIndex; }
    }

    /*
    OnSwipeDown Decrement mood index until -1
    @param mCurrentMoodIndex The current mood index
     */
    private void decrementMoodIndex() {
        if (mCurrentMoodIndex > 0) { --mCurrentMoodIndex; }
    }

    /*
    Display the dialog box for optional daily comments
     */
    public void showCommentDialog () {
        final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Commentaire")
                    .setView(taskEditText)
                    .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           mMoodComment = taskEditText.getText().toString();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //cancel
                        }
                    })
                    .create();
            dialog.show();
    }

    /*
    Alarm Manager - Store default mood at midnight
    */
    private void setTheTimeToUpdateTables() {
        Log.i("Update table function", "Yes");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, RecurringTasks.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 0);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(RTC_WAKEUP, alarmStartTime.getTimeInMillis(), INTERVAL_DAY, pendingIntent);
        Log.d("Alarm", "Set for midnight");
    }
}


