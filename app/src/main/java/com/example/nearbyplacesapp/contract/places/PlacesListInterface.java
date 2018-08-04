package com.example.nearbyplacesapp.contract.places;

import android.app.Activity;
import android.content.Context;

import com.example.nearbyplacesapp.presenter.places.PlacesRecyclerViewAdapter;
import com.google.android.gms.location.places.Place;

import java.util.Collection;
import java.util.List;

public interface PlacesListInterface {

    interface View {
        void showPlacesList(PlacesRecyclerViewAdapter recyclerViewAdapter, List<Place> placeList);
        void showProgress(final boolean show);
    }

    interface Presenter {
        void placeDetectionClient();

        void getCurrentPlaceItems();

        boolean isLocationAccessPermitted();

        void getCurrentPlaceData();

        void requestLocationAccessPermission();

        void getView(PlacesListInterface.View view);

        void getContext(Context context);

        void getActivity(Activity activity);

        void searchPlace(String text);

        void filterPlaces(Collection<Integer> filters);

        List<Place> getPlaces();
    }
}