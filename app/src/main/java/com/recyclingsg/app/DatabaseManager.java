package com.recyclingsg.app;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";
    private StatisticsManager statisticsManager;
    private UserManager userManager;

    //The attributes
    // public trash collection points
    private ArrayList<PublicTrashCollectionPoint> EWastePublicTrashCollectionPoints = new ArrayList<>();
    private ArrayList<PublicTrashCollectionPoint> SecondHandPublicTrashCollectionPoints = new ArrayList<>();
    private ArrayList<PublicTrashCollectionPoint> CashForTrashPublicTrashCollectionPoints = new ArrayList<>();

    //private trash collection points
    private ArrayList<PrivateTrashCollectionPoint> EWastePrivateTrashCollectionPoints = new ArrayList<>();
    private ArrayList<PrivateTrashCollectionPoint> SecondHandPrivateTrashCollectionPoints = new ArrayList<>();
    private ArrayList<PrivateTrashCollectionPoint> CashForTrashPrivateTrashCollectionPoints = new ArrayList<>();

    //getter methods
    public ArrayList<PrivateTrashCollectionPoint> getEWastePrivateTrashCollectionPoints() {
        pullPrivateData("e-waste");
        delay(300);
        Log.d(TAG,"pull e-waste points: "+EWastePrivateTrashCollectionPoints.size());
        return EWastePrivateTrashCollectionPoints;
    }
    public ArrayList<PrivateTrashCollectionPoint> getSecondHandPrivateTrashCollectionPoints() {
        pullPrivateData("second-hand-goods");
        delay(300);
        Log.d(TAG,"pull second-hand-goods private points: "+SecondHandPrivateTrashCollectionPoints.size());
        return SecondHandPrivateTrashCollectionPoints;
    }
    public ArrayList<PrivateTrashCollectionPoint> getCashForTrashPrivateTrashCollectionPoints() {
        pullPrivateData("cash-for-trash");
        delay(300);
        Log.d(TAG,"pull cash-for-trash private points: "+CashForTrashPrivateTrashCollectionPoints.size());
        return CashForTrashPrivateTrashCollectionPoints;
    }

    public ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints(){return EWastePublicTrashCollectionPoints;}
    public ArrayList<PublicTrashCollectionPoint> getSecondHandPublicTrashCollectionPoints(){return SecondHandPublicTrashCollectionPoints;}
    public ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints(){return CashForTrashPublicTrashCollectionPoints;}

    //the constructor and instance management code
    private static final DatabaseManager instance = new DatabaseManager();
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static DatabaseManager getInstance(){
        return instance;
    }
    //constructor for database manger
    private DatabaseManager(){}

    //loads all data. This is called in the startup class
    public void loadData(){
        //Pull public cash for trash
        pullPublicCashForTrash();
        //Pull public eWaste
        pullPublicEWasteFromDatabase();
        //Pull public SecondHand, currently unsupported
        pullPublicSecondHandFromDatabase();
    }

    //API functions
    public void pullPublicEWasteFromDatabase(){
        pullPublicData("e-waste-recycling");
    }

    public void pullPublicCashForTrash(){
        pullPublicData("cash-for-trash");
    }

    public void pullPublicSecondHandFromDatabase(){
        pullPublicData("2nd-hand-goods-collection-points");
    }

    public void addStatisticsManager(){
        this.statisticsManager = StatisticsManager.getInstance();
    }
    public void addUserManager(){this.userManager = UserManager.getInstance();}


    private void pullPublicData(final String type){
        // Connect to the URL using java's native library
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){
                String sURL = "http://www.sjtume.cn/cz2006/api/get-public-points?token=9ca2218ae5c6f5166850cc749085fa6d&point_type="; //Url to server
                sURL = sURL + type;

                Log.d(TAG, "run: HERE IT IS " + sURL);

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
                Log.d(TAG, "successfully pulled "+count+" "+type+" points");
                JsonArray collectionPointArray = rootobj.get("points").getAsJsonArray();
                for (int i = 0; i < collectionPointArray.size(); i++) {
                    PublicTrashCollectionPoint newPoint = new PublicTrashCollectionPoint();

                    //Collecting collectionPoint information
                    JsonObject ith_object = collectionPointArray.get(i).getAsJsonObject();

                    // get id
                    String id = ith_object.get("id").getAsString();
                    newPoint.setTrashCollectionPointID(id);

                    // get coordinates
                    double latitude = ith_object.get("latitude").getAsDouble();
                    double longitude = ith_object.get("longitude").getAsDouble();
                    newPoint.setCoordinate(new LatLng(latitude, longitude));

                    // get address
                    String addressBlockNumber = ith_object.get("address_block_number").getAsString();
                    String addressBuildingName = ith_object.get("address_building_name").getAsString();
                    String addressStreetName = ith_object.get("address_building_name").getAsString();
                    String address = addressBlockNumber+" "+addressBuildingName+" "+addressStreetName;
                    newPoint.setAddress(address);

                    // set opening hours
                    int openTime = 0; //Unspecified in database yet
                    int closeTime = 2359; //Unspecified in database yet
                    int[] daysOpen = {1, 1, 1, 1, 1, 0, 0}; //Hard coded, only open on weekend
                    newPoint.setOpenTime(openTime);
                    newPoint.setCloseTime(closeTime);
                    newPoint.setDayOpen(daysOpen);

                    // get collection point name
                    String name = ith_object.get("name").getAsString();
                    newPoint.setCollectionPointName(name);

                    // get description
                    String description = ith_object.get("description").getAsString();
                    newPoint.setDescription(description);

                    // get zip code
                    String zipCode = "unknown";
                    if(ith_object.get("postal_code") != null){
                        zipCode = ith_object.get("postal_code").getAsString();
                    }
                    newPoint.setZipCode(zipCode);

                    ArrayList<TrashInfo> trashInfos = new ArrayList<>();
                    JsonArray jArray = ith_object.get("trash_type").getAsJsonArray();
                    if (jArray != null) {
                        for (int j = 0; j < jArray.size(); j++) {
                            TrashInfo newTrashType = new TrashInfo(jArray.get(j).getAsString());
                            //Log.e("REGARDING TRASHINFO", "TRASH INFO NAME "+jArray.get(j).getAsString()+" "+ newTrashType.getTrashType());
                            if(newTrashType.getTrashType().equalsIgnoreCase("cash-for-trash")){
                                JsonArray trashPrices = ith_object.get("trash_prices").getAsJsonArray();
                                // Log.d(TAG, "got cash-for-trash public point with trashpice size: "+trashPrices.size());
                                for(int t=0; t<trashPrices.size(); t++){
                                    JsonObject priceInfo = trashPrices.get(t).getAsJsonObject();
                                    String trashName = priceInfo.get("trash_name").getAsString();
                                    String unit = priceInfo.get("unit").getAsString();
                                    Double  price = priceInfo.get("price_per_unit").getAsDouble();
                                    newTrashType.addTrashPrice(trashName, unit, price);
                                }
                            }
                            trashInfos.add(newTrashType);
                        }
                    }
                    newPoint.setTrash(trashInfos);

                    if(type == "cash-for-trash"){
                        CashForTrashPublicTrashCollectionPoints.add(newPoint);
                    }else if(type == "e-waste-recycling"){
                        EWastePublicTrashCollectionPoints.add(newPoint);
                    }else if(type == "2nd-hand-goods-collection-points"){
                        SecondHandPublicTrashCollectionPoints.add(newPoint);
                    }
                }
            }
        });
        thread.start();
    }

    private void pullPrivateData(final String type){
        if(type == "cash-for-trash"){
            CashForTrashPrivateTrashCollectionPoints.clear();
        }else if(type == "e-waste"){
            EWastePrivateTrashCollectionPoints.clear();
        }else{
            SecondHandPrivateTrashCollectionPoints.clear();
        }
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
                    Log.d(TAG, "opening url connection "+sURL);
                } catch (IOException e) {
                    Log.e(TAG, "Failed to open url connection "+sURL);
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
                JsonObject rootobj = null;
                try {
                    root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                    rootobj = root.getAsJsonObject();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to get reply content");
                    e.printStackTrace();
                }

                int count = rootobj.get("count").getAsInt();
                Log.e(TAG, "successfully pulled "+count+" private "+type+" points");
                JsonArray collectionPointArray = rootobj.get("points").getAsJsonArray();
                for (int i = 0; i < collectionPointArray.size(); i++) {
                    PrivateTrashCollectionPoint newPoint = new PrivateTrashCollectionPoint();

                    //Collecting collectionPoint information
                    JsonObject ith_object = collectionPointArray.get(i).getAsJsonObject();

                    // get id
                    String id = ith_object.get("id").getAsString();
                    newPoint.setTrashCollectionPointID(id);

                    // get owner info
                    String ownerName = ith_object.get("owner_name").getAsString();
                    String ownerID = ith_object.get("owner_id").getAsString();
                    newPoint.setOwnerId(ownerID);
                    newPoint.setOwnerName(ownerName);

                    // get coordinates
                    double latitude = ith_object.get("latitude").getAsDouble();
                    double longitude = ith_object.get("longitude").getAsDouble();
                    newPoint.setCoordinate(new LatLng(latitude, longitude));

                    // get address
                    String address = ith_object.get("address").getAsString();
                    newPoint.setAddress(address);

                    // set opening hours
                    int openTime = 0; //Unspecified in database yet
                    int closeTime = 2359; //Unspecified in database yet
                    int[] daysOpen = {1, 1, 1, 1, 1, 0, 0}; //Hard coded, only open on weekend
                    newPoint.setOpenTime(openTime);
                    newPoint.setCloseTime(closeTime);
                    newPoint.setDayOpen(daysOpen);

                    // get collection point name
                    String name = ith_object.get("name").getAsString();
                    newPoint.setCollectionPointName(name);

                    // get description
                    String description = ith_object.get("description").getAsString();
                    newPoint.setDescription(description);

                    // get zip code
                    String zipCode = "unknown";
                    if(ith_object.get("postal_code") != null){
                        zipCode = ith_object.get("postal_code").getAsString();
                    }
                    newPoint.setZipCode(zipCode);

                    ArrayList<TrashInfo> trashInfos = new ArrayList<>();
                    JsonArray jArray = ith_object.get("trash_type").getAsJsonArray();
                    if (jArray != null) {
                        for (int j = 0; j < jArray.size(); j++) {
                            TrashInfo newTrashType = new TrashInfo(jArray.get(j).getAsString());
                            if(newTrashType.getTrashType() == "cash-for-trash"){
                                JsonArray trashPrices = ith_object.get("trash_prices").getAsJsonArray();
                                for(int t=0; t<trashPrices.size(); t++){
                                    JsonObject priceInfo = trashPrices.get(t).getAsJsonObject();
                                    String trashName = priceInfo.get("trash_name").getAsString();
                                    String unit = priceInfo.get("unit").getAsString();
                                    Double  price = priceInfo.get("price_per_unit").getAsDouble();
                                    newTrashType.addTrashPrice(trashName, unit, price);
                                }
                            }
                            trashInfos.add(newTrashType);
                        }
                    }
                    newPoint.setTrash(trashInfos);

                    if(type == "cash-for-trash"){
                        CashForTrashPrivateTrashCollectionPoints.add(newPoint);
                    }else if(type == "e-waste"){
                        EWastePrivateTrashCollectionPoints.add(newPoint);
                    }else{
                        SecondHandPrivateTrashCollectionPoints.add(newPoint);
                    }
                }
            }
        });
        thread.start();

    }

    //function for querying
    public ArrayList<TrashCollectionPoint> queryCollectionPoint(TrashInfo trashQuery){
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
            tempArray = SecondHandPublicTrashCollectionPoints;
        }

        for(int i = 0; i < tempArray.size(); i++){
            retCollectionPoint.add(tempArray.get(i));
        }
        return retCollectionPoint;
    }

    public String to_trashType(TrashInfo trashQuery) throws Exception{
        if(trashQuery.getTrashName() == "Second Hand Goods"){
            return "2nd-hand-goods-collection-points";
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
    public boolean savePrivateTrashCollectionPoint(final PrivateTrashCollectionPoint collectionPoint){
        Log.d(TAG, "savePrivateTrashCollectionPoint: ");
        Log.d(TAG, "savePrivateTrashCollectionPoint: " + collectionPoint.getOwnerId());
        Log.d(TAG, "savePrivateTrashCollectionPoint: " + collectionPoint.getOwnerName());
        Log.d(TAG, "savePrivateTrashCollectionPoint: " + collectionPoint.getTrash());
        Log.d(TAG, "savePrivateTrashCollectionPoint: " + collectionPoint.getCoordinate().latitude);
        Log.d(TAG, "savePrivateTrashCollectionPoint: " + collectionPoint.getCoordinate().longitude);



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
//                    if (collectionPoint.getOwnerId()==null)
//                        params.append(URLEncoder.encode("TEST_ID","UTF-8"));
//                        params.append("&userName=");
//                        params.append(URLEncoder.encode("TEST_NAME","UTF-8"));
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
                    StringBuilder trashPrices = new StringBuilder();
                    String delimiter = "";
                    for (TrashInfo t : collectionPoint.getTrash()){
                        trashNamesBuilder.append(t.getTrashType());
                        Log.d(TAG, "run: " + t.getTrashType());
                        trashNamesBuilder.append(" ");
                        for(PriceInfo pinfo : t.getPriceInfoList()){
                            trashPrices.append(delimiter);
                            delimiter = "#";
                            trashPrices.append(pinfo.getTrashName());
                            trashPrices.append("?");
                            trashPrices.append(pinfo.getUnit());
                            trashPrices.append("?");
                            trashPrices.append(pinfo.getPricePerUnit());
                        }
                    }
                    String trashNames = trashNamesBuilder.toString();
                    params.append("&trash_type=");
                    params.append(URLEncoder.encode(trashNames,"UTF-8"));
                    if (!trashPrices.toString().isEmpty()){
                        params.append("&trash_prices=");
                        params.append(URLEncoder.encode(trashPrices.toString(),"UTF-8"));
                    }

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

                    Log.d(TAG, "adding private point with parameters:");
                    Log.d(TAG, params.toString());

                    writer.write(params.toString());
                    writer.flush();
                    writer.close();
                    os.close();

                    Log.d(TAG, "adding private point to the server");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Log.d(TAG,line);
                    }
                    reader.close();
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
    public boolean addDepositRecord(final DepositRecord depositRecord){
        String dateStr = depositRecord.getDate().toString();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://www.sjtume.cn/cz2006/api/add-deposit-record";
                HttpURLConnection conn;
                try{
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(15000);
                    conn.setReadTimeout(10000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    StringBuilder params = new StringBuilder("token=9ca2218ae5c6f5166850cc749085fa6d");
                    params.append("&user_id=");
                    params.append(URLEncoder.encode(depositRecord.getUserId().toString(),"UTF-8"));
                    params.append("&user_name=");
                    params.append(URLEncoder.encode(depositRecord.getNameOfUser().toString(),"UTF-8"));
                    params.append("&date=");
                    params.append(URLEncoder.encode(String.valueOf((depositRecord.getDate().getTime()/1000)),"UTF-8"));
                    params.append("&point_id=");
                    params.append(URLEncoder.encode(String.valueOf(depositRecord.getTrashCollectionPointID()),"UTF-8"));
                    params.append("&score=");
                    params.append(URLEncoder.encode(String.valueOf(depositRecord.getScore()),"UTF-8"));

                    TrashInfo trashInfo = depositRecord.getTrashInfo();
                    String trashType = trashInfo.getTrashType();
                    params.append("&trash_type=");
                    params.append(URLEncoder.encode(trashType,"UTF-8"));

                    //String trashName = String.valueOf(" ");

                    if(trashType.equalsIgnoreCase("cash-for-trash") && trashInfo.getFirstTrashName() != null) {
                        params.append("&trash_name=");
                        params.append(URLEncoder.encode(trashInfo.getFirstTrashName(),"UTF-8"));
                    }

                    String unit = String.valueOf(depositRecord.getUnits());
                    if(unit != null){
                        params.append("&trash_unit=");
                        params.append(URLEncoder.encode(unit,"UTF-8"));
                    }

                    String revenue = String.valueOf(depositRecord.getRevenue());
                    if(revenue != null) {
                        params.append("&profit=");
                        params.append(URLEncoder.encode(revenue, "UTF-8"));
                    }

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                    Log.d(TAG, "adding deposit record with parameter "+ params.toString());
                    writer.write(params.toString());
                    writer.flush();
                    writer.close();
                    os.close();

                    // get response
                    Log.d(TAG, "adding deposit record to server");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Log.d(TAG,line);
                    }
                    reader.close();
                }
                catch (IOException e){
                    Log.d(TAG,e.getMessage());
                }
            }
        });
        thread.start();
        return true;
    }

    private void delay(int time){
        try{
            Thread.sleep(time);
            Log.d(TAG, "pulling private data");
        }
        catch (InterruptedException e){
            Log.d(TAG, "interrupted when pulling private data");
            e.printStackTrace();
        }
    }

    public void pullDepositStat(){
        pullDepositStat(null, null, -1);
    }

    public void pullDepositStat(final Date begDate, final Date endDate, final int n_top){
        // Connect to the URL using java's native library
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){
                String sURL = "http://www.sjtume.cn/cz2006/api/get-deposit-stat?token=9ca2218ae5c6f5166850cc749085fa6d"; //Url to server
                // set optional parameters
                if (userManager.getUserId()!=null){
                    sURL += ("&user_id="+userManager.getUserId());
                }

                if(begDate != null){
                    sURL += ("&beg_date"+begDate.getTime()/1000);
                }

                if(endDate != null){
                    sURL += ("&end_date"+endDate.getTime()/1000);
                }

                if(n_top != -1){
                    sURL += ("&n_top"+n_top);
                }

                URL url = null;
                try {
                    url = new URL(sURL);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "invalid url for statistics query");
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

                // parse and set the national statistics
                int userCount = rootobj.get("national_user_count").getAsInt();
                double avgScore = rootobj.get("national_user_avgscore").getAsDouble();
                int cashForTrashCount = rootobj.get("cash_for_trash_cnt").getAsInt();
                int ewasteCount = rootobj.get("ewaste_cnt").getAsInt();
                int secondHandCount = rootobj.get("second_hand_good_cnt").getAsInt();
                statisticsManager.setNationalStat(new NationalStat(avgScore, userCount,
                        cashForTrashCount, ewasteCount, secondHandCount));
                Log.d(TAG, "successfully pulled national statistics");

                // set user score
                if(!rootobj.get("user_score").isJsonNull()){
                    double score = rootobj.get("user_score").getAsDouble();
                    statisticsManager.setUserScore(score);
                    Log.d(TAG, "successfully pulled user score");
                }
                else{
                    Log.d(TAG, "no user score");
                }

                // parse and set top users
                JsonArray topN = rootobj.get("top_n info").getAsJsonArray();
                ArrayList<TopUser> topUserArrayList = new ArrayList<>();
                for (int i = 0; i < topN.size(); i++) {
                    JsonObject ithArrObj = topN.get(i).getAsJsonObject();
                    String userName = ithArrObj.get("user_name").getAsString();
                    double score = ithArrObj.get("user_score").getAsDouble();

                    TopUser topUser = new TopUser(userName, score);
                    topUserArrayList.add(topUser);
                }
                statisticsManager.setTopUsers(topUserArrayList);
                Log.d(TAG, "successfully pulled "+topUserArrayList.size()+" top users");
                statisticsManager.setLastUpdate(new Date());
            }
        });
        thread.start();
    }


    public void pullDepositLogByUserId() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String sURL = "http://www.sjtume.cn/cz2006/api/get-user-deposit-log?token=9ca2218ae5c6f5166850cc749085fa6d"; //Url to server
                // set optional parameters
                if (userManager.getUserId() != null) {
                    sURL += ("&user_id=" + userManager.getUserId());
                }
                // return if not logged in
                else {
                    return;
                }

                URL url = null;
                try {
                    url = new URL(sURL);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "invalid url for deposit log query");
                    e.printStackTrace();
                }

                HttpURLConnection request = null;
                try {
                    request = (HttpURLConnection) url.openConnection();
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
                if (root != null) {
                    rootobj = root.getAsJsonObject();
                }

                JsonArray logArray = rootobj.get("records").getAsJsonArray();
                Log.d(TAG, "successfully pulled deposit records: "+logArray.size());

                ArrayList<SimpleDepositLog> logs = new ArrayList<>();
                for (int i = 0; i < logArray.size(); i++) {
                    JsonObject logJsonObj = logArray.get(i).getAsJsonObject();
                    String userName = logJsonObj.get("user_name").getAsString();
                    String date = logJsonObj.get("date").getAsString();
                    String trashType = logJsonObj.get("trash_type").getAsString();
                    Double score = logJsonObj.get("score").getAsDouble();
                    SimpleDepositLog newlog = new SimpleDepositLog(userName, date, trashType, score);
                    logs.add(newlog);
                }
                statisticsManager.setDepositLogs(logs);
            }
        });
        thread.start();
    }
}