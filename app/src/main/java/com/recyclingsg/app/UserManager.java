package com.recyclingsg.app;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by jiahengzhang on 16/3/18.
 */

public class UserManager {
    private static String userID;
    private static String userName;

    private static UserManager instance;
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static UserManager getInstance(){
        if (instance == null) {
            try {
                instance = new UserManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct UserManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }

    public UserManager(){}

    public static String getUserId() {
        return userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserID(String id) {
        userID = id;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public static void addPrivateTrashCollectionPointToUser(PrivateTrashCollectionPoint ptcp){
        ptcp.setOwnerId(userID);
        ptcp.setOwnerName(userName);
        DatabaseManager.getInstance();
        DatabaseManager.savePrivateTrashCollectionPoint(ptcp);
    }
}


