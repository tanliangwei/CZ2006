package com.recyclingsg.app.control;

import com.recyclingsg.app.entity.NationalStat;
import com.recyclingsg.app.entity.SimpleDepositLog;
import com.recyclingsg.app.entity.TopUser;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Qu Zhe on 2018-4-1.
 */

public class StatisticsManager {


    private ArrayList<TopUser> topUsers = null;
    private NationalStat nationalStat = null;
    private double userScore = -1;
    private Date lastUpdate = null;
    private ArrayList<SimpleDepositLog> depositLogs = new ArrayList<>();
    private DatabaseInterface databaseManager;

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
    public void refreshData(Date begDate, Date endDate, int n_top){
        databaseManager.pullDepositStat(begDate, endDate, n_top);
        databaseManager.pullDepositLogByUserId();
    }

    private static final String TAG = "Statistics Manager";

    //the constructor and instance management code
    private static final StatisticsManager instance = new StatisticsManager();
    //this ensures that there is only one instance of StatisticsManager in the whole story
    public static StatisticsManager getInstance(){
        return instance;
    }
    //constructor for Statistics manager
    private StatisticsManager() {
    }

    public void addDatabaseInterface(DatabaseInterface db){
        this.databaseManager = db;
    }





    /**
     * refresh the cached data with default parameters,
     * querying whole history and at most 10 top users
     */
    public void refreshData(){
        databaseManager.pullDepositStat();
        databaseManager.pullDepositLogByUserId();
    }

    /**
     * get the score of the current user, make sure the user has logged in when calling this method,
     * @return the user score
     */
    public double getUserScore(){
        if(userScore == -1){
            databaseManager.pullDepositStat();
        }
        if(lastUpdate==null ||  (new Date().getTime()-lastUpdate.getTime())/1000 < 10){
            databaseManager.pullDepositStat();
        }
        return userScore;
    }

    /**
     * get the score of top users
     * @return a list of TopUser instance
     * @see TopUser
     */
    public ArrayList<TopUser> getTopUsers() {
        if(topUsers == null){
            databaseManager.pullDepositStat();
        }
        if(lastUpdate==null ||  (new Date().getTime()-lastUpdate.getTime())/1000 < 10){
            databaseManager.pullDepositStat();
        }
        return topUsers;
    }

    /**
     * get the national statistics
     * @return national statistics
     * @see NationalStat
     */
    public NationalStat getNationalStat() {
        if(nationalStat == null){
            databaseManager.pullDepositStat();
            databaseManager.pullDepositLogByUserId();
        }
        if(lastUpdate==null ||  (new Date().getTime()-lastUpdate.getTime())/1000 < 10){
            databaseManager.pullDepositStat();
            databaseManager.pullDepositLogByUserId();
        }
        return nationalStat;
    }

    public void setNationalStat(NationalStat nationalStat) {
        this.nationalStat = nationalStat;
    }

    public void setTopUsers(ArrayList<TopUser> topUsers) {
        this.topUsers = topUsers;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setDepositLogs(ArrayList<SimpleDepositLog> depositLogs) {
        this.depositLogs = depositLogs;
    }

    public ArrayList<SimpleDepositLog> getDepositLogs() {
        return depositLogs;
    }
}


