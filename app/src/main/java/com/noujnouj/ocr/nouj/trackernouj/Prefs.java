package com.noujnouj.ocr.nouj.trackernouj;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Prefs {

    private static Prefs instance;
    private static String PREFS = "PREFS";
    private static String INSTALLATIONS = "INSTALLATIONS";
    private SharedPreferences prefs;

    private Prefs(Context context) {
        prefs = context.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
    }

    public static Prefs getInstance(Context context) {
        if (instance == null)
            instance = new Prefs(context);
        return instance;
    }

    public void storeUserMoods(ArrayList<Mood> moods) {
        //start writing (open the file)
        SharedPreferences.Editor editor = prefs.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(moods);
        editor.putString(INSTALLATIONS, json);
        //close the file
        editor.apply();
    }

    public ArrayList<Mood> getUserMoods() {
        Gson gson = new Gson();
        String json = prefs.getString(INSTALLATIONS, "");

        ArrayList<Mood> mood;

        if (json.length() < 1) {
            mood = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<Mood>>() {
            }.getType();
            mood = gson.fromJson(json, type);
        }
        return mood;
    }
}