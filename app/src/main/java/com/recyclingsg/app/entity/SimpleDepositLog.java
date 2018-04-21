package com.recyclingsg.app.entity;

/**
 * This class is the Simple Deposit Log Class . It contains information about Trash Depositions in the Database so that this information can be displayed in the Statistics Activity.
 * @author Honey Stars
 * @version 1.0
 */
public class SimpleDepositLog{
    private String depositDate;
    private String trashType;
    private double score;
    private String userName;

    /**
     * This creates a Simple Deposit Log
     * @param userName The user who made the deposition
     * @param depositDate The date of deposition
     * @param trashType The trash category
     * @param score The number of points earned
     */
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
