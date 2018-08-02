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
import java.util.List;

public class MainActivity extends Activity {

    FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    DatabaseReference DB1 = Fbase.getReference("oe");
    DatabaseReference DB2 = Fbase.getReference("user1");
    DatabaseReference DB3 = Fbase.getReference("ko");

    Gpio mot11,mot12,mot13,mot14,mot21,mot22,mot23,mot24,mot31,mot32,mot33,mot34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PeripheralManager manager = PeripheralManager.getInstance();  //이거 잇으면 가상기기로 못돌림

        final TextView T1 = findViewById(R.id.one);
        final TextView T2 = findViewById(R.id.two);
        final TextView T3 = findViewById(R.id.three);
        List<String> portList = manager.getGpioList();

        if (portList.isEmpty()){
            Log.i("없데","사용할 핀 없음");
        }
        else {
            Log.i("있데","List : "+portList);
        }
        DB1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Sinal : dataSnapshot.getChildren()) {
                    String State = (String) Sinal.child("state").getValue();

                    if ("open".equals(State)) {
                        try {
                            T1.setText("open");
                            mot11 = manager.openGpio("BCM2");  //핀설정
                            mot11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);  //초기값 설정
                            mot11.setActiveType(Gpio.ACTIVE_HIGH);  //이거랑 위에랑 같은거같은데 이거 없으면 setValue가 안먹힘
                            mot12 = manager.openGpio("BCM3");
                            mot12.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot12.setActiveType(Gpio.ACTIVE_HIGH);
                            mot13 = manager.openGpio("BCM4");
                            mot13.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot13.setActiveType(Gpio.ACTIVE_HIGH);
                            mot14 = manager.openGpio("BCM17");
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

                    else if ("lock".equals(State)) {
                        try {
                            T1.setText("lock");
                            mot11 = manager.openGpio("BCM2");
                            mot11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot11.setActiveType(Gpio.ACTIVE_HIGH);
                            mot12 = manager.openGpio("BCM3");
                            mot12.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot12.setActiveType(Gpio.ACTIVE_HIGH);
                            mot13 = manager.openGpio("BCM4");
                            mot13.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot13.setActiveType(Gpio.ACTIVE_HIGH);
                            mot14 = manager.openGpio("BCM17");
                            mot14.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot14.setActiveType(Gpio.ACTIVE_HIGH);

                            mot11.setValue(false);
                            mot12.setValue(false);
                            mot13.setValue(false);
                            mot14.setValue(false);
                            SystemClock.sleep(1);

                            for (int i = 0; i < 512; i++) {  //모터 동작
                                mot11.setValue(false);
                                mot12.setValue(false);
                                mot13.setValue(false);
                                mot14.setValue(true);
                                SystemClock.sleep(1);

                                mot11.setValue(false);
                                mot12.setValue(false);
                                mot13.setValue(true);
                                mot14.setValue(false);
                                SystemClock.sleep(1);

                                mot11.setValue(false);
                                mot12.setValue(true);
                                mot13.setValue(false);
                                mot14.setValue(false);
                                SystemClock.sleep(1);

                                mot11.setValue(true);
                                mot12.setValue(false);
                                mot13.setValue(false);
                                mot14.setValue(false);
                                SystemClock.sleep(1);
                                Toast.makeText(getApplicationContext(), "???" + i, Toast.LENGTH_SHORT).show();
                            }

                            mot11.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                            mot12.close();  //이거 finally에 넣으면 try로 옮기라고 뜸
                            mot13.close();
                            mot14.close();
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

        DB2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Sinal : dataSnapshot.getChildren()){
                    String State = (String) Sinal.child("state").getValue();

                    if ("open".equals(State)) {
                        T2.setText("open");
                        {
                            try {
                                mot21 = manager.openGpio("BCM22");  //핀설정
                                mot21.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);  //초기값 설정
                                mot21.setActiveType(Gpio.ACTIVE_HIGH);  //이거랑 위에랑 같은거같은데 이거 없으면 setValue가 안먹힘
                                mot22 = manager.openGpio("BCM10");
                                mot22.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                                mot22.setActiveType(Gpio.ACTIVE_HIGH);
                                mot23 = manager.openGpio("BCM9");
                                mot23.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                                mot23.setActiveType(Gpio.ACTIVE_HIGH);
                                mot24 = manager.openGpio("BCM11");
                                mot24.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                                mot24.setActiveType(Gpio.ACTIVE_HIGH);

                                mot21.setValue(false);
                                mot22.setValue(false);
                                mot23.setValue(false);
                                mot24.setValue(false);
                                SystemClock.sleep(1);
                                for (int i = 0; i < 512; i++) {  //모터 돌리기 i값 조절로 바퀴수 조절 가능
                                    mot21.setValue(true);
                                    mot22.setValue(false);
                                    mot23.setValue(false);
                                    mot24.setValue(false);
                                    SystemClock.sleep(1);

                                    mot21.setValue(false);
                                    mot22.setValue(true);
                                    mot23.setValue(false);
                                    mot24.setValue(false);
                                    SystemClock.sleep(1);

                                    mot21.setValue(false);
                                    mot22.setValue(false);
                                    mot23.setValue(true);
                                    mot24.setValue(false);
                                    SystemClock.sleep(1);

                                    mot21.setValue(false);
                                    mot22.setValue(false);
                                    mot23.setValue(false);
                                    mot24.setValue(true);
                                    SystemClock.sleep(1);
                                }
                                mot21.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                                mot22.close();  //finally에 놓으면 try에 놓으라고 에러뜸
                                mot23.close();
                                mot24.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if("lock".equals(State)){
                        T2.setText("lock");
                        try {
                            mot11 = manager.openGpio("BCM22");
                            mot11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot21.setActiveType(Gpio.ACTIVE_HIGH);
                            mot22 = manager.openGpio("BCM10");
                            mot22.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot22.setActiveType(Gpio.ACTIVE_HIGH);
                            mot23 = manager.openGpio("BCM9");
                            mot23.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot23.setActiveType(Gpio.ACTIVE_HIGH);
                            mot24 = manager.openGpio("BCM11");
                            mot24.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot24.setActiveType(Gpio.ACTIVE_HIGH);

                            mot21.setValue(false);
                            mot22.setValue(false);
                            mot23.setValue(false);
                            mot24.setValue(false);
                            SystemClock.sleep(1);

                            for (int i = 0; i < 512; i++) {  //모터 동작
                                mot21.setValue(false);
                                mot22.setValue(false);
                                mot23.setValue(false);
                                mot24.setValue(true);
                                SystemClock.sleep(1);

                                mot21.setValue(false);
                                mot22.setValue(false);
                                mot23.setValue(true);
                                mot24.setValue(false);
                                SystemClock.sleep(1);

                                mot21.setValue(false);
                                mot22.setValue(true);
                                mot23.setValue(false);
                                mot24.setValue(false);
                                SystemClock.sleep(1);

                                mot21.setValue(true);
                                mot22.setValue(false);
                                mot23.setValue(false);
                                mot24.setValue(false);
                                SystemClock.sleep(1);
                            }

                            mot21.close();  //핀 사용 끝냄 -> 이거 없으면 트라이 다시 들어올때 이미 사용중이라고 에러뜸
                            mot22.close();  //이거 finally에 넣으면 try로 옮기라고 뜸
                            mot23.close();
                            mot24.close();
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

        DB3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Sinal : dataSnapshot.getChildren()){
                    String State = (String) Sinal.child("state").getValue();

                    if ("open".equals(State)) {
                        T3.setText("open");
                        try {
                            mot31 = manager.openGpio("BCM5");  //핀설정
                            mot31.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);  //초기값 설정
                            mot31.setActiveType(Gpio.ACTIVE_HIGH);  //이거랑 위에랑 같은거같은데 이거 없으면 setValue가 안먹힘
                            mot32 = manager.openGpio("BCM6");
                            mot32.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot32.setActiveType(Gpio.ACTIVE_HIGH);
                            mot33 = manager.openGpio("BCM13");
                            mot33.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            mot33.setActiveType(Gpio.ACTIVE_HIGH);
                            mot24 = manager.openGpio("BCM19");
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
                        T3.setText("lock");
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
}

