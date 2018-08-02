package com.example.kimhyonseong.six;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB4 = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng sch = new LatLng(36.769099322186705, 126.93493008613588);
        final MarkerOptions NO1 = new MarkerOptions();

        DB4.addValueEventListener(new ValueEventListener() {  //보관소 남은 자리
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DS : dataSnapshot.getChildren()) {
                    String sit = (String) DS.child("rest").getValue();

                    if ("3".equals(sit)) {
                        NO1
                                .title("자전거 보관소")  //글쓰기
                                .position(sch)  //위치
                                .alpha(2)  //투명도
                                .snippet("3자리 남았습니다!!");  //부연 설명
                        mMap.addMarker(NO1).showInfoWindow();  //마커 생성
                    } else if ("2".equals(sit)) {
                        NO1
                                .title("자전거 보관소")
                                .position(sch)
                                .alpha(2)
                                .snippet("2자리 남았습니다!!");
                        mMap.addMarker(NO1).showInfoWindow();
                    } else if ("1".equals(sit)) {
                        NO1
                                .title("자전거 보관소")
                                .position(sch)
                                .alpha(2)
                                .snippet("1자리 남았습니다!!");
                        mMap.addMarker(NO1).showInfoWindow();
                    } else if ("0".equals(sit)) {
                        NO1
                                .title("자전거 보관소")
                                .position(sch)
                                .alpha(2)
                                .snippet("자리가 없습니다.");
                        mMap.addMarker(NO1).showInfoWindow();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sch));  //카메라 초기 위치 설정
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sch, (float) 19.5));  //줌 사이즈
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}