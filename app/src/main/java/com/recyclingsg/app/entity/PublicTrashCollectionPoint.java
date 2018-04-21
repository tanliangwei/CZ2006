package com.recyclingsg.app.entity;

import com.recyclingsg.app.entity.TrashCollectionPoint;

import java.util.ArrayList;

/**
 * This class is a Public Trash Collection Point. It represents public firms and organisation which collects trash.
 * @author Honey Stars
 * @version 1.0
 */


public class PublicTrashCollectionPoint extends TrashCollectionPoint {

    /**
     * This constructs a Trash Collection Point
     * @param name The name of the Trash Collection Point
     * @param xCoordinate The X-Coordinate of the Trash Collection Point for navigation purposes
     * @param yCoordinate The Y-Coordinate of the Trash Collection Point for navigation purposes
     * @param openTime The opening time of the Trash Collection Point in HHMM format
     * @param closeTime The opening time of the Trash Collection Point in HHMM format
     * @param trashName The array of trash categories in which this trash collection point accepts
     * @param trashCost The respective prices for each trash category
     * @param daysOpen An array of size 7 which indicates the days in a week which the Trash Collection Point is open
     * @param description A description of the Trash Collection Point and preferences with regards to trash collection
     * @param address The address of the Trash Collection Point
     */
    public PublicTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<String> trashName, ArrayList<Integer> trashCost, int[] daysOpen,String description, String address){
        super(name, xCoordinate, yCoordinate, openTime, closeTime, trashName, trashCost,daysOpen,description, address);
    }

    /**
     * This constructs a Trash Collection Point. Go ahead and set the attributes yourself.
     */
    public PublicTrashCollectionPoint(){};
}
