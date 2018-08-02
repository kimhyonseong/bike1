package com.example.kimhyonseong.join;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Iterator;

public class Login extends AppCompatActivity{

    FirebaseDatabase Fbase =  FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference("user");

    FirebaseAuth mAuth;
    FirebaseUser muser;

    String username;
    String url;

    final String TAG = Login.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        final EditText ID = findViewById(R.id.idedit);
        final EditText PW = findViewById(R.id.pwedit);

        Button bn1 = findViewById(R.id.bt2);


        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = ID.getText().toString();
                final String pw = PW.getText().toString();

                DB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> read = dataSnapshot.getChildren().iterator();
                        while(read.hasNext())
                        {
                            if(read.next().getKey().equals(user))
                            {
                                if(read.next().getKey().equals(pw)) {
                                    Toast.makeText(getApplicationContext(), "로그인!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        Toast.makeText(getApplicationContext(),"존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
