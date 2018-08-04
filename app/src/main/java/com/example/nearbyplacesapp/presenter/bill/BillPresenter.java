package com.example.nearbyplacesapp.presenter.bill;

import android.util.Log;
import android.widget.Toast;

import com.example.nearbyplacesapp.contract.bill.BillInterface;
import com.example.nearbyplacesapp.model.places.bill.BillModel;
import com.example.nearbyplacesapp.model.places.bill.PlaceModel;
import com.example.nearbyplacesapp.services.BillService;
import com.google.android.gms.location.places.Place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BillPresenter implements BillInterface.Presenter {

    private String baseUrl = "http://timwat.azurewebsites.net/";

    private BillInterface.View billView;
    private String token;

    private BillService billService;
    private BillModel billModel;
    private List<Place> placeList;


    public BillPresenter() {
    }

    @Override
    public void getView(BillInterface.View view) {
        this.billView = view;
    }

    @Override
    public void addBill() {
        Log.d("LOG", "addBill()");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().header("Cookie", ".AspNetCore.Identity.Application=" + token).build();
                return chain.proceed(request);
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        Retrofit retrofit = builder.build();
        billService = retrofit.create(BillService.class);

        //billModel = new BillModel(13130, "2018-07-25 17:45:00", "gen. Urbanowicza 21a, 01-476 Warszawa, Polska", "Archimedes", 52, 21);

        Call<ResponseBody> call = billService.addBill(billModel.getPrice(), billModel.getDate(), billModel.getAdress(), billModel.getPlaceName(), billModel.getGeoLong(), billModel.getGeoLat());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    billView.addedSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                billView.addedFailure();
            }
        });
    }

    @Override
    public void setBillDetails(BillModel billModel) {
       this.billModel = billModel;
       addBill();
    }

    @Override
    public void getToken(String token) {
        this.token = token;
    }

    @Override
    public void setListPlaces(List<Place> listPlaces) {
        this.placeList = listPlaces;
    }

    @Override
    public void setPlacesOnSpinner() {
        List<PlaceModel> placeModelList = new ArrayList<>();
        for (int i = 0; i < placeList.size(); i++) {
            PlaceModel placeModel = new PlaceModel();
            placeModel.setId(i+1);
            placeModel.setName(placeList.get(i).getName().toString());
            if(!placeList.get(i).getAddress().toString().isEmpty())placeModel.setAddress(placeList.get(i).getAddress().toString());
            placeModel.setLatLng(placeList.get(i).getLatLng());
            placeModel.setSelected(false);
            placeModelList.add(placeModel);
        }
        billView.setPlacesOnSpinner(placeModelList);
    }

}
