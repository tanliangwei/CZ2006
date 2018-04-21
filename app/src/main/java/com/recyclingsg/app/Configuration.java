package com.recyclingsg.app;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class Configuration {
    //the constructor and instance management code
    private static Configuration instance;
    private DatabaseInterface databaseManager = DatabaseManager.getInstance();
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
    //constructor for database manger
    public Configuration(){}

    //load all the necessary things.
    public void startUp(){
        StatisticsManager.getInstance().addDatabaseManager();
        databaseManager.addStatisticsManager();
        databaseManager.addUserManager();
        databaseManager.loadData();

    }
}
