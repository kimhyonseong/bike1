package com.example.kimhyonseong.four;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB4 = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button entrust = findViewById(R.id.entrustbike);
        Button getback = findViewById(R.id.getbackbike);
        Button tst = findViewById(R.id.test);

        entrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entrust = new Intent(getApplicationContext(),entrust.class);
                startActivity(entrust);
            }
        });

        getback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getback = new Intent(getApplicationContext(),newGetback.class);
                startActivity(getback);
            }
        });

        tst.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                 Intent Test = new Intent(getApplicationContext(),MapsActivity.class);
                 startActivity(Test);
            }
        });

        DB4.addValueEventListener(new ValueEventListener() {  //보관소 남은 자리
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DS : dataSnapshot.getChildren()){
                    String sit = (String)DS.child("rest").getValue();

                    if ("0".equals(sit)) entrust.setEnabled(false);
                    else entrust.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
