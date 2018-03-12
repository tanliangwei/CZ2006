package com.example.howard.softwareeng;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Howard on 12/3/2018.
 */

public class CollectionPointManager {

    //to be removed
    private Node[] nodes;

    public CollectionPointManager(){
        //to be removed
        nodes = new Node[]{new Node("Jurong Recycling Station", "a1234", new LatLng(1.3343,(103.742))), new Node("Pasir Ris Recyling Station", "b4321", new LatLng(1.3721, 103.9474))};
    }

    public Node[] getNodes() {
        return nodes;
    }
}
