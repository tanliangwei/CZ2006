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
    private static Date lastUpdate = null;
    private static ArrayList<SimpleDepositLog> depositLogs = new ArrayList<>();


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
    public static void refreshData(Date begDate, Date endDate, int n_top){
        DatabaseManager.pullDepositStat(begDate, endDate, n_top);
        DatabaseManager.pullDepositLogByUserId();
    }

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



    /**
     * refresh the cached data with default parameters,
     * querying whole history and at most 10 top users
     */
    public static void refreshData(){
        DatabaseManager.pullDepositStat();
        DatabaseManager.pullDepositLogByUserId();
    }

    /**
     * get the score of the current user, make sure the user has logged in when calling this method,
     * @return the user score
     */
    public static double getUserScore(){
        if(userScore == -1){
            DatabaseManager.pullDepositStat();
        }
        if(lastUpdate==null ||  (new Date().getTime()-lastUpdate.getTime())/1000 < 10){
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
        if(lastUpdate==null ||  (new Date().getTime()-lastUpdate.getTime())/1000 < 10){
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
            DatabaseManager.pullDepositLogByUserId();
        }
        if(lastUpdate==null ||  (new Date().getTime()-lastUpdate.getTime())/1000 < 10){
            DatabaseManager.pullDepositStat();
            DatabaseManager.pullDepositLogByUserId();
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

    public static void setLastUpdate(Date lastUpdate) {
        StatisticsManager.lastUpdate = lastUpdate;
    }

    public static void setDepositLogs(ArrayList<SimpleDepositLog> depositLogs) {
        StatisticsManager.depositLogs = depositLogs;
    }

    public static ArrayList<SimpleDepositLog> getDepositLogs() {
        return depositLogs;
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
    private int ewastCount = 0;
    private int secondHandGoodCount = 0;
    private int cashForTrashCount = 0;

    public NationalStat(double avgScore, int userCount, int cashForTrashCount, int ewastCount, int secondHandGoodCount){
        this.avgScore = avgScore;
        this.userCount = userCount;
        this.cashForTrashCount = cashForTrashCount;
        this.ewastCount = ewastCount;
        this.secondHandGoodCount = secondHandGoodCount;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public int getUserCount() {
        return userCount;
    }

    public int getCashForTrashCount() {
        return cashForTrashCount;
    }

    public int getEwastCount() {
        return ewastCount;
    }

    public int getSecondHandGoodCount() {
        return secondHandGoodCount;
    }
}


class SimpleDepositLog{
    private String depositDate;
    private String trashType;
    private double score;
    private String userName;

    public SimpleDepositLog(String userName, String depositDate, String trashType, double score){
        this.userName = userName;
        this.depositDate = depositDate;
        this.score = score;
        if(trashType.equalsIgnoreCase("cash-for-trash")){
            this.trashType = "cash for trash";
        }
        else if(trashType.equalsIgnoreCase("e-waste-recycling")|| trashType.equalsIgnoreCase("e-waste")){
            this.trashType = "e-waste";
        }
        else{
            this.trashType = "2nd hand goods";
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public String getTrashType() {
        return trashType;
    }

    public double getScore() {
        return score;
    }
}