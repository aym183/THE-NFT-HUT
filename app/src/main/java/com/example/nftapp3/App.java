package com.example.nftapp3;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String channel1_ID = "Channel1";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotficationChannels();

    }

    private void createNotficationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(channel1_ID, "channel1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }

}
