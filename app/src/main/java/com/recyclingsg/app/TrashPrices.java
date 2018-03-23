package com.recyclingsg.app;

/**
 * Created by Howard on 15/3/2018.
 */

public class TrashPrices {

    public static final String[] typeOfTrash = {"Second Hand Goods", "eWaste", "Cash For Trash"};
    private String trashName;
    private float prices;

    public void setTrashName(String name){trashName = name;}
    public String getTrashName(){return trashName;}

    public void setPrices(float prices){this.prices=prices;}
    public float getPrices(){return prices;}

    public TrashPrices(String name, float prices){
        setTrashName(name);
        setPrices(prices);
    }


}
