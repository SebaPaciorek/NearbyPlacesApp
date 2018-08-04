package com.example.nearbyplacesapp.contract.places;

import android.content.Context;

import com.google.android.gms.location.places.Place;

public interface ChosenPlaceInterface {

    interface View {
        void showPlace();

    }

    interface Presenter {
        void getPlace(Place place);

        void getView(ChosenPlaceInterface.View view);

        void getAndroidView(android.view.View view);

        void getContext(Context context);

        void getPlaceData();
    }
}
