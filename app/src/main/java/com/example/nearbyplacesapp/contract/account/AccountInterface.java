package com.example.nearbyplacesapp.contract.account;

import io.realm.Realm;

public interface AccountInterface {

    interface View{
        void logOut();
        void displayLoginActivity();

    }

    interface Presenter{
        void getRealm(Realm realm);
        void clearCredentials();
        void getView(AccountInterface.View view);
    }
}
