package com.noujnouj.ocr.nouj.trackernouj;

import java.util.ArrayList;

public class SaveMoodHelper {

    private final Mood mood;
    private Prefs mPreferences;

    public SaveMoodHelper(Prefs preferences, Mood mood) {
        this.mPreferences = preferences;
        this.mood = mood;
    }

    /*
    Save a mood
     */
    public void saveMood() {

        DatesUtil datesUtil = new DatesUtil();
        mood.setMoodDate(datesUtil.getTodayDate());
        mood.setMoodDateStr(datesUtil.getTodayStr());

        ArrayList<Mood> historyMoods = mPreferences.getUserMoods();

        removeSameDayMood(datesUtil, historyMoods);

        historyMoods.add(mood);
        mPreferences.storeUserMoods(historyMoods);
        removeMood7(datesUtil, mood, historyMoods);
    }

    /*
    Remove the mood already saved for today if exists = Remove the last index of the ArrayList
    @param getTodayStr Today date String format
    @param getMoodDateStr Last mood date String format
     */
    private void removeSameDayMood(DatesUtil datesUtil, ArrayList<Mood> historyMoods) {
        if (historyMoods.size() > 7 && datesUtil.compareDates(datesUtil.getTodayStr(), historyMoods.get(historyMoods.size()-1).getMoodDateStr())) {
            historyMoods.remove(historyMoods.size() - 1);
        }
    }

    /*
    Remove the oldest mood if ArrayList contains at least 7 entries
    @param mood Mood to save
    @param historyMoods All saved moods
    @param getTodayStr Today date String format
    @param getMoodDateStr Last mood date String format
     */
    private void removeMood7(DatesUtil datesUtil, Mood mood, ArrayList<Mood> historyMoods) {
        if (historyMoods.size() > 7 && datesUtil.getTodayStr().equals(mood.getMoodDateStr())) {
            historyMoods.remove(0);
        }
    }

}