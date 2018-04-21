package com.recyclingsg.app.entity;

/**
 * Created by quzhe on 2018-4-21.
 */
public class NationalStat{
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
