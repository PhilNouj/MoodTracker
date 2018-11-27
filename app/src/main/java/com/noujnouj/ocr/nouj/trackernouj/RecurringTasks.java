package com.noujnouj.ocr.nouj.trackernouj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class RecurringTasks extends BroadcastReceiver {

    /*
    Save the daily default mood at midnight
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm", "Saved auto midnight !");
        Mood mood = new Mood(3, R.color.happy_light_sage, R.drawable.smiley_happy);
        SaveMoodHelper smh = new SaveMoodHelper(Prefs.getInstance(context), mood);
        smh.saveMood();
    }
}
