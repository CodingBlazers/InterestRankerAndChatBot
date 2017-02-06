package com.codingblocks.ChatBot_And_InterestRanker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.codingblocks.ChatBot_And_InterestRanker.Adapters.ExpendituresAdapter;
import com.codingblocks.ChatBot_And_InterestRanker.Constants;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.ExpenditureTable;
import com.codingblocks.ChatBot_And_InterestRanker.Models.ExpenditureModel;
import com.codingblocks.eventerest.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by megha on 29/01/17.
 */

// Activity for management of expenditures during an event

public class ExpenditureManagement extends AppCompatActivity implements Constants{

    RecyclerView expenditureRecyclerView;
    ExpendituresAdapter expendituresAdapter;
    String batchName;
    ArrayList<ExpenditureModel> expenditureModelArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditures);
        setTitle(TITLE_EXPENDITURE_MANAGEMENT);
        expenditureRecyclerView = (RecyclerView) findViewById(R.id.expenditure_list_view);

        Intent passedIntent = getIntent();
        batchName = passedIntent.getStringExtra(INTENT_BATCH_NAME);

        expenditureModelArrayList = ExpenditureTable.getExpendituresByBatch(this, batchName);
        expendituresAdapter = new ExpendituresAdapter(this, expenditureModelArrayList);
        expenditureRecyclerView.setAdapter(expendituresAdapter);
        expenditureRecyclerView.setHasFixedSize(true);
        expenditureRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = new FloatingActionButton.Builder(this)
                .setBackgroundDrawable(R.drawable.fab)
                .build();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ExpenditureManagement.this, EditExpenditure.class);
                intent.putExtra(INTENT_BATCH_NAME, batchName);
                startActivityForResult(intent, REQUEST_CODE_ADD_EXPENDITURE);
            }
        });

        expenditureRecyclerView.addOnItemTouchListener(
                new ExpenditureRecyclerItemClickListener(ExpenditureManagement.this, new ExpenditureRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent();
                        intent.setClass(ExpenditureManagement.this, EditExpenditure.class);
                        intent.putExtra(INTENT_BATCH_NAME, batchName);
                        intent.putExtra(INTENT_EDITING_EXPENDITURE_ID, expenditureModelArrayList.get(position).getID());
                        startActivityForResult(intent, REQUEST_CODE_EDIT_EXPENDITURE);
                    }
                })
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if(requestCode == REQUEST_CODE_ADD_EXPENDITURE) {
            if (resultCode == RESULT_OK) {
                // Make sure the request was successful
                ExpenditureModel expenditure = (ExpenditureModel) data.getSerializableExtra(INTENT_EDITED_EXPENDITURE_ITEM);
                ExpenditureTable.addExpenditureInDB(ExpenditureManagement.this, expenditure);
                expenditureModelArrayList.add(ExpenditureTable.getExpendituresByDescription(ExpenditureManagement.this, expenditure.getDescription()));
                expendituresAdapter.notifyDataSetChanged();
            }
        }
        if(requestCode == REQUEST_CODE_EDIT_EXPENDITURE) {
            if (resultCode == RESULT_OK) {
                // Make sure the request was successful
                ExpenditureModel expenditure = (ExpenditureModel) data.getSerializableExtra(INTENT_EDITED_EXPENDITURE_ITEM);
                ExpenditureTable.updateExpenditureInDB(ExpenditureManagement.this, expenditure, data.getIntExtra(INTENT_EDITING_EXPENDITURE_ID, -1));
                for(int i=0; i<expenditureModelArrayList.size(); i++){
                    ExpenditureModel ex = expenditureModelArrayList.get(i);
                    if(ex.getID() == expenditure.getID()) {
                        expenditureModelArrayList.set(i, expenditure);
                        expendituresAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    // Class to handle click on an item in expenditure list
    private static class ExpenditureRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public ExpenditureRecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
    }

}
