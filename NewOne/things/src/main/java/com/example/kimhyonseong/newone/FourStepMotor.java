package com.example.kimhyonseong.newone;

import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;


import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Calendar;

public class FourStepMotor extends Activity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB = Fbase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ted);

        final TextView Tv1 = findViewById(R.id.one);
        final TextView Tv2 = findViewById(R.id.two);
        final TextView Tv3 = findViewById(R.id.three);

        PeripheralManager manager = PeripheralManager.getInstance();  //이거 잇으면 가상기기로 못돌림

        if (manager.getGpioList().isEmpty()) {
            Log.i("없데", "사용할 핀 없음");
        } else {
            Log.i("있데", "List : " + manager.getGpioList());  //Logcat에서 확인가능
        }

        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DS : dataSnapshot.getChildren()){
                    String state1 = (String) DS.child("/user1/state").getValue();
                    String state2 = (String) DS.child("/user2/state").getValue();
                    String state3 = (String) DS.child("/user3/state").getValue();

                    if ("open".equals(state1)){
                        Tv1.setText(R.string.open);
                    } else Tv1.setText(R.string.close);
                    if ("open".equals(state2)){
                        Tv2.setText(R.string.open);
                    } else Tv2.setText(R.string.close);
                    if ("open".equals(state3)){
                        Tv3.setText(R.string.open);
                    } else Tv3.setText(R.string.close);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Body body = new Body();
        Motor1 m1 = new Motor1();
        Motor2 m2 = new Motor2();
        Motor3 m3 = new Motor3();
        Shock shock = new Shock();

        body.start();
        m1.start();
        m2.start();
        m3.start();
        shock.start();
    }
}

