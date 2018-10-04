package com.example.kimhyonseong.newone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import br.com.bloder.magic.view.MagicButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MagicButton entrust = findViewById(R.id.lockB);
        MagicButton getback = findViewById(R.id.openB);
        MagicButton map = findViewById(R.id.mapB);

        entrust.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entrust = new Intent(getApplicationContext(),entrust.class);
                startActivity(entrust);
            }
        });

        getback.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getback = new Intent(getApplicationContext(),newGetback.class);
                startActivity(getback);
            }
        });

        map.setMagicButtonClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                MainActivity.super.onBackPressed();  //뒤로가기
            }
        });
    }
}
