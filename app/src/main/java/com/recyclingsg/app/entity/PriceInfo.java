package com.recyclingsg.app.entity;

/**
 * Created by quzhe on 2018-4-21.
 */

public class PriceInfo{
    private String trashName;
    private String unit;
    private double pricePerUnit;

    public PriceInfo(String name, String unit, double pricePerUnit){
        this.trashName = name;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public String getTrashName() {return trashName;}

    public String getUnit() {
        return unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }
}

