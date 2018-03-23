package com.recyclingsg.app;

import java.util.Date;

/**
 * Created by quzhe on 2018-3-20.
 */

public class DepositRecord {
    private String userId;
    private Date date;
    private float units;
    private TrashInfo trashInfo;
    private float score;
    private String TrashCollectionPointID;
    private float Revenue;
    private String NameOfUser;

    public DepositRecord(String userid, Date date, float units, TrashInfo trashInfo, float score, String TrashCollectionPointID, float Reveneue, String nameOfUser) {
        this.userId = userid;
        this.date = date;
        this.units = units;
        this.trashInfo = trashInfo;
        this.score = score;
        this.TrashCollectionPointID = TrashCollectionPointID;
        this.Revenue = Reveneue;
        this.NameOfUser = nameOfUser;
    }

    //getter and setter methods
    public float getUnits() {
        return units;
    }
    public void setUnits(float units) {
        this.units = units;
    }
    public TrashInfo getTrashInfo() {
        return trashInfo;
    }
    public void setTrashInfo(TrashInfo trashInfo) {
        this.trashInfo = trashInfo;
    }
    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }
    public String getTrashCollectionPointID() {
        return TrashCollectionPointID;
    }
    public void setTrashCollectionPointID(String trashCollectionPointID) {
        TrashCollectionPointID = trashCollectionPointID;
    }
    public float getRevenue() {
        return Revenue;
    }
    public void setRevenue(float revenue) {
        Revenue = revenue;
    }
    public String getNameOfUser() {
        return NameOfUser;
    }
    public void setNameOfUser(String nameOfUser) {
        NameOfUser = nameOfUser;
    }
    public String getUserId() {
        return userId;
    }
    public Date getDate() {
        return date;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
