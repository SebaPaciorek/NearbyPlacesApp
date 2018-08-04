package com.example.nearbyplacesapp.presenter.places;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.places.ChosenPlaceInterface;
import com.example.nearbyplacesapp.contract.places.ChosenPlaceOnMapInterface;
import com.google.android.gms.location.places.Place;

import java.util.List;

public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<PlacesRecyclerViewAdapter.ViewHolder> {

    private List<Place> placeList;
    private Context context;

    private PlacesRecyclerViewAdapter.ViewHolder holder;

    public static ChosenPlaceOnMapInterface.Presenter chosenPlaceOnMapPresenter;
    public static ChosenPlaceInterface.Presenter chosenPlacePresenter;

    public PlacesRecyclerViewAdapter(List<Place> placeList, Context context) {
        this.placeList = placeList;
        this.context = context;
        chosenPlaceOnMapPresenter = new ChosenPlaceOnMapPresenter();
        chosenPlaceOnMapPresenter.getContext(context);

        chosenPlacePresenter = new ChosenPlacePresenter();
    }

    @NonNull
    @Override
    public PlacesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_places_item, parent, false);
        PlacesRecyclerViewAdapter.ViewHolder viewHolder = new PlacesRecyclerViewAdapter.ViewHolder(view);
        this.holder = viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesRecyclerViewAdapter.ViewHolder holder, int position) {


        final Place place = placeList.get(position);

        holder.name.setText(place.getName());
        holder.address.setText(place.getAddress());
        holder.phone.setText(place.getPhoneNumber());

        if (place.getWebsiteUri() != null) {
            holder.website.setText(place.getWebsiteUri().toString());
        }

        if (place.getRating() > -1) {
            holder.ratingBar.setRating(place.getRating());
        } else {
            holder.ratingBar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView name;
        public TextView address;
        public TextView phone;
        public TextView website;
        public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            website = itemView.findViewById(R.id.website);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (getAdapterPosition() != -1) {
                Place chosenPlace = placeList.get(getAdapterPosition());
                chosenPlaceOnMapPresenter.getPlace(chosenPlace);
                chosenPlacePresenter.getPlace(chosenPlace);
                chosenPlaceOnMapPresenter.startChosenPlaceActivity();
                Log.d("LOG", "getPlaceTypes()" + placeList.get(getAdapterPosition()).getPlaceTypes().get(0).toString());
            }
            return false;
        }
    }

}
