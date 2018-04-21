package com.recyclingsg.app.control;

import android.util.Log;

import com.recyclingsg.app.entity.PrivateTrashCollectionPoint;
import com.recyclingsg.app.entity.PublicTrashCollectionPoint;
import com.recyclingsg.app.entity.TrashCollectionPoint;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static android.content.ContentValues.TAG;

/**
 * This class is the Filer Manager. It filters arrays of trash collection points based on parameter keyed in by the user.
 * @author Honey Stars
 * @version 1.0
 */

public class FilterManager {

    private static final FilterManager ourInstance = new FilterManager();

    /**
     * This returns a singleton instance of the Filter Manager.
     * @return Singleton instance of Filter Manager
     */
    public static FilterManager getInstance() {
        return ourInstance;
    }


    /**
     * The creates an instance of Filter Manager
     */
    public FilterManager(){}
    private ArrayList<TrashCollectionPoint> openTrashCollectionPoints =new ArrayList<TrashCollectionPoint>();
    private ArrayList<TrashCollectionPoint> closedTrashCollectionPoints = new ArrayList<TrashCollectionPoint>();


    /**
     * This function filters the trash collection points based on their opening and closing hours.
     * @param beforeFilterTrashCollectionPoints The array of trash collection points to be filtered
     */
    public void filterByCurrentDate(ArrayList<TrashCollectionPoint> beforeFilterTrashCollectionPoints){
        Log.d(TAG, "filterByCurrentDate: "+ beforeFilterTrashCollectionPoints.get(0).getCollectionPointName()+beforeFilterTrashCollectionPoints.get(0).getCoordinate());
        Date CurrentDate = new Date();
        DateFormat sdf = new SimpleDateFormat("HHmm");
        String CurrentDateString = sdf.format(CurrentDate);
        Log.d(TAG, "filterByCurrentDate: checkingDateFormatString" + CurrentDateString);
        int currentTimeInt = Integer.parseInt(CurrentDateString);
        Log.d(TAG, "filterByCurrentDate: checkingCurrentTimeInt" + currentTimeInt);
        for(int x = 0; x < beforeFilterTrashCollectionPoints.size(); x++){
            if((beforeFilterTrashCollectionPoints.get(x).getOpenTimeInInt() >= currentTimeInt) || beforeFilterTrashCollectionPoints.get(x).getCloseTimeInInt() <= currentTimeInt){
                closedTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
                Log.d(TAG, "filterByCurrentDate: closed ones " + closedTrashCollectionPoints.get(x).getCollectionPointName());
            } else {
                openTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
                Log.d(TAG, "filterByCurrentDate: opened ones" + openTrashCollectionPoints.get(0).getCollectionPointName());
            }
        }
    }


    public ArrayList<TrashCollectionPoint> getOpenTrashCollectionPoints(){
        return openTrashCollectionPoints;
    }

    public ArrayList<TrashCollectionPoint> getClosedTrashCollectionPoints(){
        return closedTrashCollectionPoints;
    }
//    public void filterPublicByCurrentDate(ArrayList<PublicTrashCollectionPoint> beforeFilterTrashCollectionPoints){
//        Log.d(TAG, "filterByCurrentDate: "+ beforeFilterTrashCollectionPoints.get(0).getCollectionPointName()+beforeFilterTrashCollectionPoints.get(0).getCoordinate());
//        Date CurrentDate = new Date();
//        DateFormat sdf = new SimpleDateFormat("HHmm");
//        String CurrentDateString = sdf.format(CurrentDate);
//        Log.d(TAG, "filterByCurrentDate: checkingDateFormatString" + CurrentDateString);
//        int currentTimeInt = Integer.parseInt(CurrentDateString);
//        Log.d(TAG, "filterByCurrentDate: checkingCurrentTimeInt" + currentTimeInt);
//        for(int x = 0; x < beforeFilterTrashCollectionPoints.size(); x++){
//            if((beforeFilterTrashCollectionPoints.get(x).getOpenTimeInInt() >= currentTimeInt) || beforeFilterTrashCollectionPoints.get(x).getCloseTimeInInt() <= currentTimeInt){
//                closedTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
//                Log.d(TAG, "filterByCurrentDate: closed ones " + closedTrashCollectionPoints.get(x).getCollectionPointName());
//            } else {
//                openTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
//                Log.d(TAG, "filterByCurrentDate: opened ones" + openTrashCollectionPoints.get(0).getCollectionPointName());
//            }
//        }
//    }
//
//    public void filterPrivateByCurrentDate(ArrayList<PrivateTrashCollectionPoint> beforeFilterTrashCollectionPoints){
//        if(beforeFilterTrashCollectionPoints.size()!=0) {
//            Log.d(TAG, "filterByCurrentDate: " + beforeFilterTrashCollectionPoints.get(0).getCollectionPointName() + beforeFilterTrashCollectionPoints.get(0).getCoordinate());
//            Date CurrentDate = new Date();
//            DateFormat sdf = new SimpleDateFormat("HHmm");
//            String CurrentDateString = sdf.format(CurrentDate);
//            Log.d(TAG, "filterByCurrentDate: checkingDateFormatString" + CurrentDateString);
//            int currentTimeInt = Integer.parseInt(CurrentDateString);
//            Log.d(TAG, "filterByCurrentDate: checkingCurrentTimeInt" + currentTimeInt);
//            for (int x = 0; x < beforeFilterTrashCollectionPoints.size(); x++) {
//                if ((beforeFilterTrashCollectionPoints.get(x).getOpenTimeInInt() >= currentTimeInt) || beforeFilterTrashCollectionPoints.get(x).getCloseTimeInInt() <= currentTimeInt) {
//                    closedTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
//                    Log.d(TAG, "filterByCurrentDate: closed ones " + closedTrashCollectionPoints.get(x).getCollectionPointName());
//                } else {
//                    openTrashCollectionPoints.add(beforeFilterTrashCollectionPoints.get(x));
//                    Log.d(TAG, "filterByCurrentDate: opened ones" + openTrashCollectionPoints.get(0).getCollectionPointName());
//                }
//            }
//        }
//    }
}
