package com.example.nearbyplacesapp.presenter.login;

import android.util.Log;

import com.example.nearbyplacesapp.InternetCheck;
import com.example.nearbyplacesapp.contract.bill.BillInterface;
import com.example.nearbyplacesapp.contract.login.LoginInterface;
import com.example.nearbyplacesapp.database.User;
import com.example.nearbyplacesapp.model.places.login.LoginModel;
import com.example.nearbyplacesapp.presenter.bill.BillPresenter;
import com.example.nearbyplacesapp.services.LoginService;
import com.example.nearbyplacesapp.view.bill.BillFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPresenter implements LoginInterface.Presenter {

    private LoginInterface.View loginView;
    private LoginInterface.StartView startView;
    private LoginService loginService;
    private LoginModel loginModel;
    private String baseUrl = "http://timwat.azurewebsites.net/";
    private String token;

    private String email = "";
    private String password = "";

    private Realm realm;

    private BillInterface.Presenter billPresenter;

    private InternetCheck internetCheck;


    public LoginPresenter() {
        billPresenter = new BillPresenter();
        internetCheck = new InternetCheck(internet ->{
            if(internet){
                Log.d("LOG", "Internet is on");
            }else{
                Log.d("LOG", "Internet is off");
            }
        });
    }

    @Override
    public void getView(LoginInterface.View view) {
        this.loginView = view;
    }

    @Override
    public void logIn(final boolean isStartActivity) {
        Log.d("LOG", "logIn");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        loginService = retrofit.create(LoginService.class);
        loginModel = new LoginModel(email, password);
        Call<ResponseBody> call = loginService.logIn(loginModel.getEmail(), loginModel.getPassword());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        Headers headers = response.raw().priorResponse().headers();
                        for (int i = 0; i < headers.size(); i++) {
                            Log.d("LOG", headers.name(i) + ": " + headers.value(i));

                            if (headers.value(i).contains(".AspNetCore.Identity.Application")) {
                                int beginIndex = headers.value(i).indexOf("=") + 1;
                                int endIndex = headers.value(i).indexOf(";");
                                token = headers.value(i).substring(beginIndex, endIndex);
                                billPresenter.getToken(token);
                                BillFragment.billPresenter = billPresenter;
                                rememberUserCredentials();
                                if (isStartActivity) {
                                    Log.d("LOG", "isStartActivity==true");
                                    startView.startMainActivity();
                                } else {
                                    Log.d("LOG", "isStartActivity==false");
                                    loginView.rightCredentials();
                                    loginView.startMainActivity();
                                }

                                Log.d("LOG", "token =    " + token);
                            }
                        }
                    } catch (NullPointerException e) {
                        Log.d("LOG", "cos nie poszlo");
                        if (isStartActivity) {
                            startView.startLoginActivity();
                        } else {
                            loginView.wrongCredentials();
                        }


                    }
                } else {
                    Log.d("LOG", "!response.isSuccessful() - account is not exist - erro400");
                    if (isStartActivity) {
                        startView.startLoginActivity();
                    } else {
                        loginView.wrongCredentials();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("LOG", "blad", t);
//                loginView.error(t.getMessage());
                startView.startLoginActivity();
                startView.error("Błąd połaczenia z siecią Internet.");
            }
        });
    }

    @Override
    public void setCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void rememberUserCredentials() {
        Log.d("LOG", "rememberUserCredentials ");
        realm.beginTransaction();

        if (realm.where(User.class).findAll().isEmpty()) {
            realm.createObject(User.class, 1);
        }

        User user = realm.where(User.class).equalTo("id", 1).findFirst();
        user.setEmail(email);
        user.setPassword(password);

        realm.copyToRealmOrUpdate(user);

        RealmResults<User> users = realm.where(User.class).findAll();
        realm.commitTransaction();
        Log.d("LOG", "rememberUserCredentials ");
        Log.d("LOG", "users: " + users.size());
    }

    @Override
    public void getRealm(Realm realm) {
        this.realm = realm;
    }


    @Override
    public void getStartView(LoginInterface.StartView startView) {
        this.startView = startView;
    }
}
