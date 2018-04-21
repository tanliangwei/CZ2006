package com.recyclingsg.app.entity;

/**
 * This class is the National Statistic Class. It stores information about the national statistics such as the average score of users, the number of users as well as the break down of trash categories.
 * @author Honey Stars
 * @version 1.0
 */
public class NationalStat{
    private double avgScore;
    // the number of depositors in the country
    private int userCount;
    private int ewasteCount = 0;
    private int secondHandGoodCount = 0;
    private int cashForTrashCount = 0;

    /**
     * This creates a National Statistic Object
     * @param avgScore The average score
     * @param userCount The number of users
     * @param cashForTrashCount The number of cash for trash deposits
     * @param ewasteCount The number of Ewaste deposits
     * @param secondHandGoodCount The number of Second Hand Good deposits
     */
    public NationalStat(double avgScore, int userCount, int cashForTrashCount, int ewasteCount, int secondHandGoodCount){
        this.avgScore = avgScore;
        this.userCount = userCount;
        this.cashForTrashCount = cashForTrashCount;
        this.ewasteCount = ewasteCount;
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

    public int getEwasteCount() {
        return ewasteCount;
    }

    public int getSecondHandGoodCount() {
        return secondHandGoodCount;
    }
}
