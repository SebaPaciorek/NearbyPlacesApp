package com.example.nearbyplacesapp.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("/Account/Login")
    Call<ResponseBody> logIn(@Field("Email") String email, @Field("Password") String password);
}
