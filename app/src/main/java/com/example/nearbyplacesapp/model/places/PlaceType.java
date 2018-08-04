package com.example.nearbyplacesapp.model.places;

import java.util.Collection;
import java.util.List;

public class PlaceType {


    long Id;
    String name;
    boolean isSelected;

    Collection<Integer> placeID;

    Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Collection<Integer> getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Collection<Integer> placeID) {
        this.placeID = placeID;
    }
}