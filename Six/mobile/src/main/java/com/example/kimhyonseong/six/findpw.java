package com.example.kimhyonseong.six;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class findpw extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB1 = Fbase.getReference("oe");
    DatabaseReference DB2 = Fbase.getReference("user1");
    DatabaseReference DB3 = Fbase.getReference("ko");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpw);

        Button B1 = findViewById(R.id.ok);
        Button B2 = findViewById(R.id.backk);
        final EditText E1 = findViewById(R.id.email);

        B1.setOnClickListener(new View.OnClickListener() {
            String E = E1.getText().toString();
            @Override
            public void onClick(View view) {
                DB1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot DS : dataSnapshot.getChildren()){
                            String em =(String)DS.child("E-mail").getValue();

                            if (E.equals(em)){
                                Toast.makeText(getApplicationContext(),"1번 자리 "+em,Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DB2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot DS : dataSnapshot.getChildren()){
                            String em =(String)DS.child("E-mail").getValue();

                            if (E.equals(em)){
                                Toast.makeText(getApplicationContext(),"2번 자리 "+em,Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DB3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot DS : dataSnapshot.getChildren()){
                            String em =(String)DS.child("E-mail").getValue();

                            if (E.equals(em)){
                                Toast.makeText(getApplicationContext(),"3번 자리 "+em,Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}