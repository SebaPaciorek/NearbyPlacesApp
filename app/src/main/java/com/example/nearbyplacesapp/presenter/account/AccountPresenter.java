package com.example.nearbyplacesapp.presenter.account;

import android.util.Log;

import com.example.nearbyplacesapp.contract.account.AccountInterface;
import com.example.nearbyplacesapp.database.User;

import io.realm.Realm;

public class AccountPresenter implements AccountInterface.Presenter {

    private Realm realm;
    private AccountInterface.View view;

    public AccountPresenter() {
    }

    @Override
    public void getRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void clearCredentials() {
        try{
            realm.beginTransaction();
            if (!realm.where(User.class).findAll().isEmpty()) {
                User user = realm.where(User.class).equalTo("id", 1).findFirst();
                user.setEmail("");
                user.setPassword("");
                realm.copyToRealmOrUpdate(user);
            }
            realm.commitTransaction();
            view.displayLoginActivity();
        }catch (Exception e){
        }
    }

    @Override
    public void getView(AccountInterface.View view) {
        this.view = view;
    }
}
