package com.recyclingsg.app;

/**
 * Created by jiahengzhang on 16/3/18.
 */

public class UserManager {
    private static String userID;
    private static String userName;

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

    public void addPrivateTrashCollectionPointToUser(PrivateTrashCollectionPoint ptcp){
        ptcp.setOwnerId(userID);
        ptcp.setOwnerName(userName);
        DatabaseManager.getInstance();
        DatabaseManager.addPrivateTrashCollectionPointToUser(ptcp);
    }
}


