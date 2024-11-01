package com.zohair.taskedapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
