package com.recyclingsg.app.control;

/**
 * This is the DatabaseInterface. It contains database methods which are required for the application to function. This allows new Databases to be compatible with our system easily by implementing this interface.
 * @author Honey Stars
 * @version 1.0
 */

import com.recyclingsg.app.entity.DepositRecord;
import com.recyclingsg.app.entity.PrivateTrashCollectionPoint;
import com.recyclingsg.app.entity.PublicTrashCollectionPoint;
import com.recyclingsg.app.entity.TrashCollectionPoint;

import java.util.ArrayList;
import java.util.Date;

public interface DatabaseInterface {
    @Deprecated
    public ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints();
    @Deprecated
    public ArrayList<PublicTrashCollectionPoint> getSecondHandPublicTrashCollectionPoints();
    @Deprecated
    public ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints();


    /**
     * This function returns a category of Trash Collection Points based on the trash type selected by the user.
     * @param category The category of trash type selected
     * @return The trash collection points belonging to the category selected
     */
    public ArrayList<TrashCollectionPoint> getTrashCollectionPoint(String category);

    /**
     * Loads and cache the data in the database manager.
     */
    public void loadData();

    /**
     * Saves the deposit record as a new entry in the database.
     * @param depositRecord The deposit record to be added
     * @return A boolean with true representing a successful adding and false being an unsuccessful one
     */
    public boolean addDepositRecord(final DepositRecord depositRecord);


    /**
     * Creates a reference to an instance of statistic manager in the database manager.
     */
    public void addStatisticsManager();

    /**
     * Creates a reference to an instance of user manager in the database manager.
     */
    public void addUserManager();

    /**
     * Saves the Private Trash Collection Point created by the user into the database.
     * @param collectionPoint The Trash Collection Point created
     * @return A boolean with true representing a successful adding and false being an unsuccessful one
     */
    public boolean savePrivateTrashCollectionPoint(final PrivateTrashCollectionPoint collectionPoint);

    /**
     * This function pulls all deposit statistics to be cached by the system.
     */
    public void pullDepositStat();

    /**
     * This function pulls deposit statistics between the period specified.
     * @param begDate The starting date
     * @param endDate The ending date
     * @param n_top Number of top users
     */
    public void pullDepositStat(final Date begDate, final Date endDate, final int n_top);

    /**
     * Pulls the deposit statistics of the user.
     */
    public void pullDepositLogByUserId();


}
