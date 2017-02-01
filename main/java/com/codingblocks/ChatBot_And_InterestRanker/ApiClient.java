package com.codingblocks.ChatBot_And_InterestRanker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.Streams;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HIman$hu on 9/4/2016.
 */
public class ApiClient {
    private static ApiInterface mService;

    public static ApiInterface getApiInterface(){
        if(mService==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService=retrofit.create(ApiInterface.class);
        }
        return mService;
    }
}
