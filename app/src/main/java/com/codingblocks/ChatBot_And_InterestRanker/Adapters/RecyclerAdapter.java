package com.codingblocks.ChatBot_And_InterestRanker.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.ChatBot_And_InterestRanker.Activity.StudentNamesList;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.MyDatabase;
import com.codingblocks.ChatBot_And_InterestRanker.DBMS.StudentTable;
import com.codingblocks.eventerest.R;

import java.util.List;

/**
 * Created by HIman$hu on 9/3/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<String> student_names_list;

    public RecyclerAdapter(Context context, List<String> student_names_list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.student_names_list = student_names_list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_list, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.tv1.setText(student_names_list.get(position));
        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, holder.tv1.getText().toString() + "\nPosition" + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.tv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete it?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        student_names_list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, student_names_list.size());

                        final SQLiteDatabase db = MyDatabase.getInstance(StudentNamesList.m_context).getWritableDatabase();
                        StudentTable.deleteByStudentName(db, holder.tv1.getText().toString());
                    }

                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();
            int position = vholder.getPosition();

            Toast.makeText(context, "This is position " + position, Toast.LENGTH_LONG).show();

        }
    };


    @Override
    public int getItemCount() {
        return student_names_list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.list_avatar);
            tv1 = (TextView) itemView.findViewById(R.id.student_name_text_view);
        }
    }
}
