package com.example.kimhyonseong.newone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class findpw extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpw);

        final Button ok = findViewById(R.id.ok);
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
                DB.addChildEventListener(new ChildEventListener() {
                    int i=0;
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //그냥 처음에 읽을 수 있음
                        String e1 = (String) dataSnapshot.child("user1").child("E-mail").getValue();
                        String pass1 = (String) dataSnapshot.child("user1").child("password").getValue();
                        String e2 = (String) dataSnapshot.child("user2").child("E-mail").getValue();
                        String pass2 = (String) dataSnapshot.child("user2").child("password").getValue();
                        String e3 = (String) dataSnapshot.child("user3").child("E-mail").getValue();
                        String pass3 = (String) dataSnapshot.child("user3").child("password").getValue();

                        if (E.equals(e1))
                            Toast.makeText(getApplicationContext(),"1번 자리이며 비밀번호는 "+ pass1 +" 입니다.", Toast.LENGTH_SHORT).show();
                        else if (E.equals(e2))
                            Toast.makeText(getApplicationContext(),"2번 자리이며 비밀번호는 "+pass2+" 입니다.",Toast.LENGTH_SHORT).show();
                        else if (E.equals(e3))
                            Toast.makeText(getApplicationContext(),"3번 자리이며 비밀번호는 "+pass3+" 입니다.",Toast.LENGTH_SHORT).show();
                        else {
                            ++i;
                            Log.i("메시지",String.valueOf(i));
                            Toast.makeText(getApplicationContext(),"비밀번호 X " ,Toast.LENGTH_SHORT).show();
                        }
                        /*
                        if (i==3) { 이건 addValue에서 쓸수 있을듯
                            Toast.makeText(getApplicationContext(),"비밀번호 X " ,Toast.LENGTH_SHORT).show();
                            i=0;
                        }
                        */
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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