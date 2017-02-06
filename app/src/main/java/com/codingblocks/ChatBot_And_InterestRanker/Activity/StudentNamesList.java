package com.codingblocks.ChatBot_And_InterestRanker.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codingblocks.ChatBot_And_InterestRanker.Constants;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.MyDatabase;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.StudentTable;
import com.codingblocks.ChatBot_And_InterestRanker.Models.StudentModel;
import com.codingblocks.ChatBot_And_InterestRanker.Adapters.RecyclerAdapter;
import com.codingblocks.eventerest.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

import java.util.List;


/*
This activity corresponds to the list of present students in a batch (User chooses from StudentBatches activity) and
allow him to add or delete student to the batch.
 */

public class StudentNamesList extends AppCompatActivity implements Constants{

    RecyclerView recyclerView;

    StudentModel studentModel;
    ArrayList<StudentModel> student_list;
    List<String> student_names;
    List<String> user_id_list;
    RecyclerAdapter adapter;
    ProgressDialog progressDialog;
    public String UserNames;
    public static  Context m_context;
    String batchName;


    public StudentNamesList() {
        m_context=StudentNamesList.this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_recycler_view);
        setTitle("Students List");

        user_id_list = new ArrayList<>();

        final Intent intent = getIntent();
        batchName = intent.getStringExtra(INTENT_BATCH_NAME);
        Log.i("BatchID_inStudentNames", batchName + "");
        refresh();

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setBackgroundDrawable(R.drawable.fab)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView itemIconOne = new ImageView(this);
        itemIconOne.setImageDrawable(getDrawable(R.drawable.add_student));

        ImageView itemIconTwo = new ImageView(this);
        itemIconTwo.setImageDrawable(getDrawable(R.drawable.analyse));

        ImageView itemIconThree = new ImageView(this);
        itemIconThree.setImageDrawable(getDrawable(R.drawable.payment));

        SubActionButton buttonAddStudent = itemBuilder.setContentView(itemIconOne).setLayoutParams(new FrameLayout.LayoutParams(150, 150)).build();
        SubActionButton buttonViewInterest = itemBuilder.setContentView(itemIconTwo).setLayoutParams(new FrameLayout.LayoutParams(150, 150)).build();
        SubActionButton buttonManageExpenditure = itemBuilder.setContentView(itemIconThree).setLayoutParams(new FrameLayout.LayoutParams(150, 150)).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonAddStudent)
                .addSubActionView(buttonViewInterest)
                .addSubActionView(buttonManageExpenditure)
                .attachTo(actionButton)
                .build();

        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentNamesList.this);
                builder.setTitle("Add new Student and its UserId");

                LinearLayout layout = new LinearLayout(StudentNamesList.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                // Set up the input
                final EditText user_name = new EditText(StudentNamesList.this);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                user_name.setInputType(InputType.TYPE_CLASS_TEXT);
                user_name.setHint("User Name");
                layout.addView(user_name);

                final EditText user_id = new EditText(StudentNamesList.this);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                user_id.setInputType(InputType.TYPE_CLASS_TEXT);
                user_id.setHint("User Id");
                layout.addView(user_id);

                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        studentModel = new StudentModel(user_name.getText().toString(), user_id.getText().toString(), batchName);
                        final SQLiteDatabase db = MyDatabase.getInstance(StudentNamesList.this).getWritableDatabase();
                        StudentTable.save(db, studentModel);
                        db.close();
                        refresh();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        buttonViewInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserNames = "";
                for (int i = 0; i < user_id_list.size(); i++) {
                    UserNames = UserNames + user_id_list.get(i).toString() + ",";
                }
                Log.i("User", UserNames);
                Intent intent1 = new Intent();
                intent1.setClass(StudentNamesList.this, StudentsInterestList.class);
                intent1.putExtra("UserNames", UserNames);
                startActivity(intent1);

            }
        });

        buttonManageExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StudentNamesList.this, ExpenditureManagement.class);
                intent.putExtra(INTENT_BATCH_NAME, batchName);
                startActivity(intent);
            }
        });
    }

    private void ShowProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(StudentNamesList.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage("Analysing common interest of student..");
        progressDialog.show();
    }

    private void DismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void refresh() {
        student_names = new ArrayList<>();
        final SQLiteDatabase db = MyDatabase.getInstance(StudentNamesList.this).getReadableDatabase();
        student_list = StudentTable.getByArg(db, batchName);
        if (student_list == null) {

        } else {
            for (int i = 0; i < student_list.size(); i++) {
                student_names.add(student_list.get(i).getStudent_name());
                user_id_list.add(student_list.get(i).getUser_name());
            }
            adapter = new RecyclerAdapter(this, student_names);
            recyclerView = (RecyclerView) findViewById(R.id.student_list_recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}