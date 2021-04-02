package com.example.prototipo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.example.prototipo.MainActivity.isPowerActive;

public class ServicioTimer extends Service {

    //Formato del Cronometro
    String FORMAT = "%d h : %d m : %d s";
    public static String TAG = "Servicio";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int CronoTime = intent.getExtras().getInt("CronoTime");
        final int cancelTimer = intent.getExtras().getInt("cancelTimer");
        final Integer[] timeRemaining = {CronoTime}; //Tiempo

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Intent local = new Intent();
                local.setAction("Counter");
                timeRemaining[0]--;

                if(cancelTimer == 1){
                    timer.cancel();
                }

                if(timeRemaining[0] <= 0){
                    local.putExtra("Fin", 0);
                    timer.cancel();
                }

                local.putExtra("TimeR", timeRemaining[0]);
                sendBroadcast(local);

            }
        }, 0 , 1000);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){

    }

}

