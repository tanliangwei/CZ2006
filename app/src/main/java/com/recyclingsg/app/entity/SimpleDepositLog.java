package com.recyclingsg.app.entity;

/**
 * Created by quzhe on 2018-4-21.
 */
public class SimpleDepositLog{
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
        else if(trashType.equalsIgnoreCase("e-waste-recycling")|| trashType.equalsIgnoreCase("e-waste")
                || trashType.equalsIgnoreCase("EWaste") || trashType.equalsIgnoreCase("    EWaste    ")){
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
