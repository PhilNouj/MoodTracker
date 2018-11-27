package com.noujnouj.ocr.nouj.trackernouj;

import java.time.LocalDate;

public class Mood {

    private int mMoodIndex;
    private int mMoodColor;
    private int mMoodSmiley;
    private String mMoodComment;
    private LocalDate mMoodDate;
    private String mMoodDateStr;

    public Mood(int moodIndex, int moodColor, int moodSmiley) {
        this.mMoodIndex = moodIndex;
        this.mMoodColor = moodColor;
        this.mMoodSmiley = moodSmiley;
    }


    public String getMoodDateStr() {
        return mMoodDateStr;
    }

    public void setMoodDate(LocalDate moodDate) {
        this.mMoodDate = moodDate;
    }

    public void setMoodDateStr(String moodDateStr) {
        this.mMoodDateStr = moodDateStr;
    }

    public String getMoodComment() {
        return mMoodComment;
    }

    public void setMoodComment(String moodComment) {
        mMoodComment = moodComment;
    }

    public int getMoodIndex() {
        return mMoodIndex;
    }

    public int getMoodColor() {
        return mMoodColor;
    }

    public int getMoodSmiley() {
        return mMoodSmiley;
    }

}
