package com.example.kimhyonseong.secondthing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class Getback extends AppCompatActivity{

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getbackpassword);

        RadioGroup Rd = findViewById(R.id.Radio); //라디오 그룹 이름표?

        EditText pw = findViewById(R.id.simplepw);
        String password = pw.getText().toString();

        final Button findpw = findViewById(R.id.find);
        Button getbackbike = findViewById(R.id.getback);

        int id = Rd.getCheckedRadioButtonId();
        final RadioButton rb = findViewById(id);
        final String number = rb.getText().toString();

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
                Read();
            }
        });



    }
    public void Read(){
        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("메시지","에러인가");
                String read = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(),read,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

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


