package com.recyclingsg.app;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Howard on 9/3/2018.
 */

public class Node {
    private String name;
    private String id;
    private LatLng latLng;

    public Node(String name, String id, LatLng latLng) {
        this.latLng = latLng;
        this.name = name;
        this.id = id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return name;
    }
}
