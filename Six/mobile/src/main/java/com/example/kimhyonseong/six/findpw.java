package com.example.kimhyonseong.six;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
    DatabaseReference DB4 = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpw);

        Button ok = findViewById(R.id.ok);
        Button B2 = findViewById(R.id.backk);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText E1 = findViewById(R.id.email);
                final String E = E1.getText().toString();

                DB1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot DS : dataSnapshot.getChildren()){
                            String em = (String)DS.child("E-mail").getValue();
                            String password = (String)DS.child("password").getValue();

                            if (E.equals(em)){
                                Toast.makeText(getApplicationContext(),"1번자리, 비밀번호는 "+password+" 입니다.",Toast.LENGTH_SHORT).show();
                                DB4.child("/Sit/email1").removeValue();
                                DB4.child("/Sit/email2").removeValue();
                                DB4.child("/Sit/email3").removeValue();
                                findpw.super.onBackPressed();
                                return;
                            }
                        }DB4.child("/Sit/email1").setValue("No");
                        SystemClock.sleep(1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DB2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot DS : dataSnapshot.getChildren()){
                            String em = (String)DS.child("E-mail").getValue();
                            String password = (String)DS.child("password").getValue();

                            if (E.equals(em)){
                                Toast.makeText(getApplicationContext(),"2번자리, 비밀번호는 "+password+" 입니다.",Toast.LENGTH_SHORT).show();
                                DB4.child("/Sit/email1").removeValue();
                                DB4.child("/Sit/email2").removeValue();
                                DB4.child("/Sit/email3").removeValue();
                                findpw.super.onBackPressed();
                                return;
                            }
                        }DB4.child("/Sit/email2").setValue("No");
                        SystemClock.sleep(1);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DB3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot DS : dataSnapshot.getChildren()){
                            String em = (String)DS.child("E-mail").getValue();
                            String password = (String)DS.child("password").getValue();

                            if (E.equals(em)){
                                Toast.makeText(getApplicationContext(),"비밀번호는 "+password+" 입니다.",Toast.LENGTH_SHORT).show();
                                DB4.child("/Sit/email1").removeValue();
                                DB4.child("/Sit/email2").removeValue();
                                DB4.child("/Sit/email3").removeValue();
                                findpw.super.onBackPressed();
                                return;
                            }
                        }
                        DB4.child("/Sit/email3").setValue("No");
                        SystemClock.sleep(1);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DB4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //SystemClock.sleep(1);
                        int i=0;
                        for (DataSnapshot DB : dataSnapshot.getChildren()){
                            String e1 = (String)DB.child("email1").getValue();
                            String e2 = (String)DB.child("email2").getValue();
                            String e3 = (String)DB.child("email3").getValue();

                            if ("No".equals(e1)) ++i;
                            if ("No".equals(e2)) ++i;
                            if ("No".equals(e3)) ++i;

                        }
                        //SystemClock.sleep(10);
                        if (i==3) Toast.makeText(getApplicationContext(),"일치하는 이메일이 없습니다.",Toast.LENGTH_SHORT).show();
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
                findpw.super.onBackPressed();
                finish();
            }
        });
    }
}