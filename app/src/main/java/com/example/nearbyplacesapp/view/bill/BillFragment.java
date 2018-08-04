package com.example.nearbyplacesapp.view.bill;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.SingleSpinnerListener;
import com.example.nearbyplacesapp.contract.bill.BillInterface;
import com.example.nearbyplacesapp.model.places.bill.BillModel;
import com.example.nearbyplacesapp.model.places.bill.PlaceModel;
import com.example.nearbyplacesapp.view.places.MultiSpinnerSearch;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class BillFragment extends Fragment implements BillInterface.View, View.OnClickListener {


    private OnFragmentInteractionListener mListener;
    private Button addBillButton;

    private BillSingleSpinnerSearch billSingleSpinnerSearch;
    private EditText price;

    public static BillInterface.Presenter billPresenter;

    private PlaceModel chosenPlace;

    private View addBillProgressBar;

    public BillFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        addBillButton = view.findViewById(R.id.addBill_button);
        addBillButton.setOnClickListener(this);

        billSingleSpinnerSearch = view.findViewById(R.id.bill_searchSingleSpinner);
        price = view.findViewById(R.id.price);

        addBillProgressBar = view.findViewById(R.id.addBill_progress);

        billPresenter.getView(this);
        billPresenter.setPlacesOnSpinner();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBill_button:
                getBillDetails();
        }
    }

    @Override
    public void getBillDetails() {
        if (price.getText().length() > 0){
            int priceInt =Integer.parseInt(price.getText().toString());
            if(chosenPlace != null){
                showProgress(true);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date date = new java.util.Date();

                BillModel billModel = new BillModel(priceInt, dateFormat.format(date), chosenPlace.getAddress(), chosenPlace.getName(), chosenPlace.getLatLng().longitude, chosenPlace.getLatLng().latitude);
                billPresenter.setBillDetails(billModel);
            }else{
                Toast.makeText(getContext(), "Wybierz miejsce", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Podaj wartość rachunku", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPlacesOnSpinner(List<PlaceModel> placeModelList) {
        billSingleSpinnerSearch.setLimit(1, new BillSingleSpinnerSearch.LimitExceedListener() {
            @Override
            public void onLimitListener(PlaceModel data) {

            }
        });
        billSingleSpinnerSearch.setItems(placeModelList, -1, new SingleSpinnerListener() {
            @Override
            public void onItemsSelected(List<PlaceModel> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.d("LOG", items.get(i).getName());
                        chosenPlace = (items.get(i));
                    }
                }
            }
        });
    }

    @Override
    public void addedSuccess() {
        showProgress(false);
        Toast.makeText(getContext(), "Dodano nowy rachunek", Toast.LENGTH_SHORT).show();
        price.setText(null);
//        chosenPlace = null;
//        billPresenter.setPlacesOnSpinner();
    }

    @Override
    public void addedFailure() {
        showProgress(false);
        Toast.makeText(getContext(), "Nie udało się dodać rachunku", Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            addBillProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
            addBillProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    addBillProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            addBillProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            addBillProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    addBillProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            addBillProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            addBillProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
