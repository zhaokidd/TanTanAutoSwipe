package com.example.tantanauto.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Fake Service :  start fake-service first and then start the real-service ,in this way, the real service will have a higher priority
 */
public class FakeService extends Service {
    public FakeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
