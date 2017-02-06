package com.codingblocks.ChatBot_And_InterestRanker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codingblocks.ChatBot_And_InterestRanker.Activity.StudentsInterestList;
import com.codingblocks.eventerest.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

/**
 * Created by HIman$hu on 9/4/2016.
 */

public class ExpandableTvRecyclerAdapter extends RecyclerView.Adapter<ExpandableTvRecyclerAdapter.RecyclerViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<String> courseDetail;

    public ExpandableTvRecyclerAdapter(StudentsInterestList context, ArrayList<String> courseDescription) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.courseDetail=courseDescription;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.courses_list, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

//        Log.i("ABC",courseDetail.get(position).getDesc().toString());
        holder.expandableTextView.setText(courseDetail.get(position).toString());

//        holder.expandableTextView.setText(courseDetail.get(position).getName() + "\n" + courseDetail.get(position).getDesc());
//        holder.expandableTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, holder.tv1.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();
            int position = vholder.getPosition();

            Toast.makeText(context, "This is position " + position, Toast.LENGTH_LONG).show();
        }
    };


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ExpandableTextView expandableTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);

        }
    }

}




