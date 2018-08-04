package com.example.nearbyplacesapp.database;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NearbyPlacesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("nearbyplaces.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }
}
