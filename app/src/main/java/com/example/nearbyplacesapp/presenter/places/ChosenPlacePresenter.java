package com.example.nearbyplacesapp.presenter.places;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.places.ChosenPlaceInterface;
import com.google.android.gms.location.places.Place;

public class ChosenPlacePresenter implements ChosenPlaceInterface.Presenter, View.OnClickListener {

    private String name;
    private String address;
    private double longitude;
    private double latitutde;
    private String websiteUri;
    private String phoneNumber;
    private float rating;

    private Place chosenPlace;

    private ChosenPlaceInterface.View chosenPlaceView;
    private View view;
    private Context context;

    public ChosenPlacePresenter() {
    }

    @Override
    public void getPlace(Place chosenPlace) {
        this.chosenPlace = chosenPlace;
        setPlaceArguments();
    }

    @Override
    public void getView(ChosenPlaceInterface.View view) {
        this.chosenPlaceView = view;
    }

    @Override
    public void getAndroidView(View view) {
        this.view = view;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    public void setPlaceArguments() {
        this.name = (String) chosenPlace.getName();
        this.address = (String) chosenPlace.getAddress();
        this.longitude = chosenPlace.getLatLng().longitude;
        this.latitutde = chosenPlace.getLatLng().latitude;
        this.websiteUri = String.valueOf(chosenPlace.getWebsiteUri());
        this.phoneNumber = (String) chosenPlace.getPhoneNumber();
        this.rating = chosenPlace.getRating();
    }

    @Override
    public void getPlaceData() {
        TextView nameTextView = view.findViewById(R.id.name);
        nameTextView.setText(name);
        TextView addressTextView = view.findViewById(R.id.address);
        addressTextView.setText(address);
        TextView phoneTextView = view.findViewById(R.id.phone);
        phoneTextView.setText(phoneNumber);
        TextView websiteTextView = view.findViewById(R.id.website);
        if (websiteUri != null) {
            websiteTextView.setText(websiteUri);  //dodac interakcje z przegladarka internetowa
        }else{
            websiteTextView.setText("Brak strony");
        }

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);

        if (rating > -1) {
            ratingBar.setRating(rating);
        } else {
            ratingBar.setVisibility(View.GONE);
        }

        websiteTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.website:
                String link = websiteUri;
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
                break;

            case R.id.phone:
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                context.startActivity(intent1);
                break;
        }


    }
}
