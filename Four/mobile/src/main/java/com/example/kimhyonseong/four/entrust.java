package com.example.kimhyonseong.four;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class entrust extends AppCompatActivity{

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    final DatabaseReference DB1 = Fbase.getReference("oe");
    final DatabaseReference DB2 = Fbase.getReference("user1");
    final DatabaseReference DB3 = Fbase.getReference("ko");
    final DatabaseReference DB4 = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrustpassword);

        final RadioGroup Rad = findViewById(R.id.Radio);
        final TextView pwlabe = findViewById(R.id.pwlabel);
        final TextView chechlabel = findViewById(R.id.checklabel);
        final TextView Emaillabel = findViewById(R.id.emaillabel);

        final EditText PW = findViewById(R.id.simplepw);
        final EditText Email = findViewById(R.id.mail);
        final EditText check = findViewById(R.id.check);

        final RadioButton One = findViewById(R.id.one);
        final RadioButton Two = findViewById(R.id.two);
        final RadioButton Three = findViewById(R.id.three);

        Button entrust = findViewById(R.id.entrust);

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
                        One.setEnabled(false);  //버튼 비활성화
                    } else --i;
                    if ("lock".equals(twosit)){
                        ++i;
                        Two.setEnabled(false);  //버튼 비활성화
                    } else --i;
                    if ("lock".equals(threesit)){
                        ++i;
                        Three.setEnabled(false);  //버튼 비활성화
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

        entrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw = PW.getText().toString();
                String checkpw = check.getText().toString();
                String email = Email.getText().toString();

                if (pw.isEmpty()&&checkpw.isEmpty()){
                    Toast.makeText(getApplicationContext(),"비밀번호와 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }

                else if(pw.equals(checkpw)) {
                    int id = Rad.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(id);
                    String number = rb.getText().toString();

                    if(number.equals("1")) {
                        DB1.child("user1").child("state").setValue("lock");
                        DB1.child("user1").child("password").setValue(pw);
                        DB1.child("user1").child("E-mail").setValue(email);
                        DB4.child("/Sit/num1").setValue("lock");
                        Toast.makeText(getApplicationContext(),"자전거가 보관되었습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    else if(number.equals("2")) {
                        DB2.child("banana").child("state").setValue("lock");
                        DB2.child("banana").child("password").setValue(pw);
                        DB2.child("banana").child("E-mail").setValue(email);
                        DB4.child("/Sit/num2").setValue("lock");
                        Toast.makeText(getApplicationContext(),"자전거가 보관되었습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    else if(number.equals("3")) {
                        DB3.child("tost").child("state").setValue("lock");
                        DB3.child("tost").child("password").setValue(pw);
                        DB3.child("tost").child("E-mail").setValue(email);
                        DB4.child("/Sit/num3").setValue("lock");
                        Toast.makeText(getApplicationContext(),"자전거가 보관되었습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                else if (pw.isEmpty()){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력하지 않았습니다.",Toast.LENGTH_SHORT).show();
                }

                else if(checkpw.isEmpty()){
                    Toast.makeText(getApplicationContext(),"비밀번호 확인을 하시지 않았습니다.",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"비밀번호와 비밀번호 확인이 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

