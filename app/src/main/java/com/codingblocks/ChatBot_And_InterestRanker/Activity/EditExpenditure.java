package com.codingblocks.ChatBot_And_InterestRanker.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.codingblocks.ChatBot_And_InterestRanker.Constants;
import com.codingblocks.ChatBot_And_InterestRanker.Fragments.ExpenditureManagement;
import com.codingblocks.ChatBot_And_InterestRanker.Models.ExpenditureModel;
import com.codingblocks.eventerest.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by megha on 29/01/17.
 */

public class EditExpenditure extends AppCompatActivity implements Constants {

    Button positiveButton, negativeButton;
    TextView descriptionTextView, typeTextView, amountTextView;
    ListView notesListView;
    Spinner amountUnitSpinner;

    ExpenditureModel expenditureModel;
    ArrayList<String> notes;
    ArrayAdapter<String> notesAdapter;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenditure);

        descriptionTextView = (TextView) findViewById(R.id.edit_description);
        typeTextView = (TextView) findViewById(R.id.edit_type);
        amountTextView = (TextView) findViewById(R.id.edit_amount);
        notesListView = (ListView) findViewById(R.id.edit_notes_list_view);
        amountUnitSpinner = (Spinner) findViewById(R.id.edit_amount_unit);

        notes = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(notesAdapter);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditExpenditure.this);
                builder.setTitle("Add notes");
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_add_notes, null);
                final EditText notesTextView = (EditText) v.findViewById(R.id.note_edit_text);
                notesTextView.setText(notes.get(position));
                builder.setView(v);
                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.set(position, notesTextView.getText().toString());
                        notesAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(position);
                        notesAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                builder.create().show();
            }
        });

        Intent passedIntent = getIntent();
        try{
            expenditureModel = (ExpenditureModel) passedIntent.getSerializableExtra(INTENT_EDITED_EXPENDITURE_ITEM);
            id = expenditureModel.getID();
            descriptionTextView.setText(expenditureModel.getDescription());
            typeTextView.setText(expenditureModel.getType());
            amountTextView.setText(expenditureModel.getAmountRecord() + "");
            if(expenditureModel.getNotes() != null) {
                for (String s : expenditureModel.getNotes()) {
                    notes.add(s);
                }
            }
        }
        catch (Exception e){
            id = -1;
        }

        Spinner spinner = (Spinner) findViewById(R.id.edit_amount_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        positiveButton = (Button) findViewById(R.id.edit_expenditure_positive);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenditureModel resultExpenditureModel = new ExpenditureModel(
                        id,
                        descriptionTextView.getText().toString(),
                        typeTextView.getText().toString(),
                        Double.parseDouble(amountTextView.getText().toString()),
                        amountUnitSpinner.getSelectedItem().toString(),
                        "01-01-2017",
                        1,
                        ExpenditureModel.stringArrayToString(notes),
                        null,
                        null );
                Intent resultIntent = new Intent(EditExpenditure.this, ExpenditureManagement.class);
                resultIntent.putExtra(INTENT_EDITED_EXPENDITURE_ITEM, resultExpenditureModel);
                resultIntent.putExtra(INTENT_EDITING_EXPENDITURE_ID, id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        negativeButton = (Button) findViewById(R.id.edit_expenditure_negative);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(EditExpenditure.this, ExpenditureManagement.class);
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });

        FloatingActionButton fab = new FloatingActionButton.Builder(this)
                .setBackgroundDrawable(R.drawable.fab)
                .build();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditExpenditure.this);
                builder.setTitle("Add notes");
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_add_notes, null);
                final EditText notesTextView = (EditText) v.findViewById(R.id.note_edit_text);
                builder.setView(v);
                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.add(notesTextView.getText().toString());
                        notesAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                builder.create().show();
            }
        });
    }

}
