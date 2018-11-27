package com.noujnouj.ocr.nouj.trackernouj;

import android.content.Context;
import android.media.MediaPlayer;

class SoundsUtil {

    private Context mCtx;

    SoundsUtil(Context ctx) {
        this.mCtx = ctx;
    }

    /*
    Automatic sound play depending on mood index
    @param mCurrentMoodIndex The selected mood index
     */

    void playSound(int mCurrentMoodIndex) {
        switch (mCurrentMoodIndex) {
            case 0 :
                MediaPlayer mdp = MediaPlayer.create(mCtx,R.raw.note_do);
                mdp.start();
                break;
            case 1 :
                MediaPlayer mdp1= MediaPlayer.create(mCtx,R.raw.note_re);
                mdp1.start();
                break;
            case 2 :
                MediaPlayer mdp2= MediaPlayer.create(mCtx,R.raw.note_mi);
                mdp2.start();
                break;
            case 3 :
                MediaPlayer mdp3= MediaPlayer.create(mCtx,R.raw.note_fa);
                mdp3.start();
                break;
            case 4 :
                MediaPlayer mdp4= MediaPlayer.create(mCtx,R.raw.note_sol);
                mdp4.start();
                break;
        }
    }
}
