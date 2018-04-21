package com.recyclingsg.app.entity;

/**
 * This class is the Top User class. It stores data of Top Users to be displayed in standings chart.
 * @author Honey Stars
 * @version 1.0
 */
public class TopUser{
    private String userName;
    private double score;

    /**
     * This creates a Top User to be displayed by statistic activity in the standings chart.
     * @param userName The user's name
     * @param score The number of points earned by the user
     */
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