class Shock extends Thread{
    @Override
    public void run(){
        super.run();
        final PeripheralManager manager = PeripheralManager.getInstance();

        while (true) {
            try {
                Gpio shock = manager.openGpio("BCM12");
                shock.setDirection(Gpio.DIRECTION_IN);
                shock.setActiveType(Gpio.ACTIVE_HIGH);
                Gpio buzz = manager.openGpio("BCM16");
                buzz.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                buzz.setActiveType(Gpio.ACTIVE_HIGH);

                if (shock.getValue()) {
                    buzz.setValue(true);
                    Thread.sleep(1000);
                }

                buzz.close();
                shock.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

class Body extends Thread{  //인체감지센서 및 LED
    @Override
    public void run() {
        super.run();
        final PeripheralManager manager = PeripheralManager.getInstance();  //핀설정을 위한 매니저
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        SimpleDateFormat time = new SimpleDateFormat("HH");
        String Ftime = time.format(Calendar.getInstance().getTime());  //시간


        while (true) {
            try {
                Gpio LED = manager.openGpio("BCM2");
                LED.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                LED.setActiveType(Gpio.ACTIVE_HIGH);
                Gpio body = manager.openGpio("BCM3");
                body.setDirection(Gpio.DIRECTION_IN);
                body.setActiveType(Gpio.ACTIVE_HIGH);

                while ((hour == 12) || (hour == 13) || (hour == 14) || (Ftime.equals("15"))) {  //12,13,14,15시에 실행
                    if (body.getValue()) {
                        LED.setValue(true);
                        Thread.sleep(1000);
                    } else LED.setValue(false);
                }
                LED.close();
                body.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

class Motor1 extends Thread{  //1번 모터

    private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    private DatabaseReference DB = Fbase.getReference();
    private Gpio mot11, mot12, mot13, mot14;

    @Override
    public void run() {
        super.run();
        final PeripheralManager manager = PeripheralManager.getInstance();
        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DB : dataSnapshot.getChildren()){
                    String state = (String)DB.child("/user1/state").getValue();
                    if (state.equals("open")){
                        try {
                            mot11 = manager.openGpio("BCM4");
                            mot11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot11.setActiveType(Gpio.ACTIVE_HIGH);
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
                            mot14.setValue(false);
                            mot11.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                            mot12.close();  //finally에 놓으면 try에 놓으라고 에러뜸
                            mot13.close();
                            mot14.close();
                            Thread.sleep(10);
                        }catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (state.equals("lock")){
                        try {
                            mot11 = manager.openGpio("BCM4");
                            mot11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot11.setActiveType(Gpio.ACTIVE_HIGH);
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
                                mot11.setValue(false);
                                mot12.setValue(false);
                                mot13.setValue(false);
                                mot14.setValue(true);
                                Thread.sleep(1);

                                mot11.setValue(false);
                                mot12.setValue(false);
                                mot13.setValue(true);
                                mot14.setValue(false);
                                Thread.sleep(1);

                                mot11.setValue(false);
                                mot12.setValue(true);
                                mot13.setValue(false);
                                mot14.setValue(false);
                                Thread.sleep(1);

                                mot11.setValue(true);
                                mot12.setValue(false);
                                mot13.setValue(false);
                                mot14.setValue(false);
                                Thread.sleep(1);
                            }
                            mot11.setValue(false);
                            mot11.close();
                            mot12.close();
                            mot13.close();
                            mot14.close();
                            Thread.sleep(10);
                        }catch (IOException | InterruptedException e) {
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
}

class Motor2 extends Thread{  //2번 모터

    private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    private DatabaseReference DB = Fbase.getReference();
    private Gpio mot21,mot22,mot23,mot24;

    @Override
    public void run() {
        super.run();
        final PeripheralManager manager = PeripheralManager.getInstance();

        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DB : dataSnapshot.getChildren()) {
                    String state = (String) DB.child("/user2/state").getValue();

                    if (state.equals("open")) {
                        try {
                            mot21 = manager.openGpio("BCM5");
                            mot21.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot21.setActiveType(Gpio.ACTIVE_HIGH);
                            mot22 = manager.openGpio("BCM6");
                            mot22.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot22.setActiveType(Gpio.ACTIVE_HIGH);
                            mot23 = manager.openGpio("BCM13");
                            mot23.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot23.setActiveType(Gpio.ACTIVE_HIGH);
                            mot24 = manager.openGpio("BCM26");
                            mot24.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot24.setActiveType(Gpio.ACTIVE_HIGH);

                            mot21.setValue(false);
                            mot22.setValue(false);
                            mot23.setValue(false);
                            mot24.setValue(false);
                            SystemClock.sleep(1);
                            for (int i = 0; i < 512; i++) {
                                mot21.setValue(true);
                                mot22.setValue(false);
                                mot23.setValue(false);
                                mot24.setValue(false);
                                Thread.sleep(1);

                                mot21.setValue(false);
                                mot22.setValue(true);
                                mot23.setValue(false);
                                mot24.setValue(false);
                                Thread.sleep(1);

                                mot21.setValue(false);
                                mot22.setValue(false);
                                mot23.setValue(true);
                                mot24.setValue(false);
                                Thread.sleep(1);

                                mot21.setValue(false);
                                mot22.setValue(false);
                                mot23.setValue(false);
                                mot24.setValue(true);
                                Thread.sleep(1);
                            }
                            mot24.setValue(false);

                            mot21.close();
                            mot22.close();
                            mot23.close();
                            mot24.close();
                            Thread.sleep(10);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (state.equals("lock")) {
                        try {
                            mot21 = manager.openGpio("BCM5");
                            mot21.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot21.setActiveType(Gpio.ACTIVE_HIGH);
                            mot22 = manager.openGpio("BCM6");
                            mot22.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot22.setActiveType(Gpio.ACTIVE_HIGH);
                            mot23 = manager.openGpio("BCM13");
                            mot23.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot23.setActiveType(Gpio.ACTIVE_HIGH);
                            mot24 = manager.openGpio("BCM26");
                            mot24.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot24.setActiveType(Gpio.ACTIVE_HIGH);

                            mot21.setValue(false);
                            mot22.setValue(false);
                            mot23.setValue(false);
                            mot24.setValue(false);
                            SystemClock.sleep(1);
                            for (int i = 0; i < 512; i++) {
                                mot21.setValue(false);
                                mot22.setValue(false);
                                mot23.setValue(false);
                                mot24.setValue(true);
                                Thread.sleep(1);

                                mot21.setValue(false);
                                mot22.setValue(false);
                                mot23.setValue(true);
                                mot24.setValue(false);
                                Thread.sleep(1);

                                mot21.setValue(false);
                                mot22.setValue(true);
                                mot23.setValue(false);
                                mot24.setValue(false);
                                Thread.sleep(1);

                                mot21.setValue(true);
                                mot22.setValue(false);
                                mot23.setValue(false);
                                mot24.setValue(false);
                                Thread.sleep(1);
                            }
                            mot21.setValue(false);
                            mot21.close();
                            mot22.close();
                            mot23.close();
                            mot24.close();
                            Thread.sleep(10);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }
}

class Motor3 extends Thread {  //3번 모터

    private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    private DatabaseReference DB = Fbase.getReference();
    private Gpio mot31, mot32, mot33, mot34;

    @Override
    public void run() {
        super.run();
        final PeripheralManager manager = PeripheralManager.getInstance();
        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Sinal : dataSnapshot.getChildren()) {
                    String State = (String) Sinal.child("/user3/state").getValue();

                    if (State.equals("open")) {
                        try {
                            mot31 = manager.openGpio("BCM18");  //핀설정
                            mot31.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);  //인풋 아웃풋 설정
                            mot31.setActiveType(Gpio.ACTIVE_HIGH);  //초기값 설정
                            mot32 = manager.openGpio("BCM23");
                            mot32.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot32.setActiveType(Gpio.ACTIVE_HIGH);
                            mot33 = manager.openGpio("BCM24");
                            mot33.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot33.setActiveType(Gpio.ACTIVE_HIGH);
                            mot34 = manager.openGpio("BCM25");
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
                            mot34.setValue(false);
                            mot31.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                            mot32.close();  //finally에 놓으면 try에 놓으라고 에러뜸
                            mot33.close();
                            mot34.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (State.equals("lock")) {
                        try {
                            mot31 = manager.openGpio("BCM18");
                            mot31.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot31.setActiveType(Gpio.ACTIVE_HIGH);
                            mot32 = manager.openGpio("BCM23");
                            mot32.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot32.setActiveType(Gpio.ACTIVE_HIGH);
                            mot33 = manager.openGpio("BCM24");
                            mot33.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot33.setActiveType(Gpio.ACTIVE_HIGH);
                            mot34 = manager.openGpio("BCM25");
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
                            mot31.setValue(false);
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
}
