package com.codingblocks.ChatBot_And_InterestRanker.Internet;

import com.codingblocks.ChatBot_And_InterestRanker.Internet.ApiInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HIman$hu on 9/4/2016.
 */

/*
Following class creates an ApiClient for Retrofit for calls to server.
 */

public class ApiClient {
    private static ApiInterface mService;
    private static String baseUrl = "https://interests-ranker.herokuapp.com/";
    private static int timeout = 300;

    public static ApiInterface getInterface() {
        if (mService == null) {
            Retrofit retrofit = returnRetrofitClient();
            mService = retrofit.create(ApiInterface.class);
        }
        return mService;
    }

    private static Retrofit returnRetrofitClient(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
