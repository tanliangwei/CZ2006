package com.recyclingsg.app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Howard on 12/3/2018.
 */

public class CollectionPointManager {

    //to be removed
    private List<Node> nodes;
    private String trashType;

    public CollectionPointManager(){
        nodes = new ArrayList<Node>(){{
            add (new Node("Jurong Recycling Station", "a1234", new LatLng(1.3343,(103.742))));
            add (new Node("Pasir Ris Recyling Station", "b4321", new LatLng(1.3721, 103.9474)));
            add (new Node("SouthWest Bound", "swb", new LatLng(1.22, 103.585)));
            add (new Node("NorthEast Bound", "neb", new LatLng(1.472823, 104.087221)));


        }};

    }

    public List<Node> getNodes() {
        return nodes;
    }

    public String getTrashType() {
        return trashType;
    }

    public void setTrashType(String trashType) {
        this.trashType = trashType;
    }
}
