package com.example.nearbyplacesapp.presenter.places;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.nearbyplacesapp.contract.map.MapInterface;
import com.example.nearbyplacesapp.view.places.ChosenPlaceActivity;
import com.example.nearbyplacesapp.contract.places.ChosenPlaceOnMapInterface;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ChosenPlaceOnMapPresenter implements ChosenPlaceOnMapInterface.Presenter {

    private String name;
    private String address;
    private double longitude;
    private double latitutde;
    private GoogleMap googleMap;

    private Context context;

    private ChosenPlaceOnMapInterface.View chosenPlaceView;

    private Place chosenPlace;


    public ChosenPlaceOnMapPresenter() {
    }

    @Override
    public void getPlace(Place chosenPlace) {
        this.chosenPlace = chosenPlace;
        setPlaceArguments();


//        Bundle bundle = new Bundle();
//        bundle.putString("name", (String) chosenPlace.getName());
//        bundle.putString("address", (String) chosenPlace.getAddress());
//        bundle.putDouble("latitude", chosenPlace.getLatLng().latitude);
//        bundle.putDouble("longitude", chosenPlace.getLatLng().longitude);

//        Intent intent = new Intent(context, ChosenPlaceActivity.class);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
    }
    @Override
    public void startChosenPlaceActivity(){
        Intent intent = new Intent(context, ChosenPlaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", (String) chosenPlace.getName());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public GoogleMap setPlace(GoogleMap googleMap) {
        LatLng latLng = chosenPlace.getLatLng();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(name).snippet(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        return googleMap;
    }

    public void setPlaceArguments() {
        this.name = (String) chosenPlace.getName();
        this.address = (String) chosenPlace.getAddress();
        this.longitude = chosenPlace.getLatLng().longitude;
        this.latitutde = chosenPlace.getLatLng().latitude;
    }


    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void getView(ChosenPlaceOnMapInterface.View view) {
        this.chosenPlaceView = view;
    }

    @Override
    public void showMap() {
        chosenPlaceView.showPlaceOnMap();
    }
}
