package com.example.projec7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.projec7.databinding.FragmentHeatShelterMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
public class HeatShelterMapFragment extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FragmentHeatShelterMapBinding binding;
    private HashMap<Marker, MarkerInfo> markerInfoHashMap; //Marker와 MarkerInfo를 매핑하는 맵 선언
    //    private FusedLocationProviderClient fusedLocationClient;
//    private LocationCallback locationCallback;
    private HomeFragment homeFragment;
    private Shared_UmbrellaFragment sharedUmbrellaFragment;
    private HeatIslandMapFragment heatIslandMapFragment;
    private HeatShelterMapFragment heatShelterMapFragment;
    //    private HeatShelterAPI heatShelterAPI;
    private CommunityScreenFragment communityScreenFragment;
    private HeatShelterAPI heatShelterAPI;

    private navigator navigator;

    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentHeatShelterMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragment = new HomeFragment();
        heatIslandMapFragment = new HeatIslandMapFragment();
        heatShelterMapFragment = new HeatShelterMapFragment();
        sharedUmbrellaFragment = new Shared_UmbrellaFragment();
        communityScreenFragment = new CommunityScreenFragment();
        heatShelterAPI=new HeatShelterAPI();
        navigator=new navigator();

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                if (item.getItemId() == R.id.main) {
//                    // Main 액티비티로 이동하는 코드를 여기에 추가
//                    return true;
//                } else if (item.getItemId() == R.id.maps) {
//                    startActivity(new Intent(MapsActivity.this, MapsActivity.class));
//                    return true;
//                }
//                return false;
//            }
//        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottombarView);
        bottomNavigationView.setSelectedItemId(R.id.Heat_shelter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.shared_umbrella) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, sharedUmbrellaFragment).commit();
                    searchButton.setVisibility(View.GONE); // 숨기기
                } else if (item.getItemId() == R.id.navigate_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    searchButton.setVisibility(View.GONE); // 숨기기
                } else if (item.getItemId() == R.id.Heat_islandMap) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, heatIslandMapFragment).commit();
                    searchButton.setVisibility(View.GONE); // 숨기기
                }else if(item.getItemId()==R.id.Heat_shelter) {
                    Intent intent = new Intent(navigator, HeatShelterAPI.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (item.getItemId() == R.id.community) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, communityScreenFragment).commit();
                    searchButton.setVisibility(View.GONE); // 숨기기
                }
                return true;
            }

        });






        //hashmap초기화
        markerInfoHashMap = new HashMap<>();
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //위치 업데이트 콜백 설정
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
//                }
//            }
//        };
//
//        //위치 권한 확인
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        } else {
//            // 위치 업데이트 시작
//            startLocationUpdates();
//        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 검색하기 버튼 클릭 이벤트 설정
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 검색 다이얼로그 표시
                showSearchDialog();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // UiSettings를 가져와서 확대/축소 기능 활성화
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        // 기존 지도와 마커 표시
        showOriginalMarkers();

        // 기존 위치로 카메라 이동
        LatLng defaultLocation = new LatLng(37.560787, 127.041125); // 악어떡볶이의 위도, 경도
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 16));

        // 현재 위치 표시
        LatLng currentLocation = new LatLng(37.560787, 127.041125);
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("현재 위치️").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // 가장 가까운 5군데 초록색 마크로 표시
        showNearestMarkers(currentLocation, 6, BitmapDescriptorFactory.HUE_GREEN);
    }

    private void showOriginalMarkers() {
        ArrayList<LatLng> coordinates = getIntent().getParcelableArrayListExtra("coordinates");
        HashMap<LatLng, MarkerInfo> markerInfoMap = (HashMap<LatLng, MarkerInfo>) getIntent().getSerializableExtra("markerInfoMap");

        // 인텐트에서 좌표 리스트를 가져옴
        if (coordinates != null) {
            for (LatLng coordinate : coordinates) {
                //정보가 있다면 마커에 정보 추가
                if (markerInfoMap != null && markerInfoMap.containsKey(coordinate)) {
                    MarkerInfo markerInfo = markerInfoMap.get(coordinate);
                    Marker marker = mMap.addMarker(new MarkerOptions().position(coordinate).title(markerInfo.getAddress()));
                    marker.setTag(markerInfo);
                    markerInfoHashMap.put(marker, markerInfo); // HashMap에 매핑 추가
                    //fan.png 이미지로 마커 설정
                    BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(R.drawable.fan);
                    Bitmap b = bd.getBitmap();
                    Bitmap bitMapImage = Bitmap.createScaledBitmap(b,100,100,false);
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(coordinate));
                } else {
                    mMap.addMarker(new MarkerOptions().position(coordinate));
                }
            }
        }

        // 마커 클릭 리스너 설정
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MarkerInfo markerInfo = (MarkerInfo) marker.getTag();
                if (markerInfo != null) {
                    showMarkerInfoDialog(markerInfo);
                }
                // 마커 클릭 시 title을 표시하기 위해 showInfoWindow() 호출
                marker.showInfoWindow();
                return true; //이벤트소비
            }
        });
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("검색하기").setMessage("검색할 구 이름을 입력하세요.");

        //EditText를 다이얼로그에 추가
        final EditText input = new EditText(this);
        builder.setView(input);

        //확인 버튼 설정
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //사용자가 입력한 구 이름
                String searchedGu = input.getText().toString();
                searchByGuName(searchedGu);
            }
        });

        // 취소 버튼 설정
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 다이얼로그 닫기
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void searchByGuName(String searchedGu) { //구 이름으로 검색해서 나타내기
        mMap.clear();

        ArrayList<LatLng> coordinates = getIntent().getParcelableArrayListExtra("coordinates");
        HashMap<LatLng, MarkerInfo> markerInfoMap = (HashMap<LatLng, MarkerInfo>) getIntent().getSerializableExtra("markerInfoMap");

        if (coordinates != null) {
            for (LatLng coordinate : coordinates) {
                if (markerInfoMap != null && markerInfoMap.containsKey(coordinate)) {
                    MarkerInfo markerInfo = markerInfoMap.get(coordinate);
                    //fan.png 이미지로 마커 설정
                    BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(R.drawable.fan);
                    Bitmap b = bd.getBitmap();
                    Bitmap bitMapImage = Bitmap.createScaledBitmap(b,100,100,false);

                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(coordinate)
                            .title(markerInfo.getAddress())
                            .icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)));

                    marker.setTag(markerInfo);
                    markerInfoHashMap.put(marker, markerInfo);

                    if (markerInfo.getAddress().contains(searchedGu)) {
                        marker.setVisible(true);
                    } else {
                        marker.setVisible(false);
                    }
                } else {
                    mMap.addMarker(new MarkerOptions().position(coordinate));
                }
            }
        }

        LatLng defaultLocation = new LatLng(37.5665, 126.9780);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 13));
    }

    private void showNearestMarkers(LatLng currentLocation, int count, float color) {
        //가장 가까운 5군데 찾아서 표시
        for (int i = 0; i<Math.min(count, markerInfoHashMap.size()); i++){
            Marker nearestMarker = getNearestMarker(currentLocation);
            if (nearestMarker != null) {
                nearestMarker.setIcon(BitmapDescriptorFactory.defaultMarker(color));
                //중복방지를 위해 markerInfoHashMap에서 해당 마커를 제거
                markerInfoHashMap.remove(nearestMarker);
            }else{
                //더이상 표시할 가까운 마커가 없으면 반복 중단
                break;
            }
        }
    }

    private Marker getNearestMarker(LatLng currentLocation) {
        Marker neareastMarker = null;
        double minDistance = Double.MAX_VALUE;

        for (Marker marker : markerInfoHashMap.keySet()) {
            LatLng markerLocation = marker.getPosition();
            double distance = getDistance(currentLocation, markerLocation);
            if (distance < minDistance) {
                minDistance = distance;
                neareastMarker = marker;
            }
        }
        return neareastMarker;
    }

    private double getDistance(LatLng location1, LatLng location2) {
        double lat1 = location1.latitude;
        double lon1 = location1.longitude;
        double lat2 = location2.latitude;
        double lon2 = location2.longitude;

        double R = 6371.0; // 지구의 반지름 (단위: km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    //위치 업데이트 시작
//    private void startLocationUpdates() {
//        LocationRequest locationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(5000); // 5초마다 위치 업데이트
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//    }

    private void showMarkerInfoDialog(MarkerInfo markerInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("상세 정보")
                .setMessage("쉼터명: " + markerInfo.getName()+"\n"
                        + "도로명 주소: " + markerInfo.getAddress() + "\n"
                        + "면적: " + markerInfo.getArea() + "\n"
                        + "이용 가능 인원: " + markerInfo.getUser() + "\n"
                        + "선풍기 보유 대수: " + markerInfo.getFan() + "\n"
                        + "에어컨 보유 대수: " + markerInfo.getAir())
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 확인 버튼을 누르면 알림창 닫기
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


    //액티비티가 종료될 때 위치 업데이트 중지

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        fusedLocationClient.removeLocationUpdates(locationCallback);
//    }
}