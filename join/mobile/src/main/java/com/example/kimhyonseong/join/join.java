package com.example.kimhyonseong.join;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class join extends AppCompatActivity {

    FirebaseDatabase Fdata= FirebaseDatabase.getInstance();
    DatabaseReference DB = Fdata.getReference("data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        Button bn1 = findViewById(R.id.bt1);
        Button bn2 = findViewById(R.id.login);
        final EditText simpleedit = findViewById(R.id.simpleedit);
        final EditText pwedit = findViewById(R.id.pwedit);
        final EditText Email = findViewById(R.id.emailedit);

        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String pw = simpleedit.getText().toString();
                String check = pwedit.getText().toString();
                String email = Email.getText().toString();

                if (pw==check) {
                    DB.child("user").child(pw).child("E-mail").setValue(email);
                }
            }
        });

        bn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        });

    }




}
