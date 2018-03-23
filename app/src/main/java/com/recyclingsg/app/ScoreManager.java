package com.recyclingsg.app;

import android.util.Log;

/**
 * Created by tanliangwei on 23/3/18.
 */

public class ScoreManager {
    private static final String TAG = "ScoreManager";

    //the constructor and instance management code
    private static ScoreManager instance;
    //this ensures that there is only one instance of  ScoreManager in the whole story
    public static ScoreManager getInstance(){
        if (instance == null) {
            try {
                instance = new ScoreManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct ScoreManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }
    //constructor for database manger
    public ScoreManager(){}

    //calculating score, now hard coded to 5
    public static float calculateScore(TrashPrices trashPrice, float units){
        return 5;
    }
}
