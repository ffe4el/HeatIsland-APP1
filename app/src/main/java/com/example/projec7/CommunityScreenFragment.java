package com.example.projec7;


import android.os.Bundle;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityScreenFragment extends Fragment  {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private RecyclerView recyclerView;
    Button bWrite;
    ImageButton waterCar;
    private Spinner localSpinner;
    private Spinner themeSpinner;
    private ArrayList<String> localList;
    private ArrayList<String> themeList;
    private ArrayAdapter<String> localAdapter;
    private ArrayAdapter<String> themeAdapter;
    private List<Post> postList;
    private PostAdapter adapter;
    private SearchView searchView;


    public CommunityScreenFragment() {
        // Required empty public constructor
    }


    public static CommunityScreenFragment newInstance(String param1, String param2) {
        CommunityScreenFragment fragment = new CommunityScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        localList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.local_array)));
        themeList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.theme_array)));

        // 어댑터 초기화
        localAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, localList);
        localAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        themeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, themeList);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        View rootView = inflater.inflate(R.layout.fragment_community_screen, container, false);
        localSpinner = rootView.findViewById(R.id.local_category);
        themeSpinner = rootView.findViewById(R.id.theme_category);
        localSpinner.setAdapter(localAdapter);
        themeSpinner.setAdapter(themeAdapter);

        bWrite = rootView.findViewById(R.id.WriteButton);
        waterCar=rootView.findViewById(R.id.WaterCarButton);
        searchView = rootView.findViewById(R.id.search);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setReverseLayout(true);  // 최신 글이 위로 올라오도록 설정
        layoutManager.setStackFromEnd(true);   // 리스트가 아래에서부터 쌓이도록 설정

// RecyclerView에 LayoutManager 설정
        recyclerView.setLayoutManager(layoutManager);
        // DBHelper에서 데이터 가져오기
        DBHelper dbHelper = new DBHelper(getActivity());

        // PostAdapter 객체 초기화 및 데이터 설정
        postList = new ArrayList<>();
        postList.addAll(dbHelper.getAllPosts());
        adapter = new PostAdapter(getActivity(), postList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        localSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 선택된 지역에 따라 postList 필터링
                filterPosts(localList.get(position), themeSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무것도 선택되지 않았을 때의 처리
                showAllPosts();
            }
        });

        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 선택된 테마에 따라 postList 필터링
                filterPosts(localSpinner.getSelectedItem().toString(), themeList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무것도 선택되지 않았을 때의 처리
                showAllPosts();
            }
        });




        // WriteButton 클릭 이벤트 설정
        bWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // WriteFragment로 이동
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                WriteFragment writeFragment = new WriteFragment();
                transaction.replace(R.id.container, writeFragment);
                transaction.commit();
                requireActivity().getSupportFragmentManager().executePendingTransactions();
            }
        });
        waterCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                WaterCarFragment waterCarFragment = new WaterCarFragment();
                transaction.replace(R.id.container, waterCarFragment);
                transaction.commit();
                requireActivity().getSupportFragmentManager().executePendingTransactions();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });



        return rootView;
    }
    private void performSearch(String query) {
        DBHelper dbHelper = new DBHelper(getActivity());
        List<Post> searchResults = dbHelper.searchPosts(query);
        adapter.setPosts(searchResults);
        adapter.notifyDataSetChanged();
    }

    private void filterPosts(String selectedLocal, String selectedTheme) {
        // DBHelper에서 해당 지역과 테마에 맞는 데이터 가져오기
        DBHelper dbHelper = new DBHelper(getActivity());
        List<Post> filteredPosts = dbHelper.getFilteredPosts(selectedLocal, selectedTheme);

        // Adapter에 데이터 설정 및 갱신
        adapter.setPosts(filteredPosts);
        adapter.notifyDataSetChanged();
    }
    private void showAllPosts() {
        filterPosts("구 선택", "전체");
    }

}