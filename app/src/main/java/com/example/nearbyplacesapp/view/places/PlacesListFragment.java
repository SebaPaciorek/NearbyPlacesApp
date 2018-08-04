package com.example.nearbyplacesapp.view.places;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearbyplacesapp.SpinnerListener;
import com.example.nearbyplacesapp.model.places.PlaceType;
import com.example.nearbyplacesapp.presenter.places.PlacesRecyclerViewAdapter;
import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.places.PlacesListInterface;
import com.example.nearbyplacesapp.presenter.places.PlacesListPresenter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class PlacesListFragment extends Fragment implements PlacesListInterface.View {

    private PlacesListInterface.Presenter presenter;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private LinearLayoutManager recyclerLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private SearchView searchView;
    private MultiSpinnerSearch multiSpinnerSearch;
    private TextView emptyListView;

    private Collection<Integer> filter = new ArrayList<>();

    private int counter = 0;
    private List<PlaceType> listPlaces;

    private View searchPlaceProgressBar;


    public PlacesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        presenter = new PlacesListPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);

        recyclerView = view.findViewById(R.id.places_list);
        searchView = view.findViewById(R.id.searchView);
        emptyListView = view.findViewById(R.id.emptyListView);
        searchPlaceProgressBar = view.findViewById(R.id.search_places_progress);

        multiSpinnerSearch = view.findViewById(R.id.searchMultiSpinnerUnlimited);

        setSearchViewListener();
        setLayoutManager();
        setDividerItemDecoration();

        presenter.getView(this);
        presenter.getContext(getContext());
        presenter.getActivity(getActivity());
        presenter.getCurrentPlaceItems();

        if (filter.isEmpty()) {
            listPlaces = setMultiSpinner();
        } else {
            showPlaces();
        }
        setSpinnerListener();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle outState = new Bundle();

        onSaveInstanceState(outState);
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void showPlacesList(PlacesRecyclerViewAdapter recyclerViewAdapter, List<Place> placeList) {
        showProgress(false);
        if (placeList.size() > 0) {
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            emptyListView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
        }

    }

    public void setLayoutManager() {
        recyclerLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerLayoutManager);
    }

    public void setDividerItemDecoration() {
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), recyclerLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Place place);
    }

    public void setSearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchPlace(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchPlace(newText);
                return true;
            }
        });
    }


    private List<PlaceType> setMultiSpinner() {
        Log.d("LOG", "setMultiSpinner()");

        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.place_types));
        final List<PlaceType> listPlaces = new ArrayList<>();
        List<Integer> listIDs = null;
        Collection<Integer> filters = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            PlaceType placeType = new PlaceType();
            placeType.setId(i + 1);
            placeType.setName(list.get(i).toString());
            placeType.setSelected(true);

            switch (list.get(i)) {
                case "Sklepy":
                    listIDs = new ArrayList<>();
                    listIDs.add(Place.TYPE_STORE);
                    listIDs.add(Place.TYPE_BICYCLE_STORE);
                    listIDs.add(Place.TYPE_BOOK_STORE);
                    listIDs.add(Place.TYPE_CLOTHING_STORE);
                    listIDs.add(Place.TYPE_CONVENIENCE_STORE);
                    listIDs.add(Place.TYPE_DEPARTMENT_STORE);
                    listIDs.add(Place.TYPE_ELECTRONICS_STORE);
                    listIDs.add(Place.TYPE_FURNITURE_STORE);
                    listIDs.add(Place.TYPE_HARDWARE_STORE);
                    listIDs.add(Place.TYPE_JEWELRY_STORE);
                    listIDs.add(Place.TYPE_LIQUOR_STORE);
                    listIDs.add(Place.TYPE_PET_STORE);
                    listIDs.add(Place.TYPE_SHOE_STORE);
                    listIDs.add(Place.TYPE_HOME_GOODS_STORE);
                    listIDs.add(Place.TYPE_SHOPPING_MALL);
                    listIDs.add(Place.TYPE_GROCERY_OR_SUPERMARKET);
                    placeType.setPlaceID(listIDs);
                    break;
                case "Gastronomia":
                    listIDs = new ArrayList<>();
                    listIDs.add(Place.TYPE_RESTAURANT);
                    listIDs.add(Place.TYPE_BAKERY);
                    listIDs.add(Place.TYPE_BAR);
                    listIDs.add(Place.TYPE_CAFE);
                    placeType.setPlaceID(listIDs);
                    break;

                case "Stacje paliw":
                    listIDs = new ArrayList<>();
                    listIDs.add(Place.TYPE_GAS_STATION);
                    placeType.setPlaceID(listIDs);
                    break;

                default:
                    listIDs = new ArrayList<>();
                    listIDs.addAll(PlaceTypes.ALL);
                    placeType.setPlaceID(listIDs);
                    break;
            }
            listPlaces.add(placeType);
        }

        for (int i = 0; i < listPlaces.size(); i++) {
            if (listPlaces.get(i).isSelected()) {
                filters.addAll(listPlaces.get(i).getPlaceID());
            }
        }
        filter = filters;
        showPlaces();

        return listPlaces;
//        multiSpinnerSearch.setLimit(1, new MultiSpinnerSearch.LimitExceedListener() {
//            @Override
//            public void onLimitListener(PlaceType data) {
//                Toast.makeText(getContext(), "limit wprowadzony", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void showPlaces() {
        presenter.filterPlaces(filter);
        emptyListView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        showProgress(true);
    }

    public void setSpinnerListener() {
        SpinnerListener spinnerListener = new SpinnerListener() {
            @Override
            public void onItemsSelected(List<PlaceType> items) {
                final Collection<Integer> filters = new ArrayList<>();

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        filters.addAll(items.get(i).getPlaceID());
                    }
                }
                filter = filters;
                presenter.filterPlaces(filters);
                emptyListView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                showProgress(true);
            }
        };

        multiSpinnerSearch.setItems(listPlaces, -1, spinnerListener);
    }


    @Override
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (getActivity() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                searchPlaceProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
                searchPlaceProgressBar.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        searchPlaceProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                searchPlaceProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                searchPlaceProgressBar.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        searchPlaceProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                searchPlaceProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                searchPlaceProgressBar.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }
    }


}
