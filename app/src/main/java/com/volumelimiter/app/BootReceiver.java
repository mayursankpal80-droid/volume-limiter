package com.volumelimiter.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    
    private static final String TAG = "VolumeLimiter";
    private static final String PREFS_NAME = "VolumeLimiterPrefs";
    private static final String KEY_SERVICE_ENABLED = "serviceEnabled";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        
        if (Intent.ACTION_BOOT_COMPLETED.equals(action) || 
            "android.intent.action.QUICKBOOT_POWERON".equals(action)) {
            
            Log.d(TAG, "Boot completed - checking if service should start");
            
            // Check if service was previously enabled
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            boolean wasEnabled = prefs.getBoolean(KEY_SERVICE_ENABLED, false);
            
            if (wasEnabled) {
                Log.d(TAG, "Starting Volume Limiter Service on boot");
                Intent serviceIntent = new Intent(context, VolumeLimiterService.class);
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent);
                } else {
                    context.startService(serviceIntent);
                }
            }
        }
    }
}
