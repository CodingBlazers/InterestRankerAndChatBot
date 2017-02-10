package com.codingblocks.ChatBot_And_InterestRanker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.codingblocks.ChatBot_And_InterestRanker.Activity.EditExpenditure;
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

// Fragment for management of expenditures during an event

public class ExpenditureManagement extends Fragment implements Constants{

    RecyclerView expenditureRecyclerView;
    ExpendituresAdapter expendituresAdapter;
    ArrayList<ExpenditureModel> expenditureModelArrayList;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_expenditures, container, false);
        getActivity().setTitle(TITLE_EXPENDITURE_MANAGEMENT);
        expenditureRecyclerView = (RecyclerView) view.findViewById(R.id.expenditure_list_view);

        expenditureModelArrayList = ExpenditureTable.getExpenditures(getActivity());
        expendituresAdapter = new ExpendituresAdapter(getActivity(), expenditureModelArrayList);
        expenditureRecyclerView.setAdapter(expendituresAdapter);
        expenditureRecyclerView.setHasFixedSize(true);
        expenditureRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = new FloatingActionButton.Builder(getActivity())
                .setBackgroundDrawable(R.drawable.fab)
                .build();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditExpenditure.class);
                getActivity().startActivityForResult(intent, REQUEST_CODE_ADD_EXPENDITURE);
            }
        });

        expenditureRecyclerView.addOnItemTouchListener(
                new ExpenditureRecyclerItemClickListener(getActivity(), new ExpenditureRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), EditExpenditure.class);
                        intent.putExtra(INTENT_EDITED_EXPENDITURE_ITEM, expenditureModelArrayList.get(position));
                        getActivity().startActivityForResult(intent, REQUEST_CODE_EDIT_EXPENDITURE);
                    }
                })
        );
        return view;
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
