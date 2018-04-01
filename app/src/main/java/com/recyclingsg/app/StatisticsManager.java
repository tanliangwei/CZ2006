package com.recyclingsg.app;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Qu Zhe on 2018-4-1.
 */

public class StatisticsManager {
    private static ArrayList<TopUser> topUsers = null;
    private static NationalStat nationalStat = null;
    private static double userScore = -1;

    /**
     * refresh the cached data,
     * this method should be called in the following scenarios:
     *      1. before the first query
     *      2. the cached data is outdated
     *      3. the query parameter is changed
     * @param begDate the beginning time of the time window to query
     * @param endDate the ending time of the time window to query
     * @param n_top the number of top users to query
     */

    private static final String TAG = "Statistics Manager";

    //the constructor and instance management code
    private static StatisticsManager instance;
    //this ensures that there is only one instance of  DepositManager in the whole story
    public static StatisticsManager getInstance(){
        if (instance == null) {
            try {
                instance = new StatisticsManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct StatisticsManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }
    //constructor for database manger
    public StatisticsManager(){}

    public static void refreshData(Date begDate, Date endDate, int n_top){
        DatabaseManager.pullDepositStat(begDate, endDate, n_top);
    }

    /**
     * refresh the cached data with default parameters,
     * querying whole history and at most 10 top users
     */
    public static void refreshData(){
        DatabaseManager.pullDepositStat();
    }

    /**
     * get the score of the current user, make sure the user has logged in when calling this method,
     * @return the user score
     */
    public static double getUserScore(){
        if(userScore == -1){
            DatabaseManager.pullDepositStat();
        }
        return userScore;
    }

    /**
     * get the score of top users
     * @return a list of TopUser instance
     * @see TopUser
     */
    public static ArrayList<TopUser> getTopUsers() {
        if(topUsers == null){
            DatabaseManager.pullDepositStat();
        }
        return topUsers;
    }

    /**
     * get the national statistics
     * @return national statistics
     * @see NationalStat
     */
    public static NationalStat getNationalStat() {
        if(nationalStat == null){
            DatabaseManager.pullDepositStat();
        }
        return nationalStat;
    }

    public static void setNationalStat(NationalStat nationalStat) {
        StatisticsManager.nationalStat = nationalStat;
    }

    public static void setTopUsers(ArrayList<TopUser> topUsers) {
        StatisticsManager.topUsers = topUsers;
    }

    public static void setUserScore(double userScore) {
        StatisticsManager.userScore = userScore;
    }
}


class TopUser{
    private String userName;
    private double score;

    public TopUser(String userName, double score){
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public double getScore() {
        return score;
    }
}

class NationalStat{
    private double avgScore;
    // the number of depositors in the country
    private int userCount;

    public NationalStat(double avgScore, int userCount){
        this.avgScore = avgScore;
        this.userCount = userCount;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public int getUserCount() {
        return userCount;
    }
}
