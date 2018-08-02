package com.example.kimhyonseong.four;

import android.app.Activity;
import android.os.Bundle;

public class threadE extends Activity{
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.thre);

    }
    public class User1 extends Thread{
        @Override
        public void run(){

        }
    }
}
