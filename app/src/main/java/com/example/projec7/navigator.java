package com.example.projec7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.projec7.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class navigator extends AppCompatActivity {

    private HomeFragment homeFragment;
    private Shared_UmbrellaFragment sharedUmbrellaFragment;
    private HeatIslandMapFragment heatIslandMapFragment;
    private HeatShelterMapFragment heatShelterMapFragment;
    //    private HeatShelterAPI heatShelterAPI;
    private CommunityScreenFragment communityScreenFragment;
    private HeatShelterAPI heatShelterAPI;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);


        homeFragment = new HomeFragment();
        heatIslandMapFragment = new HeatIslandMapFragment();
        heatShelterMapFragment = new HeatShelterMapFragment();
        sharedUmbrellaFragment = new Shared_UmbrellaFragment();
        communityScreenFragment = new CommunityScreenFragment();
        heatShelterAPI=new HeatShelterAPI();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottombarView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.shared_umbrella) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, sharedUmbrellaFragment).commit();
                } else if (item.getItemId() == R.id.navigate_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                } else if (item.getItemId() == R.id.Heat_islandMap) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, heatIslandMapFragment).commit();
                } else if (item.getItemId() == R.id.Heat_shelter) {
                    Intent intent = new Intent(navigator.this, HeatShelterAPI.class);
                    startActivity(intent);
                    // finish(); // 이 부분을 주석 처리하거나 삭제합니다.
                    return true;
                } else if (item.getItemId() == R.id.community) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, communityScreenFragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigate_home);

    }
}