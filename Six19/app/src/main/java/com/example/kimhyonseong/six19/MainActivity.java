package com.example.kimhyonseong.six19;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.bloder.magic.view.MagicButton;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MagicButton entrust = findViewById(R.id.lockB);
        MagicButton getback = findViewById(R.id.openB);
        MagicButton map = findViewById(R.id.mapB);

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
                /*
                DB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i=0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String Rest = (String)data.child("rest").getValue();
                            if ("3".equals(Rest)){
                                Toast.makeText(getApplicationContext(),"모든 자리가 빈자리입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                i=1;
                            }
                        }
                        if (i==1)
                        {
                            Intent getback = new Intent(getApplicationContext(),newGetback.class);
                            startActivity(getback);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }); */
                Intent getback = new Intent(getApplicationContext(),newGetback.class);
                startActivity(getback);
            }
        });

        map.setMagicButtonClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                MainActivity.super.onBackPressed();  //뒤로가기
            }
        });
    }
}
