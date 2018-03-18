package com.recyclingsg.app;

import java.util.ArrayList;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class DatabaseManager {

    //The attributes
    private static ArrayList<PublicTrashCollectionPoint> EWastePublicTrashCollectionPoints;
    private static ArrayList<PublicTrashCollectionPoint> RecyclablesPublicTrashCollectionPoints;
    private static ArrayList<PublicTrashCollectionPoint> CashForTrashPublicTrashCollectionPoints;

    //get and set functions
    public static void setEWastePublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        EWastePublicTrashCollectionPoints = list;
    }
    public static void setRecyclablesPublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        RecyclablesPublicTrashCollectionPoints = list;
    }
    public static void setCashForTrashPublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        CashForTrashPublicTrashCollectionPoints = list;
    }
    public static ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints(){return EWastePublicTrashCollectionPoints;}
    public static ArrayList<PublicTrashCollectionPoint> getRecyclablesPublicTrashCollectionPoints(){return RecyclablesPublicTrashCollectionPoints;}
    public static ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints(){return CashForTrashPublicTrashCollectionPoints;}

    //the constructor and instance management code
    private static DatabaseManager instance;
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static DatabaseManager getInstance() {
        if (instance == null) {
            try {
                instance = new DatabaseManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    //constructor for database manger
    public DatabaseManager() throws Exception {}

    //loads all data. This is called in the startup class
    public static void loadData() throws Exception{
        //Pull public cash for trash
        try {
            pullPublicCashForTrash();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Pull public eWaste
        try {
            pullPublicEWasteFromDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Pull public recyclable
        try {
            pullPublicRecyclablesFromDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //API functions
    public static void pullPublicEWasteFromDatabase() throws Exception{
        pullPublicData("general_waste");
    }
    public static void pullPublicRecyclablesFromDatabase() throws Exception{
        pullPublicData("cash-for-trash");
    }
    public static void pullPublicCashForTrash() throws Exception{
        pullPublicData("e-waste-recycling");
    }

    private static void pullPublicData(String type) throws Exception{

        String sURL = "http://www.sjtume.cn/cz2006/api/get-public-points?token=9ca2218ae5c6f5166850cc749085fa6d&point_type="; //Url to server
        sURL = sURL + type;

        // Connect to the URL using java's native library
        //URL url = new URL(sURL);

        try{
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection)url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonObject rootobj = root.getAsJsonObject();
            int count = rootobj.get("count").getAsInt();
            JsonArray collectionPointArray = rootobj.get("points").getAsJsonArray();
            for (int i = 0; i < collectionPointArray.size(); i++) {
                //Collecting collectionPoint information
                JsonObject ith_object = collectionPointArray.get(i).getAsJsonObject();
                int id = ith_object.get("id").getAsInt();
                double latitude = ith_object.get("latitude").getAsDouble();
                double longitude = ith_object.get("longitude").getAsDouble();
                int openTime = 0; //Unspecified in database yet
                int closeTime = 2359; //Unspecified in database yet
                int[] daysOpen = {1, 1, 1, 1, 1, 0, 0}; //Hard coded, only open on weekend
                String name = ith_object.get("name").getAsString();
                String description = ith_object.get("description").getAsString();
                ArrayList<String> trash_type = new ArrayList<String>();
                JsonArray jArray = ith_object.get("trash_type").getAsJsonArray();
                if (jArray != null) {
                    for (int j = 0; j < jArray.size(); j++) {
                        trash_type.add(jArray.get(j).getAsString());
                    }
                }

                //Push into Collectionpoint ArrayList
                PublicTrashCollectionPoint temp = new PublicTrashCollectionPoint(name, latitude, longitude, openTime, closeTime, trash_type, daysOpen);
                if(type == "cash-for-trash"){
                    CashForTrashPublicTrashCollectionPoints.add(temp);
                }else if(type == "e-waste-recycling"){
                    EWastePublicTrashCollectionPoints.add(temp);
                }else{
                    RecyclablesPublicTrashCollectionPoints.add(temp);
                }
            }

        } catch (MalformedURLException e) {
            System.out.println("PulldataException" + e);
        }
    }

    private static void pullPrivateData(){
        //TODO database hasn't provided private collection point data yet
    }


    //function for querying
    public static ArrayList<TrashCollectionPoint> queryCollectionPoint(TrashPrices trashQuery){
        ArrayList<TrashCollectionPoint> retCollectionPoint = new ArrayList<TrashCollectionPoint>();
        ArrayList<PublicTrashCollectionPoint> tempArray;
        String trashType = null;
        try {
            trashType = to_trashType(trashQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(trashType == "cash-for-trash"){
            tempArray = CashForTrashPublicTrashCollectionPoints;
        }else if(trashType == "e-waste-recycling"){
            tempArray = EWastePublicTrashCollectionPoints;
        }else{
            tempArray = RecyclablesPublicTrashCollectionPoints;
        }

        for(int i = 0; i < tempArray.size(); i++){
            retCollectionPoint.add(tempArray.get(i));
        }
        return retCollectionPoint;
    }

    public static String to_trashType(TrashPrices trashQuery) throws Exception{
        if(trashQuery.getTrashName() == "General Waste"){
            return "general_waste";
        }else if(trashQuery.getTrashName() == "eWaste"){
            return  "e-waste-recycling";
        }else if(trashQuery.getTrashName() == "Cash For Trash") {
            return "cash-for-trash";
        }else{
            return "Undefined";
        }
    }


    /**
     * add private trash collection point
     * @param collectionPoint the collection point to add
     * @return true if success
     */
    public boolean addPrivatePoint(PrivateTrashCollectionPoint collectionPoint){
        return true;
    }

}