package com.example.kimhyonseong.last;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private Marker mylocattion;
    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageButton button = findViewById(R.id.button1);

        try {
            MapsInitializer.initialize(this);
        } catch(Exception e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {  //버튼 누르면 내 위치로 감
            @Override
            public void onClick(View v) {
                requestMyLocation();
            }
        });

    }

    private void requestMyLocation() {  //내위치 알리는 소스
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

        final LatLng sch = new LatLng(36.768450443716084, 126.9323533776151);
        final MarkerOptions NO1 = new MarkerOptions();

        DB.addValueEventListener(new ValueEventListener() {  //보관소 남은 자리
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sit= 0;
                for (DataSnapshot DS : dataSnapshot.getChildren()) {
                    String state1 = (String) DS.child("/user1/state").getValue();
                    String state2 = (String) DS.child("/banana/state").getValue();
                    String state3 = (String) DS.child("/tost/state").getValue();

                    if ("open".equals(state1)){
                        ++sit;
                    } else;
                    if ("open".equals(state2)){
                        ++sit;
                    } else;
                    if ("open".equals(state3)){
                        ++sit;
                    } else;

                    if (sit==3) {
                        NO1
                                .title("자전거 보관소")  //글쓰기
                                .position(sch)  //위치
                                .alpha(2)  //투명도
                                .snippet("3자리 남았습니다!!");  //부연 설명
                        mMap.addMarker(NO1).showInfoWindow();  //마커 생성
                    } else if (sit==2) {
                        NO1
                                .title("자전거 보관소")
                                .position(sch)
                                .alpha(2)
                                .snippet("2자리 남았습니다!!");
                        mMap.addMarker(NO1).showInfoWindow();
                    } else if (sit==1) {
                        NO1
                                .title("자전거 보관소")
                                .position(sch)
                                .alpha(2)
                                .snippet("1자리 남았습니다!!");
                        mMap.addMarker(NO1).showInfoWindow();
                    } else if (sit==0) {
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sch, (float) 18));  //줌 사이즈
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(getApplicationContext(),marker.getTitle(),Toast.LENGTH_SHORT).show();
                if (marker.getTitle().equals("자전거 보관소")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
}
