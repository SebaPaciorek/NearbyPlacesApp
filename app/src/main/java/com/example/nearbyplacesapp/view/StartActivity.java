package com.example.nearbyplacesapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.login.LoginInterface;
import com.example.nearbyplacesapp.database.User;
import com.example.nearbyplacesapp.presenter.login.LoginPresenter;

import io.realm.Realm;
import io.realm.RealmResults;

public class StartActivity extends AppCompatActivity implements LoginInterface.StartView{

    private Realm realm;
    private Handler handler;
    private LoginInterface.Presenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        this.getSupportActionBar().hide();

        handler = new Handler();
        loginPresenter = new LoginPresenter();
        loginPresenter.getStartView(this);
        LoginActivity.loginPresenter = loginPresenter;
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    RealmResults<User> users = realm.where(User.class).findAll();
                    realm.commitTransaction();
                    if (!users.isEmpty()) {
                        try {
                            String email = users.first().getEmail();
                            String password = users.first().getPassword();
                            loginPresenter.getRealm(realm);
                            loginPresenter.setCredentials(email, password);
                            Log.d("LOG", "!users.isEmpty()");
                            loginPresenter.logIn(true);
                            //startMainActivity();

                        } catch (NullPointerException e) {

                        }

                    } else {
                        Log.d("LOG", "users.isEmpty()");
                        startLoginActivity();
                    }

                }
            }, 1000);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPause() {
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
    @Override
    public void error(String message){
        Toast.makeText(this,  message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void startMainActivity() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
