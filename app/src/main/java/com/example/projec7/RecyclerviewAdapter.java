package com.example.projec7;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    private ArrayList<address> listData= new ArrayList<>();
    Activity activity;
    private int count = 0;

    private onListItemSelectedInterface mListener;

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);


    public RecyclerviewAdapter(ArrayList<address> listData, onListItemSelectedInterface listener) {
        this.listData = listData;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new RecyclerviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address_name;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            address_name = itemView.findViewById(R.id.address_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(mSelectedItems.get(position,false)){
                        mSelectedItems.put(position,false);
                        v.setBackgroundColor(Color.WHITE);
                    } else {
                        mSelectedItems.put(position,true);
                        v.setBackgroundColor(Color.rgb(54,160,92));
                        mListener.onItemSelected(v,getAdapterPosition());
                        Log.d("test","pos = "+getAdapterPosition());
                    }


                }
            });

        }

        public void onBind(address data){
            address_name.setText(data.getName());
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void filterList(ArrayList<address> filteredList) {
        listData=filteredList;
        notifyDataSetChanged();
    }

    void addItem(address data) {
        listData.add(data);
    }


}

