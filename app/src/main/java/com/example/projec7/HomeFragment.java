package com.example.projec7;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

import java.util.List;

import kotlin.experimental.ExperimentalObjCRefinement;

public class HomeFragment extends Fragment {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dbHelper = new DBHelper(getActivity());


        //getSupportFragmentManager().beginTransaction().replace(R.id.containers, frag1).commit();
        mPager = view.findViewById(R.id.viewpager);
        mPager.setClipToOutline(true);


        pagerAdapter = new MyAdapter(getActivity(), num_page);
        mPager.setAdapter(pagerAdapter);

        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


        mPager.setCurrentItem(1002);
        mPager.setOffscreenPageLimit(3);


        CompositePageTransformer transformer = new CompositePageTransformer();

        // MarginPageTransformer 추가
        transformer.addTransformer(new MarginPageTransformer(8));

        // 사용자 정의 PageTransformer 추가
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float position) {
                float v = 1 - Math.abs(position);
                view.setScaleY(0.8f + v * 0.2f);
            }
        });

        // ViewPager2에 변환 효과 적용
        mPager.setPageTransformer(transformer);



        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    public void updateListView() {
        if (getView() != null) {
            List<votelist> dataList = dbHelper.getOneData();

            if (dataList.isEmpty()) {
                Log.d("HomeFragment", "No data available");
            } else {
                voteListAdapter adapter = new voteListAdapter(getActivity(), R.layout.vote_item, dataList);
                ListView listView = getView().findViewById(R.id.listView);
                listView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        }
    }

}