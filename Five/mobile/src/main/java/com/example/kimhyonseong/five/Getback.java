package com.example.kimhyonseong.five;

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

public class Getback extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getbackpassword);

        final RadioGroup Rd = findViewById(R.id.Radio); //라디오 그룹 이름표?

        final EditText pw = findViewById(R.id.simplepw);

        final Button findpw = findViewById(R.id.find);
        final Button getbackbike = findViewById(R.id.getback);

        final int id = Rd.getCheckedRadioButtonId();
        final RadioButton rb = findViewById(id);
        //String number = rb.getText().toString(); //이게 잘못됬음
        //final String TAG="";

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
                final String number = rb.getText().toString();
                //DatabaseReference DBnumber = Fbase.getReference("user"+number);

                if (password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요!",Toast.LENGTH_SHORT).show();
                }

                else {
                    DB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Toast.makeText(getApplicationContext(),number,Toast.LENGTH_SHORT).show();
                            //Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                            //String number = rb.getText().toString();
                            //String value = dataSnapshot.getValue(String.class);
                            //Log.d(TAG, "Value is: " + value);

                            /*while (child.hasNext())
                             {
                                if (password.equals(child.next().getKey())) {
                                    Toast.makeText(getApplicationContext(), "로그인 완료!", Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(getApplicationContext(), child.next().getKey(), Toast.LENGTH_SHORT).show(); //객체가 3개 이상일때 오류남
                             }*/
                             //Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();

                            for (DataSnapshot MSnapshot : dataSnapshot.getChildren()){  //데이터를 가져와서 읽는(이해를 못하고 쓰는중)
                                String email = (String) MSnapshot.child("E-mail1").getValue();
                                //String PW1 = (String) MSnapshot.child("password").getValue();

                                Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();

                                /*if (password.equals(PW1)){
                                    Toast.makeText(getApplicationContext(),"로그인 완료!!",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다...",Toast.LENGTH_SHORT).show();

                                }*/
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "캔슬로 들어간다", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
    /*public void Read(){
       DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("메시지","에러인가");
                String read = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(),"들어가긴하니",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

    /*public class User {
        public String password;
        public String State;
        public String Email;

        public User(String p,String s,String e)
        {
            this.password=p;
            this.State=s;
            this.Email=e;
        }
    }*/
}



