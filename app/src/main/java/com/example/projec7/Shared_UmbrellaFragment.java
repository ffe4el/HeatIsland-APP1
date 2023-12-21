package com.example.projec7;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Shared_UmbrellaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Shared_UmbrellaFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MAX_UMBRELLA_SLOTS = 10;

    private String mParam1;
    private String mParam2;
    GoogleMap mMap;
    private SharedUmbrellaViewModel viewModel;
    public Button button1;
    public Button button2;


    public Button imageButton;

    //현재 위도 & 경도 임의 설정
    private double current_latitude = 37.5;
    private double current_hardness = 127.0;
    List<Marker> markerList = new ArrayList<>();

    private double clickedLatitude =0;
    private double clickedHardness =0;
    private String clickedName;
    private int[] n;
    int tmp;
    private String MyLocationName;
    String [] IconName = {"marker0","marker1","marker2","marker3","marker4","marker5","marker6","marker7","marker8","marker9"};

    public Shared_UmbrellaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Shared_UmbrellaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Shared_UmbrellaFragment newInstance(String param1, String param2) {
        Shared_UmbrellaFragment fragment = new Shared_UmbrellaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(SharedUmbrellaViewModel.class);
        if (viewModel.getUmbrellaCounts() != null) {
            System.arraycopy(viewModel.getUmbrellaCounts(), 0, n, 0, viewModel.getUmbrellaCounts().length);
        } else {
            n = new int[MAX_UMBRELLA_SLOTS];
        }
        if(viewModel.getCountMarkerList()!=null)
            markerList.addAll(viewModel.getCountMarkerList());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared__umbrella, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        imageButton = view.findViewById(R.id.imageButton);

        button1.setClickable(true);
        button2.setClickable(true);

        imageButton.setClickable(true);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        imageButton.setOnClickListener(this);


        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng[] locations = {
                new LatLng(37.5642135,127.0016985),
                new LatLng(37.532610938096, 126.99448332375),
                new LatLng(37.554371328, 126.9227542239),
                new LatLng(37.50042,127.06418),
                new LatLng(37.544023739723215,127.05247047608701)
        };

        Marker myLocation = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(current_latitude,current_hardness))
                .title("현재 위치"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation.getPosition()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation.getPosition(), 12));

        for (int i = 0; i < markerList.size(); i++) {
            if (markerList.get(i).getPosition().longitude == current_hardness && markerList.get(i).getPosition().latitude == current_latitude) {
                String tmpString;
                if (viewModel.getCurrentLocationMarkerName() != null)
                    tmpString = viewModel.getCurrentLocationMarkerName();
                else
                    tmpString = "나의 우산통";
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .title(tmpString)
                        .position(new LatLng(current_latitude, current_hardness))
                        .snippet(n[i] + "개 남았습니다"));
                if (n[i]>9)
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker9over", 115, 201)));
                else marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(IconName[n[i]], 115, 201)));
                markerList.add(marker);
                break;
            }
        }
        for (int i = 0; i < locations.length; i++) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(locations[i])
                    .snippet(n[i] + "개 남았습니다.")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(IconName[n[i]], 115, 201))));

            if (i==0)
                marker.setTitle("서울 중심");
            else if (i==1)
                marker.setTitle("이태원");
            else if (i==2)
                marker.setTitle("홍대");
            else if (i==3)
                marker.setTitle("대치동");
            else if (i==4)
                marker.setTitle("성수동");
            markerList.add(marker);
        }

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (markerList.contains(marker)) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);

            // 클릭한 마커의 인덱스
            clickedLatitude=marker.getPosition().latitude;
            clickedHardness=marker.getPosition().longitude;
            clickedName=marker.getTitle();
            // 여기에 필요한 로직 추가

            marker.showInfoWindow();
            return true;
        }
        return false;
    }

    private Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(
                getResources(),
                getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName())
        );
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("개수 입력");

            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String inputValue = input.getText().toString();

                    try {
                        int intValue = Integer.parseInt(inputValue);

                        for (int i = 0; i<markerList.size(); i++){
                            if (markerList.get(i).getTitle().equals(clickedName)) {
                                tmp = i;
                                break;
                            }

                        }
                        n[tmp]+=intValue;
                        Toast.makeText(getActivity(), "넣은 개수: " + intValue+" 총 개수: "+n[tmp] + "\n새로고침 방법: 다른 페이지 방문 후 복귀", Toast.LENGTH_SHORT).show();

                        // 기존 마커의 snippet 업데이트
                        Marker marker = markerList.get(tmp);

                        if (n[tmp] > 9) {
                            marker.setSnippet(n[tmp] + "개 남았습니다.");
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker9over", 115, 201)));

                        } else {
                            marker.setSnippet(n[tmp] + "개 남았습니다.");
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(IconName[n[tmp]], 115, 201)));

                        }
                        // 이부분이 바로바로 갱신이 안되는 버그가 있음 (다른 프래그먼트를 갔다오면 갱신 가능)
                        marker.showInfoWindow();

                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(), "올바른 개수를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else if (view.getId() == R.id.button2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("개수 입력(최대 2개만 가져갈 수 있습니다)");

            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String inputValue = input.getText().toString();
                    try {
                        int intValue = Integer.parseInt(inputValue);
                        for (int i = 0; i<markerList.size(); i++){
                            if (markerList.get(i).getTitle().equals(clickedName)) {
                                tmp = i;
                                break;
                            }

                        }

                        if (intValue <= 2 && n[tmp] - intValue >= 0) {
                            n[tmp] -= intValue;
                            Toast.makeText(getActivity(), "뺀 개수: " + intValue+" 총 개수: "+n[tmp] + "\n새로고침 방법: 다른 페이지 방문 후 복귀", Toast.LENGTH_SHORT).show();

                            // 기존 마커의 snippet 업데이트
                            Marker marker = markerList.get(tmp);
                            if (n[tmp] > 9) {
                                marker.setSnippet(n[tmp] + "개 남았습니다.");
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker9over", 115, 201)));


                            } else {
                                marker.setSnippet(n[tmp] + "개 남았습니다.");
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(IconName[n[tmp]], 115, 201)));
                            }
                            // 이부분이 바로바로 갱신이 안되는 버그가 있음 (다른 프래그먼트를 갔다오면 갱신 가능)
                            marker.showInfoWindow();

                        }
                        else if (intValue > 2) {
                            Toast.makeText(getActivity(), "최대 2개만 가져갈 수 있습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "현재 개수가 부족합니다. 죄송합니다.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(), "올바른 개수를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else if (view.getId() == R.id.imageButton) {
            // 새로운 마커의 위치
            LatLng newMarkerLocation = new LatLng(current_latitude, current_hardness);
            for (Marker marker : markerList){
                if (marker.getPosition().latitude==current_latitude&&marker.getPosition().longitude==current_hardness){
                    Toast.makeText(getActivity(), "현재 위치에 우산통이 이미 존재합니다", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            // AlertDialog를 이용하여 사용자로부터 문자열 입력받기
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("현재 위치에 통 놓기 (위치를 알려주세요!)");

            final EditText input = new EditText(getActivity());
            builder.setView(input);

            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyLocationName = input.getText().toString();

                    // 새로운 마커 추가
                    Marker newMarker = mMap.addMarker(new MarkerOptions()
                            .position(newMarkerLocation)
                            .snippet(0+"개 남았습니다.")
                            .title(MyLocationName) // 사용자가 입력한 문자열로 타이틀 설정
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(IconName[0], 115, 201))));

                    // 마커 리스트에 추가
                    markerList.add(newMarker);

                    // n 배열 크기 늘리기
                    int[] newN = new int[n.length + 1];
                    System.arraycopy(n, 0, newN, 0, n.length);
                    n = newN;

                    // 새로운 마커에 대한 인덱스 할당
                    int newIndex = markerList.size() - 1;

                    // 카메라 이동
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(newMarkerLocation));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newMarkerLocation, 12));

                    Toast.makeText(getActivity(), "현재 위치에 새로운 장소가 등록되었습니다", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setUmbrellaCounts(n);
        viewModel.setCountMarkerList(markerList);
        viewModel.setCurrentLocationMarkerName(MyLocationName);
    }

}