package com.recyclingsg.app;

/**
 * Created by David on 2018/3/18.
 */

public class FacebookManager {
    private String userID;
    private String userName;

    public String getUserId(){
        return userID;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserID(String id){
        userID=id;
    }
    public void setUserName(String name){
        userName=name;
    }
}
