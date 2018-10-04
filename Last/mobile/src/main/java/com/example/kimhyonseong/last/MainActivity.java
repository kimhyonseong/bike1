package com.example.kimhyonseong.last;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.bloder.magic.view.MagicButton;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference("kim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MagicButton entrust = findViewById(R.id.lockB);
        final MagicButton getback = findViewById(R.id.openB);
        MagicButton map = findViewById(R.id.mapB);

        entrust.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entrust = new Intent(getApplicationContext(),entrust.class);
                startActivity(entrust);
                finish();
            }
        });

        getback.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getback = new Intent(getApplicationContext(),newGetback.class);
                startActivity(getback);
                finish();
            }
        });

        map.setMagicButtonClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });
    }
}
