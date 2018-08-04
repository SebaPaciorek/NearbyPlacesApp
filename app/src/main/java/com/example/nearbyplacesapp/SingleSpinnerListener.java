package com.example.nearbyplacesapp;

import com.example.nearbyplacesapp.model.places.bill.PlaceModel;

import java.util.List;

public interface SingleSpinnerListener {
    void onItemsSelected(List<PlaceModel> items);
}
