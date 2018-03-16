package com.recyclingsg.app;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by jiahengzhang on 16/3/18.
 */

public class FilterManager {

    public FilterManager(){}
    private ArrayList<TrashCollectionPoint> openTrashCollectionPoints =new ArrayList<TrashCollectionPoint>();

    public ArrayList<TrashCollectionPoint> filterByCurrentDate(ArrayList<TrashCollectionPoint> beforeFilterTrashCollectionPoints){
        Date CurrentDate = new Date();
        DateFormat sdf = new SimpleDateFormat("HHmm");
        String CurrentDateString = sdf.format(CurrentDate);
        int currentTimeInt = Integer.parseInt(CurrentDateString);

        for(int x = 0; x < beforeFilterTrashCollectionPoints.size(); x++){
            ArrayList<TrashCollectionPoint>
            if((beforeFilterTrashCollectionPoints.get(x).getOpenTimeInInt() >= currentTimeInt) && beforeFilterTrashCollectionPoints.get(x).getCloseTimeInInt() <= currentTimeInt){

            }
        }

    }

}
