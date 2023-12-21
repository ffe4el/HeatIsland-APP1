package com.example.projec7;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class voteListAdapter extends ArrayAdapter {
    public voteListAdapter(@NonNull Context context, int resource, @NonNull List objects){
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        int pos = position;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.vote_item, parent, false);

        }
        TextView address_view = convertView.findViewById(R.id.item_address);
        TextView count_view = convertView.findViewById(R.id.item_count);

        votelist list_view_item = (votelist) getItem(position);
        address_view.setText(list_view_item.getName());
        count_view.setText(String.valueOf(list_view_item.getCount()));

        return convertView;
    }
}
