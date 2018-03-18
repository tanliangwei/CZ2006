package com.recyclingsg.app;

/**
 * Created by quzhe on 2018-3-16.
 */

public class PrivateTrashCollectionPoint extends TrashCollectionPoint {
    private String owner;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
