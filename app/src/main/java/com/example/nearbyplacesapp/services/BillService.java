package com.example.nearbyplacesapp.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BillService {

    @FormUrlEncoded
    @POST("/Statistics/AddBillWithPlaceToUser")
    Call<ResponseBody> addBill(@Field("Price") double Price, @Field("Date") String Date, @Field("Adress") String Adress, @Field("PlaceName") String PlaceName, @Field("GeoLong") double GeoLong, @Field("GeoLat") double GeoLat);

}
