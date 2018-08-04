package com.example.nearbyplacesapp.view.places;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.places.ChosenPlaceOnMapInterface;
import com.example.nearbyplacesapp.presenter.places.PlacesRecyclerViewAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class ChosenPlaceOnMapFragment extends Fragment implements OnMapReadyCallback, ChosenPlaceOnMapInterface.View {


    private ChosenPlaceOnMapInterface.Presenter chosenPlaceOnMapPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chosenPlaceOnMapPresenter = PlacesRecyclerViewAdapter.chosenPlaceOnMapPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chosen_place_on_map, container, false);

        chosenPlaceOnMapPresenter.getView(this);
        chosenPlaceOnMapPresenter.showMap();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(11);
        chosenPlaceOnMapPresenter.setPlace(googleMap);
    }

    @Override
    public void showPlaceOnMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_chosen_place_on_map);
        mapFragment.getMapAsync(this);
    }
}
