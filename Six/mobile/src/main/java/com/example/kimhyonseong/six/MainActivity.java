package com.example.kimhyonseong.six;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.bloder.magic.view.MagicButton;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB4 = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MagicButton entrust = findViewById(R.id.lockB);
        MagicButton getback = findViewById(R.id.openB);
        MagicButton tst = findViewById(R.id.mapB);

        entrust.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entrust = new Intent(getApplicationContext(),entrust.class);
                startActivity(entrust);
            }
        });

        getback.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getback = new Intent(getApplicationContext(),newGetback.class);
                startActivity(getback);
            }
        });

        tst.setMagicButtonClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                 Intent map = new Intent(getApplicationContext(),MapsActivity.class);
                 startActivity(map);
                 finish();
            }
        });

        /*
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
        });*/
    }
}
