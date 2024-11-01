package com.zohair.taskedapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebCalls {

    @GET("/quotes")
    Call<QuoteResponse> getQuotes();
}
