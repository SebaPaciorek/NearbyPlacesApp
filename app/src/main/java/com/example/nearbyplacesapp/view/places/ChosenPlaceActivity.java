package com.example.nearbyplacesapp.view.places;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.nearbyplacesapp.R;

public class ChosenPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_place);
        Intent intent = getIntent();
        showChosenPlace();
        showChosenPlaceOnMap(intent);
        String name = intent.getExtras().getString("name");
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar)));
    }

    public void showChosenPlace(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChosenPlaceFragment chosenPlaceFragment = new ChosenPlaceFragment();
        fragmentTransaction.replace(R.id.chosenPlaceFrame, chosenPlaceFragment).commit();
    }

    public void showChosenPlaceOnMap(Intent intent){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChosenPlaceOnMapFragment chosenPlaceOnMapFragment = new ChosenPlaceOnMapFragment();
        chosenPlaceOnMapFragment.setArguments(intent.getExtras());
        fragmentTransaction.replace(R.id.chosenPlaceMapFrame, chosenPlaceOnMapFragment).commit();
    }
}
