package com.example.kimhyonseong.six19;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class newGetback extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    final DatabaseReference DB1 = Fbase.getReference("oe");
    final DatabaseReference DB2 = Fbase.getReference("user1");
    final DatabaseReference DB3 = Fbase.getReference("ko");
    final DatabaseReference DB4 = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getbackpassword);

        final RadioGroup Rd = findViewById(R.id.Radio); //라디오 그룹 이름표?

        final EditText pw = findViewById(R.id.simplepw);

        final Button findpw = findViewById(R.id.find);
        final Button getbackbike = findViewById(R.id.getback);

        final RadioButton One = findViewById(R.id.one);
        final RadioButton Two = findViewById(R.id.two);
        final RadioButton Three = findViewById(R.id.three);

        DB4.addValueEventListener(new ValueEventListener() {   //비활성화를 위한 소스
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DB : dataSnapshot.getChildren()){
                    String lock1 = (String) DB.child("num1").getValue();
                    String lock2 = (String) DB.child("num2").getValue();
                    String lock3 = (String) DB.child("num3").getValue();

                    if ("open".equals(lock1)) One.setEnabled(false);
                    if ("open".equals(lock2)) Two.setEnabled(false);
                    if ("open".equals(lock3)) Three.setEnabled(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Find = new Intent(getApplicationContext(),findpw.class);
                startActivity(Find);
            }
        });

        getbackbike.setOnClickListener(new View.OnClickListener() {  //버튼 누르면
            @Override
            public void onClick(View view) {

                final String password = pw.getText().toString();
                final int id = Rd.getCheckedRadioButtonId();
                final RadioButton rb = findViewById(id);

                if (id == -1)
                    Toast.makeText(getApplicationContext(), "자리를 먼저 선택해주세요!", Toast.LENGTH_SHORT).show();

                else {
                    final String number = rb.getText().toString();

                    if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    }
                    else if (number.equals("1")) {
                        DB1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot MSnapshot : dataSnapshot.getChildren()) {
                                    String Pword = (String) MSnapshot.child("password").getValue();

                                    if (password.equals(Pword)) {
                                        Toast.makeText(getApplicationContext(), "잠금해제!!", Toast.LENGTH_SHORT).show();
                                        DB1.child("user1").child("state").setValue("open");
                                        DB1.child("user1").child("password").removeValue();
                                        DB1.child("/user1/E-mail").removeValue();
                                        DB4.child("/Sit/num1").setValue("open");
                                        onBackPressed();
                                        finish();
                                        return;
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else if (number.equals("2")) {
                        DB2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot MSnapshot : dataSnapshot.getChildren()) {
                                    String Pword = (String) MSnapshot.child("password").getValue();

                                    if (password.equals(Pword)) {
                                        Toast.makeText(getApplicationContext(), "잠금 해제!!", Toast.LENGTH_SHORT).show();
                                        DB2.child("banana").child("state").setValue("open");
                                        DB2.child("/banana/password").removeValue();
                                        DB2.child("/banana/E-mail").removeValue();
                                        DB4.child("/Sit/num2").setValue("open");
                                        onBackPressed();
                                        finish();
                                        return;
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else if (number.equals("3")) {
                        DB3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot MSnapshot : dataSnapshot.getChildren()) {
                                    String Pword = (String) MSnapshot.child("password").getValue();

                                    if (password.equals(Pword)) {
                                        Toast.makeText(getApplicationContext(), "잠금 해제!!", Toast.LENGTH_SHORT).show();
                                        DB3.child("tost").child("state").setValue("open");
                                        DB3.child("/tost/password").removeValue();
                                        DB3.child("/tost/E-mail").removeValue();
                                        DB4.child("/Sit/num3").setValue("open");
                                        onBackPressed();
                                        finish();
                                        return;
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        DB4.addValueEventListener(new ValueEventListener() {  //보관소 남은 자리 상태 표시 및 갯수 파악
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot DS : dataSnapshot.getChildren()){
                    String onesit = (String)DS.child("num1").getValue();
                    String twosit = (String)DS.child("num2").getValue();
                    String threesit = (String)DS.child("num3").getValue();

                    if ("lock".equals(onesit)){
                        ++i;
                    } else --i;
                    if ("lock".equals(twosit)){
                        ++i;
                    } else --i;
                    if ("lock".equals(threesit)){
                        ++i;
                    } else --i;
                }
                if (i==3) DB4.child("/Sit/rest").setValue("0");
                else if (i==1) DB4.child("/Sit/rest").setValue("1");
                else if (i==-1) DB4.child("/Sit/rest").setValue("2");
                else if (i==-3) DB4.child("/Sit/rest").setValue("3");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



