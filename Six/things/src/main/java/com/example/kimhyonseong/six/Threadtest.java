package com.example.kimhyonseong.six;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

public class Threadtest extends Activity /*implements Runnable*/ {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB1 = Fbase.getReference("oe");
    DatabaseReference DB2 = Fbase.getReference("user1");
    DatabaseReference DB3 = Fbase.getReference("ko");
    Gpio mot11, mot12, mot13, mot14,mot21,mot22,mot23,mot24,mot31,mot32,mot33,mot34, LED, Inch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ted);

        PeripheralManager manager = PeripheralManager.getInstance();  //이거 잇으면 가상기기로 못돌림

        final TextView T1 = findViewById(R.id.one);
        final TextView T2 = findViewById(R.id.two);
        final TextView T3 = findViewById(R.id.three);

        if (manager.getGpioList().isEmpty()) {
            Log.i("없데", "사용할 핀 없음");
        } else {
            Log.i("있데", "List : " + manager.getGpioList());
        }
/*
        Thread thread1 = new Thread(() -> {
            try {
                LED = manager.openGpio("BCM2");
                LED.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                LED.setActiveType(Gpio.ACTIVE_LOW);

                while(true) {
                    LED.setValue(true);
                    Thread.sleep(100);
                    LED.setValue(false);
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Inch = manager.openGpio("BCM3");
                Inch.setDirection(Gpio.DIRECTION_IN);
                Inch.setActiveType(Gpio.ACTIVE_LOW);

                while (true){
                    //Toast.makeText(getApplicationContext(),"동작",Toast.LENGTH_SHORT).show();
                    if (Inch.getValue()){
                        try {
                            mot11 = manager.openGpio("BCM4");  //핀설정
                            mot11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);  //초기값 설정
                            mot11.setActiveType(Gpio.ACTIVE_HIGH);  //이거랑 위에랑 같은거같은데 이거 없으면 setValue가 안먹힘
                            mot12 = manager.openGpio("BCM17");
                            mot12.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot12.setActiveType(Gpio.ACTIVE_HIGH);
                            mot13 = manager.openGpio("BCM27");
                            mot13.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot13.setActiveType(Gpio.ACTIVE_HIGH);
                            mot14 = manager.openGpio("BCM22");
                            mot14.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot14.setActiveType(Gpio.ACTIVE_HIGH);

                            mot11.setValue(false);
                            mot12.setValue(false);
                            mot13.setValue(false);
                            mot14.setValue(false);
                            SystemClock.sleep(1);
                            for (int i = 0; i < 512; i++) {  //모터 돌리기 i값 조절로 바퀴수 조절 가능
                                mot11.setValue(true);
                                mot12.setValue(false);
                                mot13.setValue(false);
                                mot14.setValue(false);
                                SystemClock.sleep(1);

                                mot11.setValue(false);
                                mot12.setValue(true);
                                mot13.setValue(false);
                                mot14.setValue(false);
                                SystemClock.sleep(1);

                                mot11.setValue(false);
                                mot12.setValue(false);
                                mot13.setValue(true);
                                mot14.setValue(false);
                                SystemClock.sleep(1);

                                mot11.setValue(false);
                                mot12.setValue(false);
                                mot13.setValue(false);
                                mot14.setValue(true);
                                SystemClock.sleep(1);
                            }
                            mot11.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                            mot12.close();  //finally에 놓으면 try에 놓으라고 에러뜸
                            mot13.close();
                            mot14.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
        });*/
        T0 t0 = new T0();
        T1 t1 = new T1();
        //T2 t2 = new T2();

        t0.start();
        t1.start();
        //t2.run();
        /*try {
            LED = manager.openGpio("BCM2");
            LED.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            LED.setActiveType(Gpio.ACTIVE_LOW);

            for (int i=0; i<10; i++) {
                LED.setValue(true);
                Thread.sleep(100);
                LED.setValue(false);
                Thread.sleep(100);
            }
            LED.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }

//    @Override
//    public void run() {

//    }
}

    class T0 extends Thread{  //그냥 해봄

        Gpio mot11, mot12, mot13, mot14,mot21,mot22,mot23,mot24,mot31,mot32,mot33,mot34, LED, Inch;
        @Override
        public void run() {
            super.run();
            final PeripheralManager manager = PeripheralManager.getInstance();
            try {
                LED = manager.openGpio("BCM2");
                LED.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                LED.setActiveType(Gpio.ACTIVE_LOW);

                while(true) {
                    LED.setValue(true);
                    Thread.sleep(100);
                    LED.setValue(false);
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class T1 extends Thread{

        private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
        private DatabaseReference DB1 = Fbase.getReference("oe");
        Gpio mot11, mot12, mot13, mot14,mot21,mot22,mot23,mot24,mot31,mot32,mot33,mot34, LED, Inch;

        @Override
        public void run() {
            super.run();
            //Toast.makeText(getApplicationContext(),"되니",Toast.LENGTH_SHORT).show();
            final PeripheralManager manager = PeripheralManager.getInstance();
            try {
                mot11 = manager.openGpio("BCM4");  //핀설정
                mot11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);  //초기값 설정
                mot11.setActiveType(Gpio.ACTIVE_HIGH);  //이거랑 위에랑 같은거같은데 이거 없으면 setValue가 안먹힘
                mot12 = manager.openGpio("BCM17");
                mot12.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                mot12.setActiveType(Gpio.ACTIVE_HIGH);
                mot13 = manager.openGpio("BCM27");
                mot13.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                mot13.setActiveType(Gpio.ACTIVE_HIGH);
                mot14 = manager.openGpio("BCM22");
                mot14.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                mot14.setActiveType(Gpio.ACTIVE_HIGH);

                mot11.setValue(false);
                mot12.setValue(false);
                mot13.setValue(false);
                mot14.setValue(false);
                SystemClock.sleep(1);
                for (int i = 0; i < 512; i++) {  //모터 돌리기 i값 조절로 바퀴수 조절 가능
                    mot11.setValue(true);
                    mot12.setValue(false);
                    mot13.setValue(false);
                    mot14.setValue(false);
                    Thread.sleep(1);

                    mot11.setValue(false);
                    mot12.setValue(true);
                    mot13.setValue(false);
                    mot14.setValue(false);
                    Thread.sleep(1);

                    mot11.setValue(false);
                    mot12.setValue(false);
                    mot13.setValue(true);
                    mot14.setValue(false);
                    Thread.sleep(1);

                    mot11.setValue(false);
                    mot12.setValue(false);
                    mot13.setValue(false);
                    mot14.setValue(true);
                    Thread.sleep(1);
                }
                mot11.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                mot12.close();  //finally에 놓으면 try에 놓으라고 에러뜸
                mot13.close();
                mot14.close();
                Thread.sleep(10);
            }catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class T2 extends Thread{
        @Override
        public void run() {
            super.run();
            final PeripheralManager manager = PeripheralManager.getInstance();

            try {

                Gpio LED = manager.openGpio("BCM10");
                LED.setActiveType(Gpio.ACTIVE_HIGH);
                LED.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

                for (int i=0; i<100; i++) {
                    LED.setValue(true);
                    SystemClock.sleep(100);
                    LED.setValue(false);
                }
                LED.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*
class T3 extends Thread{

    private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    private DatabaseReference DB3 = Fbase.getReference("ko");

    private Gpio mot31,mot32,mot33,mot34;

    @Override
    public void run() {
        super.run();
        final PeripheralManager manager = PeripheralManager.getInstance();
        DB3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Sinal : dataSnapshot.getChildren()){
                    String State = (String) Sinal.child("state").getValue();

                    if ("open".equals(State)) {
                        //T3.setText("open");
                        try {
                            mot31 = manager.openGpio("BCM5");  //핀설정
                            mot31.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);  //인풋 아웃풋 설정
                            mot31.setActiveType(Gpio.ACTIVE_HIGH);  //초기값 설정
                            mot32 = manager.openGpio("BCM6");
                            mot32.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot32.setActiveType(Gpio.ACTIVE_HIGH);
                            mot33 = manager.openGpio("BCM13");
                            mot33.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot33.setActiveType(Gpio.ACTIVE_HIGH);
                            mot34 = manager.openGpio("BCM19");
                            mot34.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot34.setActiveType(Gpio.ACTIVE_HIGH);

                            mot31.setValue(false);
                            mot32.setValue(false);
                            mot33.setValue(false);
                            mot34.setValue(false);
                            SystemClock.sleep(1);
                            for (int i = 0; i < 512; i++) {  //모터 돌리기 i값 조절로 바퀴수 조절 가능
                                mot31.setValue(true);
                                mot32.setValue(false);
                                mot33.setValue(false);
                                mot34.setValue(false);
                                SystemClock.sleep(1);

                                mot31.setValue(false);
                                mot32.setValue(true);
                                mot33.setValue(false);
                                mot34.setValue(false);
                                SystemClock.sleep(1);

                                mot31.setValue(false);
                                mot32.setValue(false);
                                mot33.setValue(true);
                                mot34.setValue(false);
                                SystemClock.sleep(1);

                                mot31.setValue(false);
                                mot32.setValue(false);
                                mot33.setValue(false);
                                mot34.setValue(true);
                                SystemClock.sleep(1);
                            }
                            mot31.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                            mot32.close();  //finally에 놓으면 try에 놓으라고 에러뜸
                            mot33.close();
                            mot34.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if("lock".equals(State)){
                        //T3.setText("lock");
                        try {
                            mot31 = manager.openGpio("BCM5");
                            mot31.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot31.setActiveType(Gpio.ACTIVE_HIGH);
                            mot32 = manager.openGpio("BCM6");
                            mot32.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot32.setActiveType(Gpio.ACTIVE_HIGH);
                            mot33 = manager.openGpio("BCM13");
                            mot33.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot33.setActiveType(Gpio.ACTIVE_HIGH);
                            mot34 = manager.openGpio("BCM19");
                            mot34.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot34.setActiveType(Gpio.ACTIVE_HIGH);

                            mot31.setValue(false);
                            mot32.setValue(false);
                            mot33.setValue(false);
                            mot34.setValue(false);
                            SystemClock.sleep(1);

                            for (int i = 0; i < 512; i++) {  //모터 동작
                                mot31.setValue(false);
                                mot32.setValue(false);
                                mot33.setValue(false);
                                mot34.setValue(true);
                                SystemClock.sleep(1);

                                mot31.setValue(false);
                                mot32.setValue(false);
                                mot33.setValue(true);
                                mot34.setValue(false);
                                SystemClock.sleep(1);

                                mot31.setValue(false);
                                mot32.setValue(true);
                                mot33.setValue(false);
                                mot34.setValue(false);
                                SystemClock.sleep(1);

                                mot31.setValue(true);
                                mot32.setValue(false);
                                mot33.setValue(false);
                                mot34.setValue(false);
                                SystemClock.sleep(1);
                            }

                            mot31.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                            mot32.close();  //이거 finally에 넣으면 try로 옮기라고 뜸
                            mot33.close();
                            mot34.close();
                            SystemClock.sleep(1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}*/