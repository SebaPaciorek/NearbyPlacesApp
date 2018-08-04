package com.example.nearbyplacesapp.contract.map;


import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapInterface {

    interface View {
        void showPlacesOnMap();

        void moveCamera(LatLng latLng);
    }

    interface Presenter {
        GoogleMap setPlaces(GoogleMap googleMap);

        void getView(MapInterface.View view);

        void showMap();

        void setListPlaces(List<Place> listPlaces);
    }
}
