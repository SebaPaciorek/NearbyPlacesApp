package com.example.nearbyplacesapp.presenter.map;

import com.example.nearbyplacesapp.contract.map.MapInterface;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapPresenter implements MapInterface.Presenter {

    private List<Place> placeList;

    private MapInterface.View mapView;

    private ArrayList<LatLng> latLngList;

    public MapPresenter() {
    }

    @Override
    public GoogleMap setPlaces(GoogleMap googleMap) {
        MarkerOptions markerOptions = new MarkerOptions();

        latLngList = new ArrayList<>();

        for (int i = 0; i < placeList.size(); i++) {
            latLngList.add(i, placeList.get(i).getLatLng());
        }
        int i = 0;
        for (LatLng point : latLngList) {

            markerOptions.position(point);
            markerOptions.title(placeList.get(i).getName().toString());
            markerOptions.snippet(placeList.get(i).getAddress().toString());
            googleMap.addMarker(markerOptions);
            i++;
        }
        if(placeList.size() > 0){
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeList.get(0).getLatLng()));
        }


        return googleMap;
    }

    @Override
    public void getView(MapInterface.View view) {
        this.mapView = view;
    }

    @Override
    public void showMap() {
        mapView.showPlacesOnMap();
    }

    @Override
    public void setListPlaces(List<Place> listPlaces) {
        this.placeList = listPlaces;
    }

}
