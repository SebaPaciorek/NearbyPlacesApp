package com.example.nearbyplacesapp.contract.places;

import android.content.Context;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;

public interface ChosenPlaceOnMapInterface {

    interface View {

        void showPlaceOnMap();
    }

    interface Presenter {
        void getPlace(Place place);

        GoogleMap setPlace(GoogleMap googleMap);

        void getContext(Context context);

        void getView(ChosenPlaceOnMapInterface.View view);

        void showMap();

        void startChosenPlaceActivity();
    }
}
