package com.example.kimhyonseong.six19;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity4 extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private Marker mylocattion,bike;
    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB4 = Fbase.getReference("kim");
    //LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps4);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageButton button = findViewById(R.id.button1);

        try {
            MapsInitializer.initialize(this);
        } catch(Exception e) {
            e.printStackTrace();
        }

        //requestMyLocation(); //상시 내 위치 알리기

        button.setOnClickListener(new View.OnClickListener() {  //버튼 누르면 내 위치로 감
            @Override
            public void onClick(View v) {
                requestMyLocation();
            }
        });

    }

    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 5000;
            float minDistance = 0;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }
                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) { }
                        @Override
                        public void onProviderEnabled(String provider) { }
                        @Override
                        public void onProviderDisabled(String provider) {  }
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                showCurrentLocation(lastLocation);
            }

            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {  }
                        @Override
                        public void onProviderEnabled(String provider) { }
                        @Override
                        public void onProviderDisabled(String provider) { }
                    }
            );
        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        if (mylocattion != null){
            mylocattion.remove();
        }

        mylocattion = mMap.addMarker(new MarkerOptions()
                .title("내 위치")
                .position(curPoint));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(curPoint));  //카메라 위치
        mylocattion.showInfoWindow();
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sch, (float) 19));  //줌 사이즈
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(getApplicationContext(),marker.getTitle(),Toast.LENGTH_SHORT).show();
                if (marker.getTitle().equals("자전거보관소")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
}