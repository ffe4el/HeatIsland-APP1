package com.example.projec7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class WaterCarFragment extends Fragment implements onListItemSelectedInterface {

    RecyclerviewAdapter adapter;
    ArrayList<address> listdata, filteredList;

    final int voteCount[] = new int[25];

    String addName[]={"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_water_car, container, false);
        init(view);
        getData();

        for (int i = 0; i<25;i++)

        {
            voteCount[i] = 0;
        }

        Button btnRes = view.findViewById(R.id.button);
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper = new DBHelper(requireContext());
                for (int i=0;i <addName.length;i++){
                    int countValue = voteCount[i];
                    long result = helper.insertAddress(addName[i],countValue);
                }


                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new voteResult())
                        .commit();

            }
        });
        return view;
    }
    private void init(View view) {
        ConstraintLayout mainLayout = view.findViewById(R.id.mainLayout);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);


        filteredList = new ArrayList<>();
        listdata = new ArrayList<>();
        adapter = new RecyclerviewAdapter(listdata, this);


        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        recyclerView.addItemDecoration(new RecyclerDecoration(45));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);



    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void searchFilter(String searchText) {
        filteredList.clear();

        for (int i = 0; i < listdata.size(); i++) {
            if (listdata.get(i).getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(listdata.get(i));
            }
        }

        adapter.filterList(filteredList);
    }
    public void onItemSelected(View v, int position){
        voteCount[position]++;
    }

    private void getData(){
        address data = new address("강남구");
        adapter.addItem(data);
        data = new address("강동구");
        adapter.addItem(data);
        data = new address("강북구");
        adapter.addItem(data);
        data = new address("강서구");
        adapter.addItem(data);
        data = new address("관악구");
        adapter.addItem(data);
        data = new address("광진구");
        adapter.addItem(data);
        data = new address("구로구");
        adapter.addItem(data);
        data = new address("금천구");
        adapter.addItem(data);
        data = new address("노원구");
        adapter.addItem(data);
        data = new address("도봉구");
        adapter.addItem(data);
        data = new address("동대문구");
        adapter.addItem(data);
        data = new address("동작구");
        adapter.addItem(data);
        data = new address("마포구");
        adapter.addItem(data);
        data = new address("서대문구");
        adapter.addItem(data);
        data = new address("서초구");
        adapter.addItem(data);
        data = new address("성동구");
        adapter.addItem(data);
        data = new address("성북구");
        adapter.addItem(data);
        data = new address("송파구");
        adapter.addItem(data);
        data = new address("양천구");
        adapter.addItem(data);
        data = new address("영등포구");
        adapter.addItem(data);
        data = new address("용산구");
        adapter.addItem(data);
        data = new address("은평구");
        adapter.addItem(data);
        data = new address("종로구");
        adapter.addItem(data);
        data = new address("중구");
        adapter.addItem(data);
        data = new address("중랑구");
        adapter.addItem(data);
    }

}