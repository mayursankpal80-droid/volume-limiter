package com.volumelimiter.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SeekBar volumeSeekBar;
    private TextView volumePercentText;
    private Button startServiceButton;
    private Button stopServiceButton;
    private TextView statusText;
    private View statusIndicator;
    
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "VolumeLimiterPrefs";
    private static final String KEY_VOLUME_LIMIT = "volumeLimit";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        // Initialize views
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        volumePercentText = findViewById(R.id.volumePercentText);
        startServiceButton = findViewById(R.id.startServiceButton);
        stopServiceButton = findViewById(R.id.stopServiceButton);
        statusText = findViewById(R.id.statusText);
        statusIndicator = findViewById(R.id.statusIndicator);
        
        // Load saved volume limit
        int savedLimit = sharedPreferences.getInt(KEY_VOLUME_LIMIT, 50);
        volumeSeekBar.setProgress(savedLimit);
        volumePercentText.setText(savedLimit + "%");
        
        // Setup SeekBar listener
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumePercentText.setText(progress + "%");
                // Save the volume limit
                sharedPreferences.edit().putInt(KEY_VOLUME_LIMIT, progress).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // Start service button
        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVolumeLimiterService();
            }
        });
        
        // Stop service button
        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVolumeLimiterService();
            }
        });
        
        // Update UI based on service status
        updateServiceStatus();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
    }
    
    private void startVolumeLimiterService() {
        Intent serviceIntent = new Intent(this, VolumeLimiterService.class);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
        
        updateServiceStatus();
    }
    
    private void stopVolumeLimiterService() {
        Intent serviceIntent = new Intent(this, VolumeLimiterService.class);
        stopService(serviceIntent);
        updateServiceStatus();
    }
    
    private void updateServiceStatus() {
        if (isServiceRunning(VolumeLimiterService.class)) {
            statusText.setText("Service Active - Volume Protected");
            statusText.setTextColor(0xFF4CAF50);
            
            // Set status indicator to green
            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);
            shape.setColor(0xFF4CAF50);
            statusIndicator.setBackground(shape);
            
        } else {
            statusText.setText("Service Stopped - No Protection");
            statusText.setTextColor(0xFF757575);
            
            // Set status indicator to gray
            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);
            shape.setColor(0xFF9E9E9E);
            statusIndicator.setBackground(shape);
        }
    }
    
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
