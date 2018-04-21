package com.recyclingsg.app.control;

/**
 * This class is the Database Factory. It is in charged of deciding the Database class to initialise.
 * @author Honey Stars
 * @version 1.0
 */

public class DatabaseFactory {

    /**
     * This function creates the relevant Database Manager to be used in the application based on the string input.
     * @param databaseType the database to create
     * @return the relevant instance of database manager.
     */
    public DatabaseInterface makeDatabaseManager(String databaseType){
        DatabaseInterface dbInterface = null;

        switch (databaseType){
            case "SQL":
                dbInterface = DatabaseManager.getInstance();

        }
        return dbInterface;
    }
}
