package com.musica.cortador.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.musica.cortador.R;
import com.musica.cortador.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Bhavesh.Kaila on 20-May-15.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.MyViewHolder> {

    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_nav_drawer,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        NavDrawerItem current=data.get(position);
        viewHolder.title.setText(current.getTitle());
        viewHolder.navIcon.setImageResource(current.getImageId());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        ImageView navIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.nav_title);
            navIcon = (ImageView) itemView.findViewById(R.id.nav_icon);
        }

    }
}
