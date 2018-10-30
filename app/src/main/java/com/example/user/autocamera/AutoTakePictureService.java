package com.example.user.autocamera;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class AutoTakePictureService extends Service {
    private MyReceiver receiver;
    public AutoTakePictureService() {

    }
    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("SERVICE");
        registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
