package com.recyclingsg.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

public class TrashInfo {

    //basic constructor
    public  TrashInfo(){}
    public TrashInfo(String name, float prices){
        setTrashType(name);
    }

    //constructor for E-waste and Second Hand where there is no price or fixed price.
    public TrashInfo(String name){ setTrashType(name);}

    //constructor for Cash-For-Trash scheme.
    public TrashInfo(String trashType, ArrayList<String> cashForTrashNames, ArrayList<String> CashForTrashUnits, ArrayList<Double> cashForTrashPrices){
        for(int i=0;i<cashForTrashNames.size();i++){
            String units = null;
            if (CashForTrashUnits.size()==0){
                units = "per unit";
            }else {
                units = CashForTrashUnits.get(i);
            }
            addTrashPrice(cashForTrashNames.get(i), units, cashForTrashPrices.get(i));
        }
        setTrashType(trashType);
    }

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
        if(trashType.equalsIgnoreCase("ewaste")){
            this.trashType = "e-waste";
        }
        if(trashType.equalsIgnoreCase("Second Hand Goods")){
            this.trashType="second-hand-goods";
        }
        if(trashType.equalsIgnoreCase("Cash For Trash")){
            this.trashType="cash-for-trash";
        }

    }

    public String getTrashTypeForSpinner(){
        if(trashType.equalsIgnoreCase("e-waste")||trashType.equalsIgnoreCase("e-waste-recycling")){
            return "    EWaste    ";
        }
        if(trashType.equalsIgnoreCase("Second-Hand-Goods")||trashType.equalsIgnoreCase("2nd-hand-goods-collection-points")){
            return "Second Hand Goods";
        }
        if(trashType.equalsIgnoreCase("Cash-For-Trash")){
            return "Cash For Trash";
        }
        return trashType;
    }

    @Deprecated
    public void setTrashName(String name){setTrashType(name);}

    @Deprecated public String getTrashName(){return getTrashType();}


    public void addTrashPrice(String trashName, String unit, double pricePerUnit) {
        trashPrices.put(trashName, new PriceInfo(trashName, unit, pricePerUnit));
    }

    PriceInfo getTrashPrice(String trashName){
        return trashPrices.get(trashName);
    }

    String getFirstTrashName(){
        for (String trashName : trashPrices.keySet()){
            // only return the first trash name
            return trashName;
        }
        return null;
    }

    ArrayList<PriceInfo> getPriceInfoList(){
        ArrayList<PriceInfo> result = new ArrayList<>();
        for (PriceInfo priceInfo : trashPrices.values()){
            result.add(priceInfo);
        }
        return result;
    }
}

class PriceInfo{
    private String trashName;
    private String unit;
    private double pricePerUnit;

    PriceInfo(String name, String unit, double pricePerUnit){
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

