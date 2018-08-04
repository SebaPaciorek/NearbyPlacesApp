package com.example.nearbyplacesapp;

import com.example.nearbyplacesapp.model.places.PlaceType;

import java.util.List;

public interface SpinnerListener {
    void onItemsSelected(List<PlaceType> items);
}
