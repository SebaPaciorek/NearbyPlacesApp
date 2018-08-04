package com.example.nearbyplacesapp.contract.internetConnection;

public interface InternetConnectionContract {

    interface View{
        void showInternetTextView(boolean isConnected);
    }

    interface Presenter{
        void changedStatusInternet(boolean isConnected);
        void getView(InternetConnectionContract.View view);
    }
}
