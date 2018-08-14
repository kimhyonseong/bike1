package com.example.kimhyonseong.six;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class TwoStepMotor extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mot1 M1 = new Mot1();
        Mot2 M2 = new Mot2();
        Mot3 M3 = new Mot3();

        M1.run();
        M2.run();
        M3.run();
    }
}
class Mot1 extends Thread {
    private PeripheralManager manager = PeripheralManager.getInstance();
    private Gpio step , dir;
    private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    private DatabaseReference DB1 = Fbase.getReference("oe");

    @Override
    public void run(){
        super.run();
        DB1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DB : dataSnapshot.getChildren()){
                    String state = (String)DB.child("state").getValue();

                    if ("open".equals(state)){
                        try{
                            step = manager.openGpio("BCM4");    //돌아가는 변수
                            step.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            step.setActiveType(Gpio.ACTIVE_HIGH);
                            step.setValue(false);

                            dir = manager.openGpio("BCM17");    //방향을 제시하는 변수
                            dir.setActiveType(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            dir.setActiveType(Gpio.ACTIVE_HIGH);
                            dir.setValue(true);      //시계방향

                            for (int i = 0; i<400; i++){
                                step.setValue(true);
                                SystemClock.sleep(1);
                            }
                            dir.close();
                            step.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else if ("lock".equals(state)){
                        try {
                            step = manager.openGpio("BCM4");
                            step.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            step.setActiveType(Gpio.ACTIVE_HIGH);
                            step.setValue(false);

                            dir = manager.openGpio("BCM17");
                            dir.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            dir.setActiveType(Gpio.ACTIVE_HIGH);
                            dir.setValue(false);      //반시계방향

                            for (int i=0; i<400; i++){
                                step.setValue(true);
                                SystemClock.sleep(1);
                            }
                            step.close();
                            dir.close();
                        } catch(IOException e){
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

class Mot2 extends Thread {
    private PeripheralManager manager = PeripheralManager.getInstance();
    private Gpio step , dir;
    private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    private DatabaseReference DB2 = Fbase.getReference("user1");

    @Override
    public void run(){
        super.run();
        DB2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DB : dataSnapshot.getChildren()){
                    String state = (String)DB.child("state").getValue();

                    if ("open".equals(state)){
                        try{
                            step = manager.openGpio("BCM27");    //돌아가는 변수
                            step.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            step.setActiveType(Gpio.ACTIVE_HIGH);
                            step.setValue(false);

                            dir = manager.openGpio("BCM22");    //방향을 제시하는 변수
                            dir.setActiveType(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            dir.setActiveType(Gpio.ACTIVE_HIGH);
                            dir.setValue(true);      //시계방향

                            for (int i = 0; i<400; i++){
                                step.setValue(true);
                                SystemClock.sleep(1);
                            }
                            dir.close();
                            step.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else if ("lock".equals(state)){
                        try {
                            step = manager.openGpio("BCM27");
                            step.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            step.setActiveType(Gpio.ACTIVE_HIGH);
                            step.setValue(false);

                            dir = manager.openGpio("BCM22");
                            dir.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            dir.setActiveType(Gpio.ACTIVE_HIGH);
                            dir.setValue(false);      //반시계방향

                            for (int i=0; i<400; i++){
                                step.setValue(true);
                                SystemClock.sleep(1);
                            }
                            step.close();
                            dir.close();
                        } catch(IOException e){
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

class Mot3 extends Thread {
    private PeripheralManager manager = PeripheralManager.getInstance();
    private Gpio step , dir;
    private FirebaseDatabase Fbase = FirebaseDatabase.getInstance();
    private DatabaseReference DB3 = Fbase.getReference("ko");

    @Override
    public void run(){
        super.run();
        DB3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot DB : dataSnapshot.getChildren()){
                    String state = (String)DB.child("state").getValue();

                    if ("open".equals(state)){
                        try{
                            step = manager.openGpio("BCM10");    //돌아가는 변수
                            step.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            step.setActiveType(Gpio.ACTIVE_HIGH);
                            step.setValue(false);

                            dir = manager.openGpio("BCM9");    //방향을 제시하는 변수
                            dir.setActiveType(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            dir.setActiveType(Gpio.ACTIVE_HIGH);
                            dir.setValue(true);      //시계방향

                            for (int i = 0; i<400; i++){
                                step.setValue(true);
                                SystemClock.sleep(1);
                            }
                            dir.close();
                            step.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else if ("lock".equals(state)){
                        try {
                            step = manager.openGpio("BCM10");
                            step.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            step.setActiveType(Gpio.ACTIVE_HIGH);
                            step.setValue(false);

                            dir = manager.openGpio("BCM9");
                            dir.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                            dir.setActiveType(Gpio.ACTIVE_HIGH);
                            dir.setValue(false);      //반시계방향

                            for (int i=0; i<400; i++){
                                step.setValue(true);
                                SystemClock.sleep(1);
                            }
                            step.close();
                            dir.close();
                        } catch(IOException e){
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