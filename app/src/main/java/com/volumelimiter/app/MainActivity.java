package com.volumelimiter.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
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
    private static final String KEY_SERVICE_ENABLED = "serviceEnabled";
    
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
        
        // Check battery optimization on first run
        checkBatteryOptimization();
        
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
    
    private void checkBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            String packageName = getPackageName();
            
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                new AlertDialog.Builder(this)
                    .setTitle("Battery Optimization")
                    .setMessage("To keep the volume limiter running in the background, please disable battery optimization for this app.")
                    .setPositiveButton("Settings", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                        intent.setData(Uri.parse("package:" + packageName));
                        startActivity(intent);
                    })
                    .setNegativeButton("Later", null)
                    .show();
            }
        }
    }
    
    private void startVolumeLimiterService() {
        Intent serviceIntent = new Intent(this, VolumeLimiterService.class);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
        
        // Save service state
        sharedPreferences.edit().putBoolean(KEY_SERVICE_ENABLED, true).apply();
        
        Toast.makeText(this, "Volume protection started", Toast.LENGTH_SHORT).show();
        updateServiceStatus();
    }
    
    private void stopVolumeLimiterService() {
        Intent serviceIntent = new Intent(this, VolumeLimiterService.class);
        stopService(serviceIntent);
        
        // Save service state
        sharedPreferences.edit().putBoolean(KEY_SERVICE_ENABLED, false).apply();
        
        Toast.makeText(this, "Volume protection stopped", Toast.LENGTH_SHORT).show();
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
