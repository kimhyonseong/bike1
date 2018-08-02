package com.example.kimhyonseong.secondthing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class findpw extends AppCompatActivity{

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpw);


    }
}
