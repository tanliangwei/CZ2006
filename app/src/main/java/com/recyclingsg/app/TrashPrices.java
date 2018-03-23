package com.recyclingsg.app;

import java.util.HashMap;

/*
// Example usage, to create an trashInfo object and set trash prices
TrashInfo myAcceptableTrash = new TrashInfo();
// set trash category to cash-for-trash
myAcceptableTrash.setTrashType("cash-for-trash");
// set paper price to 1.2 dollar per kg
myAcceptableTrash.addTrashPrice("paper", "kg", 1.2);
// set metal bin price to 2.5 dollar per kg
myAcceptableTrash.addTrashPrice("metal bin", "kg", 2.5);
// to get the trash category
String trashType = myAcceptableTrash.getTrashType();
// to get the trash price of paper
PriceInfo paperPrice = myAcceptableTrash.getTrashPrice("paper");
*/

public class TrashPrices {
    public static final String[] typeOfTrash = {"Second Hand Goods", "eWaste", "Cash For Trash"};
    // the general category of the trash, should be one of cash-for-trash, e-waste, second-hand-goods
    private String trashType;
    // detailed price info, with key-value pair of trashName-PriceInfo(unit, pricePerUnit)
    private HashMap<String, PriceInfo> trashPrices = new HashMap<>();

    public String getTrashType() {
        return trashType;
    }

    public void setTrashType(String trashType) {
        this.trashType = trashType;
    }

    @Deprecated
    public void setTrashName(String name){setTrashType(name);}

    @Deprecated
    public String getTrashName(){return getTrashType();}

    public TrashPrices(String name, float prices){
        setTrashType(name);
    }

    public void addTrashPrice(String trashName, String unit, double pricePerUnit) {
        trashPrices.put(trashName, new PriceInfo(unit, pricePerUnit));
    }

    PriceInfo getTrashPrice(String trashName){
        return trashPrices.get(trashName);
    }
}

class PriceInfo{
    private String unit;
    private double pricePerUnit;

    PriceInfo(String unit, double pricePerUnit){
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnit() {
        return unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }
}

