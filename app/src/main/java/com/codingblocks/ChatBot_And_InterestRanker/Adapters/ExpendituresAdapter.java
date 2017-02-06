package com.codingblocks.ChatBot_And_InterestRanker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingblocks.ChatBot_And_InterestRanker.Models.ExpenditureModel;
import com.codingblocks.eventerest.R;

import java.util.ArrayList;

/**
 * Created by megha on 29/01/17.
 */

public class ExpendituresAdapter extends RecyclerView.Adapter<ExpendituresAdapter.ExpenditureListItemViewHolder> {

    Context context;
    ArrayList<ExpenditureModel> expenditureList = new ArrayList<>();
    LayoutInflater inflater;

    public ExpendituresAdapter(Context context, ArrayList<ExpenditureModel> expenditureList){
        this.context = context;
        this.expenditureList = expenditureList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ExpenditureListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.expenditure_list_item, parent, false);
        ExpenditureListItemViewHolder viewHolder = new ExpenditureListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExpenditureListItemViewHolder holder, int position) {
        ExpenditureModel expenditure = expenditureList.get(position);
        holder.descriptionTextView.setText(expenditure.getDescription());
        holder.amountTextView.setText(expenditure.getAmountRecord() + "");
        holder.amountTypeTextView.setText(expenditure.getAmountUnit());
    }

    @Override
    public int getItemCount() {
        return expenditureList.size();
    }

    public static class ExpenditureListItemViewHolder extends RecyclerView.ViewHolder {

        public TextView descriptionTextView, amountTextView, amountTypeTextView;
        public ExpenditureListItemViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            amountTextView = (TextView) itemView.findViewById(R.id.amount);
            amountTypeTextView = (TextView) itemView.findViewById(R.id.amount_unit);
        }
    }

}
