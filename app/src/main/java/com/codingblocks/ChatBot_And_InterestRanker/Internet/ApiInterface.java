package com.codingblocks.ChatBot_And_InterestRanker.Internet;

import com.codingblocks.ChatBot_And_InterestRanker.Models.CourseDescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HIman$hu on 9/4/2016.
 */
public interface ApiInterface {

    @GET("topics.php")
    Call<List<CourseDescription>> getDetails(@Query("user") String userNames);

}
