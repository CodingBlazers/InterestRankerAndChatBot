package com.codingblocks.ChatBot_And_InterestRanker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.codingblocks.ChatBot_And_InterestRanker.Constants;
import com.codingblocks.ChatBot_And_InterestRanker.Models.ExpenditureModel;
import com.codingblocks.eventerest.R;

/**
 * Created by megha on 29/01/17.
 */

public class EditExpenditure extends AppCompatActivity implements Constants {

    Button positiveButton;
    TextView descriptionTextView, typeTextView, amountTextView;
    String batchName;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenditure);

        descriptionTextView = (TextView) findViewById(R.id.edit_description);
        typeTextView = (TextView) findViewById(R.id.edit_type);
        amountTextView = (TextView) findViewById(R.id.edit_amount);

        Intent passedIntent = getIntent();
        batchName = passedIntent.getStringExtra(INTENT_BATCH_NAME);
        try{
            id = getIntent().getIntExtra(INTENT_EDITING_EXPENDITURE_ID, -1);
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
                ExpenditureModel expenditureModel = new ExpenditureModel(
                        id,
                        batchName,
                        descriptionTextView.getText().toString(),
                        typeTextView.getText().toString(),
                        Double.parseDouble(amountTextView.getText().toString()),
                        "Rs.",
                        "01-01-2017",
                        1,
                        null,
                        null,
                        null );
                Intent resultIntent = new Intent(EditExpenditure.this, ExpenditureManagement.class);
                resultIntent.putExtra(INTENT_EDITED_EXPENDITURE_ITEM, expenditureModel);
                resultIntent.putExtra(INTENT_EDITING_EXPENDITURE_ID, id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

}
