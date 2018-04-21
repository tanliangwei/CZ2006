package com.recyclingsg.app;

public class DatabaseFactory {

    public DatabaseInterface makeDatabaseManager(String databaseType){
        DatabaseInterface dbInterface = null;

        switch (databaseType){
            case "SQL":
                dbInterface = DatabaseManager.getInstance();

        }
        return dbInterface;
    }
}
