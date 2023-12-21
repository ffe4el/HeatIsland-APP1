package com.example.projec7;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
//gradle 파일이 없어서 오류뜸
//import com.devs.vectorchildfinder.VectorChildFinder;
//import com.devs.vectorchildfinder.VectorDrawableCompat;

import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeatIslandMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeatIslandMapFragment extends Fragment {

    private ImageView mapImageView;
    private ImageView mapImageView2;
    private ImageView mapImageView3;

    private int color;
    private boolean flag = false;
    private static final String[] ITEMS = {"온도", "습도", "열섬"};
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_PARAM3 = "param3";
    public HeatIslandMapFragment() {
        // Required empty public constructor
    }

    public static HeatIslandMapFragment newInstance(String param1, String param2, String param3) {
        HeatIslandMapFragment fragment = new HeatIslandMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_heat_island_map, container, false);

        mapImageView = view.findViewById(R.id.iv_korea);
        mapImageView2 = view.findViewById(R.id.iv_tmp);
        mapImageView3 = view.findViewById(R.id.iv_hotisland);
        Spinner spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ITEMS[position];

                if (selectedItem.equals("온도")) {
                    if (!flag) {
                        MapUpdater.updateImageView(getActivity(), R.drawable.seoul_districts, mapImageView, selectedItem);
                        flag = true;
                    }

                    toggleImageViews(true, false,false);
                    Log.d("Myapp", "1번 사진임");
                } else if(selectedItem.equals("습도")) {
                    MapUpdater_water.updateImageView(getActivity(), R.drawable.seoul_districts, mapImageView2, selectedItem);
                    toggleImageViews(false,true, false);
                    Log.d("Myapp", "2번 사진임");
                }else if (selectedItem.equals("열섬")) {
                    // "열섬"이 선택되면 mapImageView3를 보이게 설정
                    MapUpdater_hotisland.updateImageView(getActivity(), R.drawable.seoul_districts, mapImageView3, selectedItem);
                    toggleImageViews(false, false,true);
                    Log.d("Myapp", "열섬 사진임");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected (if needed)
            }
        });

        return view;
    }
    private void toggleImageViews(boolean isImageView1Visible,boolean isImageView2Visible, boolean isImageView3Visible) {
        mapImageView.setVisibility(isImageView1Visible ? View.VISIBLE : View.GONE);
        mapImageView2.setVisibility(isImageView2Visible ? View.VISIBLE : View.GONE);
        mapImageView3.setVisibility(isImageView3Visible ? View.VISIBLE : View.GONE);
    }
}