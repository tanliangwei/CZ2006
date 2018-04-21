package com.recyclingsg.app;

import java.util.Date;

/**
 * This class is a the configuration class. It is used at the start of the application runtime to load and call certain data and functions.
 * @author Honey Stars
 * @version 1.0
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

    /**
     * This creates a Deposit Record to be saved to the Database. It is used when the user deposits trash.
     * @param userid The User ID of the user depositing the trash
     * @param date The date when the User deposits the trash
     * @param units The units of trash deposited
     * @param trashInfo The category of trash which he deposit
     * @param score The score he earned for that deposition
     * @param TrashCollectionPointID The ID of the Trash Collection Point where he deposited
     * @param Reveneue The amount of money he earns for depositing the trash
     * @param nameOfUser The name of the User
     */
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
