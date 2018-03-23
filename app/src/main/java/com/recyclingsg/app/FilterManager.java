package com.recyclingsg.app;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static android.content.ContentValues.TAG;

/**
 * Created by jiahengzhang on 16/3/18.
 */

public class FilterManager {

    public FilterManager(){}
    private ArrayList<TrashCollectionPoint> openTrashCollectionPoints =new ArrayList<TrashCollectionPoint>();
    private ArrayList<TrashCollectionPoint> closedTrashCollectionPoints = new ArrayList<TrashCollectionPoint>();

    public void filterByCurrentDate(ArrayList<PublicTrashCollectionPoint> beforeFilterTrashCollectionPoints){
        Log.d(TAG, "filterByCurrentDate: "+ beforeFilterTrashCollectionPoints.get(0).getCollectionPointName()+beforeFilterTrashCollectionPoints.get(0).getCoordinate());
        Date CurrentDate = new Date();
        DateFormat sdf = new SimpleDateFormat("HHmm");
        String CurrentDateString = sdf.format(CurrentDate);
        Log.d(TAG, "filterByCurrentDate: checkingDateFormatString" + CurrentDateString);
        int currentTimeInt = Integer.parseInt(CurrentDateString);
        Log.d(TAG, "filterByCurrentDate: checkingCurrentTimeInt" + currentTimeInt);
        for(int x = 0; x < beforeFilterTrashCollectionPoints.size(); x++){
            if((beforeFilterTrashCollectionPoints.get(x).getOpenTimeInInt() >= currentTimeInt)|| beforeFilterTrashCollectionPoints.get(x).getCloseTimeInInt() <= currentTimeInt){
            closedTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
                Log.d(TAG, "filterByCurrentDate: closed ones " + closedTrashCollectionPoints.get(0).getCoordinate());
            } else {
                openTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
                Log.d(TAG, "filterByCurrentDate: opened ones" + openTrashCollectionPoints.get(0).getCoordinate());
            }
        }
    }
    public ArrayList<TrashCollectionPoint> getOpenTrashCollectionPoints(){
        return openTrashCollectionPoints;
    }

    public ArrayList<TrashCollectionPoint> getClosedTrashCollectionPoints(){
        return closedTrashCollectionPoints;
    }

}
