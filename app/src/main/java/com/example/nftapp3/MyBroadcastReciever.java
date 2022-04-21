package com.example.nftapp3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Toast.makeText(context, "BOOT COMPLETED!", Toast.LENGTH_LONG).show();
            Log.d("Boot", "boot completed");
        }
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            Toast.makeText(context, "CONNECTIVITY CHANGED!", Toast.LENGTH_LONG).show();
            Log.d("Network", "network changed");
        }

    }
}
