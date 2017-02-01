package com.codingblocks.ChatBot_And_InterestRanker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codingblocks.customnavigationdrawer.R;

import java.util.ArrayList;

/**
 * Created by Bhavya Sapra on 01-02-2017.
 */

public class repositoryAdapter extends ArrayAdapter<repositories>
{
    Context context;
    ArrayList<repositories> reposdetail ;

    public repositoryAdapter(Context context, ArrayList<repositories> objects) {
        super(context,0, objects);
        this.context=context;
        this.reposdetail=objects;

    }

    public static class ViewHolder
    {

        public TextView reposname;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.repos_layout, null);
            ViewHolder vh = new ViewHolder();
            vh.reposname = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(vh);
        }
        repositories currentRepos = reposdetail.get(position);

        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.reposname.setText(currentRepos.getrepo_name().toUpperCase());




        return convertView;
    }



}