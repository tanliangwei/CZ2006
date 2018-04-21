package com.recyclingsg.app.entity;

/**
 * This class contains information about the trash category as well as the sub-categories if they exist.
 * @author Honey Stars
 * @version 1.0
 */

import com.recyclingsg.app.entity.PriceInfo;

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

    public  TrashInfo(){}

    /**
     * This creates a Trash Info object
     * @param name The category of Trash.
     */
    public TrashInfo(String name){ setTrashType(name);}

    /**
     * This creates a Trash Info object
     * @param trashType The category of trash
     * @param cashForTrashNames The names of Sub-Trash Type if Cash-For-Trash is selected
     * @param CashForTrashUnits The units of Sub-Trash Type if Cash-For-Trash is selected
     * @param cashForTrashPrices The prices of Sub-Trash Type if Cash-For-Trash is selected
     */
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
        else if(trashType.equalsIgnoreCase("Cash For Trash")){
            this.trashType="cash-for-trash";
        }
        else{
            this.trashType="second-hand-goods";
        }
    }

    /**
     * Returns the String of the Trash Type to be displayed in drop down menus.
     * @return
     */
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


    /**
     * This adds a Sub-Trash Type and its price and unit information to it.
     * @param trashName The Sub-Trash Type
     * @param unit The unit
     * @param pricePerUnit The price per unit
     */
    public void addTrashPrice(String trashName, String unit, double pricePerUnit) {
        trashPrices.put(trashName, new PriceInfo(trashName, unit, pricePerUnit));
    }

    /**
     * Return the price of the Sub-Trash Type
     * @param trashName The name of the Sub-Trash
     * @return The price
     */
    public PriceInfo getTrashPrice(String trashName){
        return trashPrices.get(trashName);
    }

    public String getFirstTrashName(){
        for (String trashName : trashPrices.keySet()){
            // only return the first trash name
            return trashName;
        }
        return null;
    }

    public ArrayList<PriceInfo> getPriceInfoList(){
        ArrayList<PriceInfo> result = new ArrayList<>();
        for (PriceInfo priceInfo : trashPrices.values()){
            result.add(priceInfo);
        }
        return result;
    }
}
