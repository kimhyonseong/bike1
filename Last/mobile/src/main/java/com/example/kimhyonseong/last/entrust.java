package com.example.kimhyonseong.last;

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

public class entrust extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    final DatabaseReference DB = Fbase.getReference();
    final DatabaseReference DB1 = Fbase.getReference("oe");
    final DatabaseReference DB2 = Fbase.getReference("user1");
    final DatabaseReference DB3 = Fbase.getReference("ko");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrustpassword);

        final RadioGroup Rad = findViewById(R.id.Radio);

        final EditText PW = findViewById(R.id.simplepw);
        final EditText Email = findViewById(R.id.mail);
        final EditText check = findViewById(R.id.check);

        final RadioButton One = findViewById(R.id.one);
        final RadioButton Two = findViewById(R.id.two);
        final RadioButton Three = findViewById(R.id.three);

        Button entrust = findViewById(R.id.entrust);

        DB.addValueEventListener(new ValueEventListener() {  //보관소 남은 자리 상태 표시 및 갯수 파악
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DS : dataSnapshot.getChildren()){
                    String onesit = (String)DS.child("/user1/state").getValue();
                    String twosit = (String)DS.child("/banana/state").getValue();
                    String threesit = (String)DS.child("/tost/state").getValue();

                    if ("lock".equals(onesit)){
                        One.setEnabled(false);  //버튼 비활성화
                    }
                    if ("lock".equals(twosit)){
                        Two.setEnabled(false);  //버튼 비활성화
                    }
                    if ("lock".equals(threesit)){
                        Three.setEnabled(false);  //버튼 비활성화
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        entrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw = PW.getText().toString();
                String checkpw = check.getText().toString();
                String email = Email.getText().toString();

                int id = Rad.getCheckedRadioButtonId();
                RadioButton rb = findViewById(id);

                if (id==-1) Toast.makeText(getApplicationContext(),"먼저 자전거 자리를 선택해주세요!",Toast.LENGTH_SHORT).show();

                else {
                    String number = rb.getText().toString();

                    if (pw.isEmpty() && checkpw.isEmpty())
                        Toast.makeText(getApplicationContext(), "비밀번호와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else if (pw.isEmpty())
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    else if (checkpw.isEmpty())
                        Toast.makeText(getApplicationContext(), "비밀번호 확인을 하시지 않았습니다.", Toast.LENGTH_SHORT).show();
                    else if (!pw.equals(checkpw))
                        Toast.makeText(getApplicationContext(), "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    else if (email.isEmpty())
                        Toast.makeText(getApplicationContext(), "E-mail은 비밀번호를 찾기 위해 꼭 필요합니다!!", Toast.LENGTH_SHORT).show();

                    else if (pw.equals(checkpw)) {
                        if (number.equals("1")) {
                            DB1.child("user1").child("state").setValue("lock");
                            DB1.child("user1").child("password").setValue(pw);
                            DB1.child("user1").child("E-mail").setValue(email);
                            Toast.makeText(getApplicationContext(), "자전거가 보관되었습니다.", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                            //startActivity(intent);
                            onBackPressed();
                            finish();
                        } else if (number.equals("2")) {
                            DB2.child("banana").child("state").setValue("lock");
                            DB2.child("banana").child("password").setValue(pw);
                            DB2.child("banana").child("E-mail").setValue(email);
                            Toast.makeText(getApplicationContext(), "자전거가 보관되었습니다.", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                            //startActivity(intent);
                            onBackPressed();
                            finish();
                        } else if (number.equals("3")) {
                            DB3.child("tost").child("state").setValue("lock");
                            DB3.child("tost").child("password").setValue(pw);
                            DB3.child("tost").child("E-mail").setValue(email);
                            Toast.makeText(getApplicationContext(), "자전거가 보관되었습니다.", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                            //startActivity(intent);
                            onBackPressed();
                            finish();
                        }
                    }
                }
            }
        });
    }
}

