package com.noujnouj.ocr.nouj.trackernouj;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.Toast;

/*
 * RecyclerView.Adapter;
 * RecyclerView.ViewHolder
 */
public class UserMoodsAdapter extends RecyclerView.Adapter<UserMoodsAdapter.UserMoodsViewHolder> {

    private static final int TEXT_VIEW_BASE_WIDTH = 100;
    private Context mCtx;
    private ArrayList<Mood> mHistoryMoods;
    private String mMoodDateStr;
    private String mComment;
    private int mDiffDate;


    protected UserMoodsAdapter(Context ctx, ArrayList<Mood> historyMoods) {
        this.mCtx = ctx;
        this.mHistoryMoods = historyMoods;
    }

    @Override
    public UserMoodsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item, null);
        return new UserMoodsViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(UserMoodsViewHolder holder, final int position) {

        final Mood userMood = mHistoryMoods.get(position);

        /*
        Set the width of the textViewColor
        */
        int moodVariation = userMood.getMoodIndex()*50;
        float density = mCtx.getResources().getDisplayMetrics().density;
        holder.textViewColor.getLayoutParams().width = (int) ((TEXT_VIEW_BASE_WIDTH  + moodVariation) * density);

        /*
        Calculate number of days between mood date and today
        Get the optional mood comment for each mood
        */
        DatesUtil datesUtil = new DatesUtil();
        mDiffDate = datesUtil.daysBetweenDates(datesUtil.convertDateStrToLocalDate(mHistoryMoods.get(position).getMoodDateStr()), datesUtil.getTodayDate());
        mComment = userMood.getMoodComment();

        /*
        Display show comment button if a comment was added to a mood. Excludes today's mood
        @param mDiffDate The number of days between two dates
        @param mComment The comment of a mood
        */
        if (mDiffDate> 0 && mComment != null && mComment.length() > 0) {
            holder.mCommentButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.mCommentButton.setVisibility(View.INVISIBLE);
        }

        /*
        Show all saved moods with period. Excludes today's mood
        @param mDiffDate The number of days between two dates
        */
        if (mDiffDate>0) {
            displayPeriod(mDiffDate);
            holder.textViewColor.setBackgroundResource(userMood.getMoodColor());
            holder.textViewDate.setText(mMoodDateStr);

            // Onclick listener - Toast for saved comment of a specific mood
            holder.mCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mComment = userMood.getMoodComment();
                    Toast.makeText(mCtx, mComment, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHistoryMoods.size();
    }

    // Show period of the different moods saved depending on their dates
    private void displayPeriod(int diffDate) {
        switch (mDiffDate) {
            case 0:
                mMoodDateStr = "Aujourd'hui";
                break;
            case 1:
                mMoodDateStr = "Hier";
                break;
            case 2:
                mMoodDateStr = "Avant-hier";
                break;
            case 3:
                mMoodDateStr = "Il y a trois jours";
                break;
            case 4:
                mMoodDateStr = "Il y a quatre jours";
                break;
            case 5:
                mMoodDateStr = "Il y a cinq jours";
                break;
            case 6:
                mMoodDateStr = "Il y a six jours";
                break;
            case 7:
                mMoodDateStr = "Il y a une semaine";
                break;
        }

    }


    class UserMoodsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewColor;
        Button mCommentButton;
        TextView textViewDate;

        private UserMoodsViewHolder(View itemView) {
            super(itemView);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            mCommentButton = itemView.findViewById(R.id.buttonViewMessage);

        }
    }
}
