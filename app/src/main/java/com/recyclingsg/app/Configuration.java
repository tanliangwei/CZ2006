package com.recyclingsg.app;

/**
 * This class is a the configuration class. It is used at the start of the application runtime to load and call certain data and functions.
 * @author Honey Stars
 * @version 1.0
 */

public class Configuration {
    //the constructor and instance management code
    private static Configuration instance;

    /**
     * This returns a singleton instance of the Configuration.
     * @return Singleton instance of  Configuration
     */
    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
    //constructor for database manger
    private Configuration(){}

    /**
     * Loads all the data required by the Application.
     */
    public static void startUp(){
        DatabaseManager.getInstance();
        DatabaseManager.loadData();

    }
}
