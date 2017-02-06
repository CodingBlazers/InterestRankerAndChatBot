package com.codingblocks.ChatBot_And_InterestRanker.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codingblocks.ChatBot_And_InterestRanker.Constants;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.BatchTable;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.MyDatabase;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.StudentTable;
import com.codingblocks.ChatBot_And_InterestRanker.Models.BatchModel;
import com.codingblocks.eventerest.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HIman$hu on 8/6/2016.
 */

/*
This activity shows a list of present batches of students and allow user to add or delete student batches.
 */

public class StudentBatches extends AppCompatActivity implements Constants{

    String m_Text;
    ListView batchesListView;
    List<String> batchesList;
    ArrayAdapter<String> adapter;
    BatchModel batchModel;
    EditText editText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_batches);
        setTitle(TITLE_STUDENT_BATCHES);
        batchesListView = (ListView) findViewById(R.id.batches_list_view);

        batchesList = new ArrayList<>();

        final SQLiteDatabase db = MyDatabase.getInstance(StudentBatches.this).getReadableDatabase();
        final ArrayList<BatchModel> batchesListDB = BatchTable.getByArg(db);
        db.close();
        if (batchesListDB != null) {
            for (int i = 0; i < batchesListDB.size(); i++) {
                batchesList.add(/*batchesListDB.get(i).getId() + " " +*/ batchesListDB.get(i).getBatch_name());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, batchesList);
            batchesListView.setAdapter(adapter);
        }

        FloatingActionButton fab = new FloatingActionButton.Builder(this)
                .setBackgroundDrawable(R.drawable.fab)
                .build();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentBatches.this);

                LayoutInflater inflater = StudentBatches.this.getLayoutInflater();
                View view1 = inflater.inflate(R.layout.new_batch_alert_dialog, null);
                builder.setView(view1);
                editText = (EditText) view1.findViewById(R.id.id_BatchName);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        m_Text = editText.getText().toString();

                        if (batchesList.contains(m_Text)) {
                            Toast.makeText(StudentBatches.this, "Batch is already added in record", Toast.LENGTH_SHORT).show();
                        }

                        else {

                            batchesList.add(m_Text);
                            adapter.notifyDataSetChanged();

                            //adapter = new ArrayAdapter<List<String>>(StudentBatches.this, android.R.layout.simple_list_item_1, batchesList);
                            //batchesListView.setAdapter(adapter);//After this step you should be able to see the data in your list view.

                            batchModel = new BatchModel(m_Text);
                            //batchModel = new BatchModel(m_Text);
                            //batch_count++;

                            SQLiteDatabase db = MyDatabase.getInstance(StudentBatches.this).getWritableDatabase();
                            BatchTable.save(db, batchModel);
                            db.close();
                        }
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

        batchesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentBatches.this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete it?");
                android.app.AlertDialog.Builder ok = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String batchName_toBeRemoved = batchesList.get(position);
                        batchesList.remove(batchName_toBeRemoved);
                        adapter.notifyDataSetChanged();

                        final SQLiteDatabase db = MyDatabase.getInstance(StudentBatches.this).getWritableDatabase();
                        BatchTable.deleteById(db, position + 1);

                        SQLiteDatabase db1 = MyDatabase.getInstance(StudentNamesList.m_context).getWritableDatabase();
                        StudentTable.deleteByBatchName(db1, batchName_toBeRemoved/*adapter.getItem(position).toString()*/);

                        db.close();
                        db1.close();

                    }
                });
                builder.create().show();
                return true;
            }
        });

        batchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("Batch_ID_InBatch", position + "");
                Intent intent = new Intent();
                intent.setClass(StudentBatches.this, StudentNamesList.class);
                intent.putExtra(INTENT_BATCH_NAME, batchesList.get(position).toString());
                startActivity(intent);
            }
        });
    }
}
