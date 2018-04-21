package com.recyclingsg.app.entity;

/**
 * This class is the Price Info class. It records and stores price information of Sub-Trash Type of Cash-For-Trash objects.
 * @author Honey Stars
 * @version 1.0
 */

public class PriceInfo{
    private String trashName;
    private String unit;
    private double pricePerUnit;

    /**
     * This creates a price info object.
     * @param name The name of the Sub-Trash Type
     * @param unit The units of the Sub-Trash Type
     * @param pricePerUnit The price per unit of the Sub-Trash Type
     */
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

