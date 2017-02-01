package com.codingblocks.ChatBot_And_InterestRanker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HIman$hu on 9/4/2016.
 */
public interface ApiInterface {


    @GET("/users/:username/repos")
    Call<repositoryList> getDetails(@Query("username") String userName);
}
