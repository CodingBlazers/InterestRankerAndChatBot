package com.codingblocks.ChatBot_And_InterestRanker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bhavya Sapra on 31-01-2017.
 */

public class repositories {
    @SerializedName("name")
    public String repoName;

    public String getrepo_name() {
        return repoName;
    }
}
