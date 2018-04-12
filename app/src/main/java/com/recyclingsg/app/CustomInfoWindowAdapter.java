package com.recyclingsg.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Howard on 16/3/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private Marker markerShowingInfoWindow;
    private Context mContext;
    private View popUP;
    private Button testButton;


//    private View.OnTouchListener customWindowOnTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent m){
//            Log.d("TAG", "onClick: customInfoWindowButton Clicked!!!!!!!!!!! ");
//            CharSequence text2 = "Hello toast!";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(popUP.getContext(), text2, duration);
//            toast.show();
//            return true;
//        }
//    };


    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
    }







    @Override
    public View getInfoWindow(Marker marker) {
        TrashCollectionPoint tcp= (TrashCollectionPoint)marker.getTag();
        TrashCollectionPointManager.getInstance();
        TrashCollectionPointManager.setUserSelectedTrashCollectionPoint(tcp);
        Log.d("MARKER CLICK","THE SELECTED POINT IS" + tcp.getCollectionPointName());



        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Getting view from the layout file info_window_layout
        View popUp = inflater.inflate(R.layout.layout_popup, null);
        Log.d("TAG", "onClick: customInfoWindow Created! ");

        TextView popUpTitle = (TextView) popUp.findViewById(R.id.popup_title);
        TextView popUpContent = (TextView) popUp.findViewById(R.id.popup_content);
        ImageView popUpImage = (ImageView) popUp.findViewById(R.id.popup_image);
        Button customInfoWindowButton = popUp.findViewById(R.id.testButton);

            popUpTitle.setText(marker.getTitle());

            //trashCollectionPointManager.setUserSelectedTrashPoint();
            TrashCollectionPointManager.getInstance();
            TrashCollectionPointManager.setUserSelectedTrashPointID(marker.getId());
            TrashCollectionPointManager.setUserSelectedTrashPointCoordinates(marker.getPosition());

            popUpContent.setText("Hello dudes");
            customInfoWindowButton.setText("Hold Down to Navigate/n");
            Log.d("TAG", "getInfoContents: "+ TrashCollectionPointManager.getInstance().getUserSelectedTrashPointCoordinates().toString().substring(10,TrashCollectionPointManager.getInstance().getUserSelectedTrashPointCoordinates().toString().length()-1));
            popUP = popUp;
            return popUp;
        }


    }
