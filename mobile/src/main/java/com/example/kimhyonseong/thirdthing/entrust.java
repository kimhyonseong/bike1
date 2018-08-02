package com.example.kimhyonseong.thirdthing;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class entrust extends AppCompatActivity{

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference("oe");

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

        Button entrust = findViewById(R.id.entrust);

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
                        DB.child("user1").child("state").setValue("lock");
                        DB.child("user1").child("password").setValue(pw);
                        DB.child("user1").child("E-mail").setValue(email);
                    }

                    else if(number.equals("2")) {
                        DB.child("user2").child("state").setValue("lock");
                        DB.child("user2").child("password").setValue(pw);
                        DB.child("user2").child("E-mail").setValue(email);
                    }

                    else if(number.equals("3")) {
                        DB.child("user3").child("state").setValue("lock");
                        DB.child("user3").child("password").setValue(pw);
                        DB.child("user3").child("E-mail").setValue(email);
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

