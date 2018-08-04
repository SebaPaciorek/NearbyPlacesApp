package com.example.nearbyplacesapp.view.places;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.places.ChosenPlaceInterface;
import com.example.nearbyplacesapp.presenter.places.PlacesRecyclerViewAdapter;

public class ChosenPlaceFragment extends Fragment implements ChosenPlaceInterface.View {

    private ChosenPlaceInterface.Presenter chosenPlacePresenter;
    private TextView nameTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chosenPlacePresenter = PlacesRecyclerViewAdapter.chosenPlacePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chosen_place, container, false);

        chosenPlacePresenter.getView(this);
        chosenPlacePresenter.getAndroidView(view);
        chosenPlacePresenter.getContext(getContext());
        showPlace();
        return view;
    }

    @Override
    public void showPlace() {
        chosenPlacePresenter.getPlaceData();
    }
}
