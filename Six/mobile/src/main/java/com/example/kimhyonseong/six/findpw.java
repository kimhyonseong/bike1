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

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText E1 = findViewById(R.id.email);
                final String E = E1.getText().toString();

                DB1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot DS : dataSnapshot.getChildren()){
                            String em = (String)DS.child("E-mail").getValue();
                            String password = (String)DS.child("password").getValue();

                            if (E.equals(em)){
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("plain/text");
                                email.putExtra(Intent.EXTRA_EMAIL,em);
                                email.putExtra(Intent.EXTRA_SUBJECT,"안녕하세요! 자전거 보관소입니다!!");
                                email.putExtra(Intent.EXTRA_TEXT,"1번 자리 고객님의 비밀번호는 "+password+" 입니다.");
                                startActivity(email);
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
                            String em = (String)DS.child("E-mail").getValue();
                            String password = (String)DS.child("password").getValue();

                            if (E.equals(em)){
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("plain/text");
                                email.putExtra(Intent.EXTRA_EMAIL,em);
                                email.putExtra(Intent.EXTRA_SUBJECT,"안녕하세요! 자전거 보관소입니다!!");
                                email.putExtra(Intent.EXTRA_TEXT,"2번 자리 고객님의 비밀번호는 "+password+" 입니다.");
                                startActivity(email);
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
                            String em = (String)DS.child("E-mail").getValue();
                            String password = (String)DS.child("password").getValue();

                            if (E.equals(em)){
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("plain/text");
                                email.putExtra(Intent.EXTRA_EMAIL,em);
                                email.putExtra(Intent.EXTRA_SUBJECT,"안녕하세요! 자전거 보관소입니다!!");
                                email.putExtra(Intent.EXTRA_TEXT,"3번 자리 고객님의 비밀번호는 "+password+" 입니다.");
                                startActivity(email);
                                return;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(getApplicationContext(),"비밀번호는 이메일로 보내드렸습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findpw.super.onBackPressed();
            }
        });
    }
}