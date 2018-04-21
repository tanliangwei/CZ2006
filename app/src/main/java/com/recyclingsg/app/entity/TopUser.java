package com.recyclingsg.app.entity;

/**
 * Created by quzhe on 2018-4-21.
 */
public class TopUser{
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
