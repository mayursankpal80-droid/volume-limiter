package com.volumelimiter.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.core.app.NotificationCompat;

public class VolumeLimiterService extends Service {

    private static final String CHANNEL_ID = "VolumeLimiterChannel";
    private static final int NOTIFICATION_ID = 1;
    
    private AudioManager audioManager;
    private Handler handler;
    private Runnable volumeCheckRunnable;
    private SharedPreferences sharedPreferences;
    
    private int maxVolumeLimit = 50; // Default 50%
    private static final int CHECK_INTERVAL = 500; // Check every 500ms
    
    private BroadcastReceiver volumeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {
                checkAndLimitVolume();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        sharedPreferences = getSharedPreferences("VolumeLimiterPrefs", MODE_PRIVATE);
        
        // Load saved volume limit
        maxVolumeLimit = sharedPreferences.getInt("volumeLimit", 50);
        
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Volume Limiter Service",
                NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Keeps volume limiter active");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        
        // Register volume change receiver
        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volumeChangeReceiver, filter);
        
        // Setup periodic volume check
        handler = new Handler(Looper.getMainLooper());
        volumeCheckRunnable = new Runnable() {
            @Override
            public void run() {
                checkAndLimitVolume();
                handler.postDelayed(this, CHECK_INTERVAL);
            }
        };
        handler.post(volumeCheckRunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Reload volume limit in case it changed
        maxVolumeLimit = sharedPreferences.getInt("volumeLimit", 50);
        
        // Start foreground service with notification
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);
        
        return START_STICKY; // Service will restart if killed
    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            notificationIntent, 
            PendingIntent.FLAG_IMMUTABLE
        );

        return new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Volume Limiter Active")
            .setContentText("Maximum volume set to " + maxVolumeLimit + "%")
            .setSmallIcon(android.R.drawable.ic_lock_silent_mode_off)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build();
    }

    private void checkAndLimitVolume() {
        try {
            // Get current volume for music/media stream
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxSystemVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            
            // Calculate the maximum allowed volume based on percentage
            int maxAllowedVolume = (int) ((maxVolumeLimit / 100.0) * maxSystemVolume);
            
            // If current volume exceeds limit, reduce it
            if (currentVolume > maxAllowedVolume) {
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC, 
                    maxAllowedVolume, 
                    0 // No UI flags
                );
            }
            
            // Also check and limit alarm volume to prevent workarounds
            int currentAlarm = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
            int maxAlarm = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
            int maxAllowedAlarm = (int) ((maxVolumeLimit / 100.0) * maxAlarm);
            
            if (currentAlarm > maxAllowedAlarm) {
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxAllowedAlarm, 0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        // Stop handler
        if (handler != null && volumeCheckRunnable != null) {
            handler.removeCallbacks(volumeCheckRunnable);
        }
        
        // Unregister receiver
        try {
            unregisterReceiver(volumeChangeReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
