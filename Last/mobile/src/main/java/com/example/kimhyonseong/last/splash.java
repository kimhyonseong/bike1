package com.example.kimhyonseong.last;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity {
    @Override
    protected void onCreate(Bundle saveinstanceState){
        super.onCreate(saveinstanceState);
        try{
            Thread.sleep(4000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        Intent main = new Intent(this,MapsActivity.class);
        startActivity(main);
        finish();
    }
}
