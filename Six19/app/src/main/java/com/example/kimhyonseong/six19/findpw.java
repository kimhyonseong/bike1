package com.example.kimhyonseong.six19;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    DatabaseReference DB4 = Fbase.getReference();

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

                if (E.isEmpty()){
                    Toast.makeText(getApplicationContext(),"이메일을 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                DB4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i=0;
                        for (DataSnapshot DB : dataSnapshot.getChildren()){
                            String e1 = (String)DB.child("user1").child("E-mail").getValue();
                            String pass1 = (String)DB.child("user1").child("password").getValue();
                            String e2 = (String)DB.child("banana").child("E-mail").getValue();
                            String pass2 = (String)DB.child("banana").child("password").getValue();
                            String e3 = (String)DB.child("tost").child("E-mail").getValue();
                            String pass3 = (String)DB.child("tost").child("password").getValue();

                            if (E.equals(e1)) {
                                Toast.makeText(getApplicationContext(),"1번자리, 비밀번호는 "+pass1+" 입니다.",Toast.LENGTH_SHORT).show();
                                E1.setText("");
                                onBackPressed();
                                finish();
                                return;
                            }
                            else if (E.equals(e2)) {
                                Toast.makeText(getApplicationContext(),"2번자리, 비밀번호는 "+pass2+" 입니다.",Toast.LENGTH_SHORT).show();
                                E1.setText("");
                                onBackPressed();
                                finish();
                                return;
                            }
                            else if (E.equals(e3)) {
                                Toast.makeText(getApplicationContext(),"3번자리, 비밀번호는 "+pass3+" 입니다.",Toast.LENGTH_SHORT).show();
                                E1.setText("");
                                onBackPressed();
                                finish();
                                return;
                            }
                            ++i;
                        }
                        if (i==5) {
                            Toast.makeText(getApplicationContext(),"일치하는 이메일이 없습니다.",Toast.LENGTH_SHORT).show();
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
                findpw.super.onBackPressed();
                finish();
            }
        });
    }
}