package com.example.nearbyplacesapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.login.LoginInterface;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View, View.OnClickListener {

    private Realm realm;

    public static LoginInterface.Presenter loginPresenter;
    private Button loginButton;
    private AutoCompleteTextView emailText;
    private EditText passwordText;

    private String email;
    private String password;

    private View loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        realm = Realm.getDefaultInstance();

        //loginPresenter = new LoginPresenter();


        loginPresenter.getView(this);
        loginPresenter.getRealm(realm);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        loginProgress = findViewById(R.id.login_progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Log.d("LOG", "logIn    ");
                getCredentials();
        }
    }

    @Override
    public void getCredentials() {
        Log.d("LOG", "getCredentials    ");
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        if(email.length() == 0){
            Toast.makeText(this, "Podaj email", Toast.LENGTH_SHORT).show();
        }
        if(password.length() == 0){
            Toast.makeText(this, "Podaj hasło", Toast.LENGTH_SHORT).show();
        }

        if ((email.length() > 0) && (password.length() > 0)) {
            loginPresenter.setCredentials(email, password);
            showProgress(true);
            loginPresenter.logIn(false);
        }
    }

    @Override
    public void wrongCredentials() {
        showProgress(false);
        emailText.setText("");
        passwordText.setText("");
        Toast.makeText(this, "Zły email lub hasło", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rightCredentials() {
    }

    @Override
    public void error(String message) {
        showProgress(false);
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginProgress.setVisibility(show ? View.GONE : View.VISIBLE);
            loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginProgress.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loginProgress.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
