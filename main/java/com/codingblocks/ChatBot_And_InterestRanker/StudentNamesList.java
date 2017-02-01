package com.codingblocks.ChatBot_And_InterestRanker;

import android.annotation.TargetApi;
import android.app.Application;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.codingblocks.ChatBot_And_InterestRanker.DBMS.BatchTable;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.MyDatabase;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.StudentTable;
import com.codingblocks.customnavigationdrawer.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

import java.util.List;

public class StudentNamesList extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ListView studentlist;


    StudentModel studentModel;
    ArrayList<StudentModel> student_list;
    List<String> student_names;
    List<String> user_id_list;

    //int batch_id;
    String batch_name;
    ProgressDialog progressDialog;
    public String UserNames;
    public static  Context m_context;


    public StudentNamesList() {
        m_context=StudentNamesList.this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_studentnameslist);
        setTitle("Students List");

        user_id_list = new ArrayList<>();

        final Intent intent = getIntent();
        batch_name = intent.getStringExtra("Batch_Name");
        Log.i("BatchID_inStudentNames", batch_name + "");
        refresh();

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setBackgroundDrawable(R.drawable.fab)
                //.setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView ItemIconOne = new ImageView(this);
        ItemIconOne.setImageDrawable(getDrawable(R.drawable.add_student));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);

        ImageView ItemIconTwo = new ImageView(this);
        ItemIconTwo.setImageDrawable(getDrawable(R.drawable.analyse));
        SubActionButton button1 = itemBuilder.setContentView(ItemIconOne).setLayoutParams(new FrameLayout.LayoutParams(250, 250)).build();
        SubActionButton button2 = itemBuilder.setContentView(ItemIconTwo).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .attachTo(actionButton)
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
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

                        studentModel = new StudentModel(user_name.getText().toString(), user_id.getText().toString(), batch_name);
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


        studentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                student_names = new ArrayList<>();
                final SQLiteDatabase db = MyDatabase.getInstance(StudentNamesList.this).getReadableDatabase();
                student_list = StudentTable.getByArg(db, batch_name);
                for (int i = 0; i < student_list.size(); i++) {
                    student_names.add(student_list.get(i).getStudent_name());
                    user_id_list.add(student_list.get(i).getUser_name());
                }

                Intent intent = new Intent();
                intent.setClass(StudentNamesList.this, reposActivity.class);
                intent.putExtra("Username", user_id_list.get(position).toString()/*adapter.getItem(position).toString()*/);
                startActivity(intent);
            }
        });












//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                ShowProgressDialog();
//
//                UserNames = "";
//                for (int i = 0; i < user_id_list.size(); i++) {
//                    UserNames = UserNames + user_id_list.get(i).toString() + ",";
//                }
//                Log.i("User", UserNames);
//                Intent intent1 = new Intent();
//                intent1.setClass(StudentNamesList.this, StudentsInterestList.class);
//                intent1.putExtra("UserNames", UserNames);
//                startActivity(intent1);
//
//            }
//        });


    }

//    public void updateStudentNamesDatabase(int batch_ID){
//
//        SQLiteDatabase db1 = MyDatabase.getInstance(this).getWritableDatabase();
//        StudentTable.deleteById(db1,batch_ID);
//        db1.close();
//
//    }
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
       studentlist=(ListView) findViewById(R.id.Studentslistview);
        student_names = new ArrayList<>();
        final SQLiteDatabase db = MyDatabase.getInstance(StudentNamesList.this).getReadableDatabase();
        student_list = StudentTable.getByArg(db, batch_name);
        if (student_list == null) {

        } else {
            for (int i = 0; i < student_list.size(); i++) {
                student_names.add(student_list.get(i).getStudent_name());
                user_id_list.add(student_list.get(i).getUser_name());
            }
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, student_names);
            studentlist.setAdapter(adapter);
        }
    }
}