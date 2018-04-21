package com.recyclingsg.app;


import java.util.ArrayList;
import java.util.Date;

public interface DatabaseInterface {
    @Deprecated
    public ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints();
    @Deprecated
    public ArrayList<PublicTrashCollectionPoint> getSecondHandPublicTrashCollectionPoints();
    @Deprecated
    public ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints();


    public ArrayList<TrashCollectionPoint> getTrashCollectionPoint(String category);
    public void loadData();
    public boolean addDepositRecord(final DepositRecord depositRecord);

    public void addStatisticsManager();
    public void addUserManager();
    public boolean savePrivateTrashCollectionPoint(final PrivateTrashCollectionPoint collectionPoint);
    public void pullDepositStat();
    public void pullDepositStat(final Date begDate, final Date endDate, final int n_top);
    public void pullDepositLogByUserId();
}
