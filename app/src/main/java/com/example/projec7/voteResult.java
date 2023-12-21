package com.example.projec7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class voteResult extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vote_result, container, false);

        Button btnMain = view.findViewById(R.id.button);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();

            }
        });
        return view;
    }
}