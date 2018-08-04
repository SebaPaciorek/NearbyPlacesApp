package com.example.nearbyplacesapp.contract.bill;

import com.example.nearbyplacesapp.model.places.bill.BillModel;
import com.example.nearbyplacesapp.model.places.bill.PlaceModel;
import com.google.android.gms.location.places.Place;

import java.util.List;

public interface BillInterface {
    interface View {
        void getBillDetails();

        void setPlacesOnSpinner(List<PlaceModel> placeModelList);

        void addedSuccess();

        void addedFailure();

        void showProgress(final boolean show);
    }

    interface Presenter {
        void getView(BillInterface.View view);

        void addBill();

        void setBillDetails(BillModel billModel);

        void getToken(String token);

        void setListPlaces(List<Place> listPlaces);

        void setPlacesOnSpinner();
    }
}
