package com.example.nearbyplacesapp.presenter.places;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.nearbyplacesapp.InternetCheck;
import com.example.nearbyplacesapp.contract.map.MapInterface;
import com.example.nearbyplacesapp.contract.places.PlacesListInterface;
import com.example.nearbyplacesapp.presenter.map.MapPresenter;
import com.example.nearbyplacesapp.view.bill.BillFragment;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlacesListPresenter implements PlacesListInterface.Presenter {

    private PlacesListInterface.View view;
    private Context context;
    private Activity activity;
    private String filterText = new String();
    private Collection<Integer> filters = new ArrayList<>();
    private List<Place> placeList;

    private PlaceDetectionClient placeDetectionClient;
    private static final int LOC_REQ_CODE = 1;

    public static MapInterface.Presenter mapPresenter;
    private InternetCheck internetCheck;
    private boolean internetIsOn = true;

    public PlacesListPresenter() {
        internetCheck = new InternetCheck(internet -> {
            if (internet) {
                internetIsOn = internet;
                Log.d("LOG", " Place Fragment Internet is on");
            } else {
                internetIsOn = internet;
                Log.d("LOG", " Place Fragment  Internet is off");
            }
        });
    }

    @Override
    public void placeDetectionClient() {
        this.placeDetectionClient = Places.getPlaceDetectionClient(activity);
    }

    @Override
    public void getCurrentPlaceItems() {
        if (isLocationAccessPermitted()) {
            getCurrentPlaceData();
        } else {
            requestLocationAccessPermission();
        }
    }

    @Override
    public boolean isLocationAccessPermitted() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    @SuppressLint("MissingPermission")
    public void getCurrentPlaceData() {
        internetCheck = new InternetCheck(internet -> {
            internetIsOn = internet;
        });

        if (internetIsOn) {
            if (activity != null) {
                boolean isRestrictedToPlacesOpenNow = false;

                final PlaceFilter placeFilter = new PlaceFilter(isRestrictedToPlacesOpenNow, null);
                placeDetectionClient();

                Task<PlaceLikelihoodBufferResponse> placeResult = placeDetectionClient.getCurrentPlace(placeFilter);
                placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                        placeList = new ArrayList<>();

                        internetCheck = new InternetCheck(internet -> {
                            internetIsOn = internet;
                        });

                        if (internetIsOn) {
                            PlaceLikelihoodBufferResponse likelihoods = task.getResult();
                            if (filterText.isEmpty()) {
                                for (PlaceLikelihood placeLikelihood : likelihoods) {
                                    if (anyMatch(placeLikelihood.getPlace().getPlaceTypes(), filters)) {
                                        placeList.add(placeLikelihood.getPlace().freeze());
                                    }
                                    //placeList.add(placeLikelihood.getPlace().freeze());
                                }
                            } else {
                                for (PlaceLikelihood placeLikelihood : likelihoods) {
                                    if (anyMatch(placeLikelihood.getPlace().getPlaceTypes(), filters)) {
                                        if (placeLikelihood.getPlace().getName().toString().toLowerCase().contains(filterText)) {
                                            placeList.add(placeLikelihood.getPlace().freeze());
                                        }
                                    }

                                }
                            }

                            likelihoods.release();
                            mapPresenter = new MapPresenter();
                            mapPresenter.setListPlaces(placeList);
                            BillFragment.billPresenter.setListPlaces(placeList);
                            PlacesRecyclerViewAdapter recyclerViewAdapter = new PlacesRecyclerViewAdapter(placeList, activity);
                            if (activity != null)
                                view.showPlacesList(recyclerViewAdapter, placeList);
                        }

                    }
                });
            }
        }

    }

    private boolean anyMatch(Collection<Integer> placeTypes, Collection<Integer> filters) {
        for (Integer placeType : placeTypes) {
            if (filters.contains(placeType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOC_REQ_CODE);
    }

    @Override
    public void getView(PlacesListInterface.View view) {
        this.view = view;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void getActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void searchPlace(String text) {
        this.filterText = text;
        getCurrentPlaceData();
    }

    @Override
    public void filterPlaces(Collection<Integer> filters) {
        this.filters = filters;
        getCurrentPlaceData();
    }

    @Override
    public List<Place> getPlaces() {
        return placeList;
    }

}
