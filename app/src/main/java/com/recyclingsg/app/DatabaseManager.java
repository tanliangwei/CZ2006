package com.recyclingsg.app;

import android.nfc.Tag;
import android.util.Log;

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
    private static final String TAG = "DatabaseManager";

    //The attributes
    private static ArrayList<PublicTrashCollectionPoint> EWastePublicTrashCollectionPoints = new ArrayList<>();
    private static ArrayList<PublicTrashCollectionPoint> RecyclablesPublicTrashCollectionPoints = new ArrayList<>();
    private static ArrayList<PublicTrashCollectionPoint> CashForTrashPublicTrashCollectionPoints = new ArrayList<>();

    public static ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints(){return EWastePublicTrashCollectionPoints;}
    public static ArrayList<PublicTrashCollectionPoint> getRecyclablesPublicTrashCollectionPoints(){return RecyclablesPublicTrashCollectionPoints;}
    public static ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints(){return CashForTrashPublicTrashCollectionPoints;}

    //the constructor and instance management code
    private static DatabaseManager instance;
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static DatabaseManager getInstance(){
        if (instance == null) {
            try {
                instance = new DatabaseManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct DatabaseManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }
    //constructor for database manger
    public DatabaseManager(){}

    //loads all data. This is called in the startup class
    public static void loadData(){
        //Pull public cash for trash
        pullPublicCashForTrash();
        //Pull public eWaste
        pullPublicEWasteFromDatabase();
        //Pull public recyclable, currently unsupported
        // pullPublicRecyclablesFromDatabase();
    }

    //API functions
    public static void pullPublicEWasteFromDatabase(){
        pullPublicData("e-waste-recycling");
    }

    public static void pullPublicCashForTrash(){
        pullPublicData("cash-for-trash");
    }

    // unsupported
//    public static void pullPublicRecyclablesFromDatabase(){
//        pullPublicData("public-recyclable");
//    }

    private static void pullPublicData(final String type){
        // Connect to the URL using java's native library
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run(){
                String sURL = "http://www.sjtume.cn/cz2006/api/get-public-points?token=9ca2218ae5c6f5166850cc749085fa6d&point_type="; //Url to server
                sURL = sURL + type;

                URL url = null;
                try {
                    url = new URL(sURL);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "invalid url for pulling data");
                    e.printStackTrace();
                }

                HttpURLConnection request = null;
                try {
                    request = (HttpURLConnection)url.openConnection();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to open url connection");
                    e.printStackTrace();
                }
                try {
                    request.connect();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to connect to url");
                    e.printStackTrace();
                }

                JsonParser jp = new JsonParser();
                JsonElement root = null;
                try {
                    root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                } catch (IOException e) {
                    Log.e(TAG, "Failed to get reply content");
                    e.printStackTrace();
                }

                JsonObject rootobj = null;
                if(root != null){
                    rootobj = root.getAsJsonObject();
                }
                int count = rootobj.get("count").getAsInt();
                Log.e(TAG, "successfully pulled "+count+type+"points");
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
            }
        });

        thread.start();

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