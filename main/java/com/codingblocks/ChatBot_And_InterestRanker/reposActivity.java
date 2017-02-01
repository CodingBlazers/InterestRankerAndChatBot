package com.codingblocks.ChatBot_And_InterestRanker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.codingblocks.customnavigationdrawer.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhavya Sapra on 31-01-2017.
 */



public  class reposActivity extends AppCompatActivity {
ArrayList<repositories> repository1=new ArrayList<>();
   repositoryAdapter adapter;
    ProgressDialog progressDialog;
ListView reposList1;
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     //requestWindowFeature(Window.FEATURE_PROGRESS);
     setContentView(R.layout.activity_repos);
     setTitle("Repository List");
     final Intent intent = getIntent();
     String user_name = intent.getStringExtra("Username");
        reposList1 = (ListView) findViewById(R.id.reposListView);

     Call<repositoryList> call = ApiClient.getApiInterface().getDetails(user_name);
//     ProgressDialog progressDialog;
     progressDialog=new ProgressDialog(reposActivity.this);
     progressDialog.setTitle("Fetching Repositories");
     progressDialog.setMessage("Wait");
     progressDialog.show();


     call.enqueue(new Callback<repositoryList>() {
         @Override
         public void onResponse(Call<repositoryList> call, Response<repositoryList> response) {
             progressDialog.dismiss();
             if (response.isSuccessful()) {
                 repositoryList reposlist = response.body();
                  repository1 = reposlist.getreposlist;


                 adapter=new repositoryAdapter(reposActivity.this,repository1);
                 reposList1.setAdapter(adapter);

             }
             else
                 Toast.makeText(reposActivity.this, response.message() + response.code(), Toast.LENGTH_LONG).show();


         }

         @Override
         public void onFailure(Call<repositoryList> call, Throwable t) {
             progressDialog.dismiss();
             Toast.makeText(reposActivity.this, "On Failure", Toast.LENGTH_LONG).show();

         }

     });

        }

//    private void ShowProgressDialog() {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(reposActivity.this);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(false);
//        }
//        progressDialog.setMessage("Analysing common interest of student..");
//        progressDialog.show();
//    }
//
//    private void DismissProgressDialog() {
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//        }
//    }
}