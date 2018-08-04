package com.example.nearbyplacesapp.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.nearbyplacesapp.contract.internetConnection.InternetConnectionContract;
import com.example.nearbyplacesapp.database.NearbyPlacesApplication;
import com.example.nearbyplacesapp.view.MainActivity;

public class InternetConnectorReceiver extends BroadcastReceiver implements InternetConnectionContract.Presenter {

    private InternetConnectionContract.View view;

    public InternetConnectorReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            boolean isVisible = NearbyPlacesApplication.isActivityVisible();

            if (isVisible) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    changedStatusInternet(true);
                } else {
                    changedStatusInternet(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changedStatusInternet(boolean isConnected) {
//        view.showInternetTextView(isConnected);
        MainActivity.getInstance().showInternetTextView(isConnected);
    }

    @Override
    public void getView(InternetConnectionContract.View view) {
        this.view = view;
    }
}
