package com.recyclingsg.app;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    //The attributes
    // public trash collection points
    private static ArrayList<PublicTrashCollectionPoint> EWastePublicTrashCollectionPoints = new ArrayList<>();
    private static ArrayList<PublicTrashCollectionPoint> RecyclablesPublicTrashCollectionPoints = new ArrayList<>();
    private static ArrayList<PublicTrashCollectionPoint> CashForTrashPublicTrashCollectionPoints = new ArrayList<>();

    //private trash collection points
    private static ArrayList<PrivateTrashCollectionPoint> EWastePrivateTrashCollectionPoints = new ArrayList<>();
    private static ArrayList<PrivateTrashCollectionPoint> RecyclablesPrivateTrashCollectionPoints = new ArrayList<>();
    private static ArrayList<PrivateTrashCollectionPoint> CashForTrashPrivateTrashCollectionPoints = new ArrayList<>();

    //getter methods
    public static ArrayList<PrivateTrashCollectionPoint> getEWastePrivateTrashCollectionPoints() {
        return EWastePrivateTrashCollectionPoints;
    }
    public static ArrayList<PrivateTrashCollectionPoint> getRecyclablesPrivateTrashCollectionPoints() {
        return RecyclablesPrivateTrashCollectionPoints;
    }
    public static ArrayList<PrivateTrashCollectionPoint> getCashForTrashPrivateTrashCollectionPoints() {
        return CashForTrashPrivateTrashCollectionPoints;
    }

    public ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints(){return EWastePublicTrashCollectionPoints;}
    public ArrayList<PublicTrashCollectionPoint> getRecyclablesPublicTrashCollectionPoints(){return RecyclablesPublicTrashCollectionPoints;}
    public ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints(){return CashForTrashPublicTrashCollectionPoints;}

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
                    ArrayList<Integer> trashprice = new ArrayList<Integer>(); //to be populated once we include prices.

                    //Push into Collectionpoint ArrayList
                    PublicTrashCollectionPoint temp = new PublicTrashCollectionPoint(name, latitude, longitude, openTime, closeTime, trash_type, trashprice, daysOpen);
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

    private static void pullPrivateData(final String type){
        // Connect to the URL using java's native library
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){
                String sURL = "http://www.sjtume.cn/cz2006/api/get-private-points?token=9ca2218ae5c6f5166850cc749085fa6d&point_type="; //Url to server
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
                    ArrayList<Integer> trashprice = new ArrayList<Integer>(); //to be populated once we include prices.

                    //Push into Collection point ArrayList
                    PrivateTrashCollectionPoint temp = new PrivateTrashCollectionPoint(name, latitude, longitude, openTime, closeTime, trash_type, trashprice, daysOpen);
                    if(type == "cash-for-trash"){
                        CashForTrashPrivateTrashCollectionPoints.add(temp);
                    }else if(type == "e-waste-recycling"){
                        EWastePrivateTrashCollectionPoints.add(temp);
                    }else{
                        RecyclablesPrivateTrashCollectionPoints.add(temp);
                    }
                }
            }
        });
        thread.start();

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
    public static boolean savePrivateTrashCollectionPoint(final PrivateTrashCollectionPoint collectionPoint){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://www.sjtume.cn/cz2006/api/add-private-point";
                HttpURLConnection conn;
                try{
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(15000);
                    conn.setReadTimeout(10000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    StringBuilder params = new StringBuilder("token=9ca2218ae5c6f5166850cc749085fa6d");
                    params.append("&userId=");
                    params.append(URLEncoder.encode(collectionPoint.getOwnerId().toString(),"UTF-8"));
                    params.append("&userName=");
                    params.append(URLEncoder.encode(collectionPoint.getOwnerName().toString(),"UTF-8"));
                    params.append("&longitude=");
                    params.append(URLEncoder.encode(String.valueOf(collectionPoint.getCoordinate().longitude),"UTF-8"));
                    params.append("&latitude=");
                    params.append(URLEncoder.encode(String.valueOf(collectionPoint.getCoordinate().latitude),"UTF-8"));
                    params.append("&postalCode=");
                    params.append(URLEncoder.encode(String.valueOf(collectionPoint.getZipCode()),"UTF-8"));


                    StringBuilder trashNamesBuilder = new StringBuilder();
                    for (TrashPrices t : collectionPoint.getTrash()){
                        trashNamesBuilder.append(t.getTrashName());
                        trashNamesBuilder.append(" ");
                    }
                    String trashNames = trashNamesBuilder.toString();
                    params.append("&trash_type=");
                    params.append(URLEncoder.encode(trashNames,"UTF-8"));

                    String description = collectionPoint.getDescription();
                    if(description != null) {
                        params.append("&description=");
                        params.append(URLEncoder.encode(description,"UTF-8"));
                    }

                    String pointName = collectionPoint.getCollectionPointName();
                    if(pointName != null){
                        params.append("&pointName=");
                        params.append(URLEncoder.encode(pointName,"UTF-8"));
                    }

                    String address = collectionPoint.getAddress();
                    if(address != null) {
                        params.append("&address=");
                        params.append(URLEncoder.encode(address, "UTF-8"));
                    }

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                    writer.write(params.toString());
                    writer.flush();
                    writer.close();
                    os.close();

                    conn.connect();
                }
                catch (IOException e){
                    Log.e(TAG,e.getMessage());
                }
            }
        });
        thread.start();
        return true;
    }

    /**
     * add the deposit record to the server
     * @param depositRecord the deposit record to add
     * @return true if success
     */
    public static boolean addDepositRecord(final DepositRecord depositRecord){
        String dateStr = depositRecord.getDate().toString();
        String userId = depositRecord.getUserId();
        return true;
    }
}