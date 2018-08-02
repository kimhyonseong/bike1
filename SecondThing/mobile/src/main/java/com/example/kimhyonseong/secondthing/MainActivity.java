package com.example.kimhyonseong.secondthing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button entrust = findViewById(R.id.entrustbike);
        Button getback = findViewById(R.id.getbackbike);

        entrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entrust = new Intent(getApplicationContext(),entrust.class);
                startActivity(entrust);
            }
        });

        getback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getback = new Intent(getApplicationContext(),Getback.class);
                startActivity(getback);
            }
        });

    }
}
