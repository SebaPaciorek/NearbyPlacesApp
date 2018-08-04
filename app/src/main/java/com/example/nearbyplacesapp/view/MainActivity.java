package com.example.nearbyplacesapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.broadcast_receiver.InternetConnectorReceiver;
import com.example.nearbyplacesapp.contract.internetConnection.InternetConnectionContract;
import com.example.nearbyplacesapp.contract.account.AccountInterface;
import com.example.nearbyplacesapp.database.NearbyPlacesApplication;
import com.example.nearbyplacesapp.view.account.AccountActivity;
import com.example.nearbyplacesapp.view.bill.BillFragment;
import com.example.nearbyplacesapp.view.map.MapFragment;
import com.example.nearbyplacesapp.view.places.PlacesListFragment;

public class MainActivity extends AppCompatActivity implements InternetConnectionContract.View{

    static MainActivity mainActivity;

    private InternetConnectionContract.Presenter presenter;

    private TextView internetTextView;

    PlacesListFragment placesListFragment = new PlacesListFragment();
    MapFragment mapFragment = new MapFragment();
    BillFragment billFragment = new BillFragment();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_places:
                    fragmentTransaction.replace(R.id.fragmentContainer, placesListFragment).commit();
                    return true;
                case R.id.navigation_map:
                    fragmentTransaction.replace(R.id.fragmentContainer, mapFragment).commit();
                    return true;
                case R.id.navigation_bill:
                    fragmentTransaction.replace(R.id.fragmentContainer, billFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new InternetConnectorReceiver();

        internetTextView = findViewById(R.id.internetTextView);
        presenter.getView(this);

        mainActivity = this;


//        this.getSupportActionBar().hide();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new PlacesListFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NearbyPlacesApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        NearbyPlacesApplication.activityPaused();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_toolbar_navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.account:
                displayAccountActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayAccountActivity() {
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void showInternetTextView(boolean isConnected){
        if (isConnected){
            internetTextView.setVisibility(View.GONE);
        }else{
            internetTextView.setVisibility(View.VISIBLE);
        }
    }


    public static MainActivity getInstance(){
        return mainActivity;
    }
}
