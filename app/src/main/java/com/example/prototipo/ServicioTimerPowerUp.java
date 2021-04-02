package com.example.prototipo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class ServicioTimerPowerUp extends Service {

    //Formato del Cronometro
    String FORMAT = "%d h : %d m : %d s";
    public static String TAG = "Servicio";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Integer[] timeRemaining = {300}; //5 Minutos

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Intent local = new Intent();
                local.setAction("Counter");
                timeRemaining[0]--;

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


}

