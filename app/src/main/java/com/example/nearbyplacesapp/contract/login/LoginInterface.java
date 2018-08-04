package com.example.nearbyplacesapp.contract.login;

import io.realm.Realm;

public interface LoginInterface {
    interface View{
        void getCredentials();
        void wrongCredentials();
        void rightCredentials();
        void error(String message);
        void startMainActivity();
        void showProgress(final boolean show);
    }

    interface StartView{
        void startLoginActivity();
        void startMainActivity();
        void error(String message);
    }

    interface Presenter{
        void getView(LoginInterface.View view);
        void logIn(boolean isStartActivity);
        void setCredentials(String email, String password);
        void rememberUserCredentials();
        void getRealm(Realm realm);
        void getStartView(LoginInterface.StartView startView);
    }
}
