package com.recyclingsg.app;


import java.util.ArrayList;
import java.util.Date;

public interface DatabaseInterface {
    public ArrayList<PrivateTrashCollectionPoint> getEWastePrivateTrashCollectionPoints();
    public ArrayList<PrivateTrashCollectionPoint> getSecondHandPrivateTrashCollectionPoints();
    public ArrayList<PrivateTrashCollectionPoint> getCashForTrashPrivateTrashCollectionPoints();
    public ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints();
    public ArrayList<PublicTrashCollectionPoint> getSecondHandPublicTrashCollectionPoints();
    public ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints();

    public void loadData();
    public boolean addDepositRecord(final DepositRecord depositRecord);

    public void addStatisticsManager();
    public void addUserManager();
    public boolean savePrivateTrashCollectionPoint(final PrivateTrashCollectionPoint collectionPoint);
    public void pullDepositStat();
    public void pullDepositStat(final Date begDate, final Date endDate, final int n_top);
    public void pullDepositLogByUserId();
}
