package com.codingblocks.ChatBot_And_InterestRanker;

/**
 * Created by Sachin on 9/3/2016.
 */
public class BatchModel {
    int id;
    String batch_name;

    public BatchModel(int id,String batch_name) {
        this.id=id;
        this.batch_name = batch_name;
    }

    public BatchModel(String batch_name){
        this.batch_name=batch_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }
}
