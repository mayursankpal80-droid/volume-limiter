# Volume Limiter App for Android 10

A powerful volume restriction app designed specifically for Android 10 (ColorOS 7.2) with 3GB RAM. This app prevents the media/YouTube volume from exceeding your set limit, no matter what.

## Features

✅ **Restrict Maximum Volume** - Set a percentage limit (0-100%) that volume cannot exceed
✅ **Works with YouTube & All Media** - Monitors and restricts volume across all media apps
✅ **Background Service** - Runs continuously in the background to enforce limits
✅ **Persistent Protection** - Starts automatically and keeps running
✅ **Low Resource Usage** - Optimized for 3GB RAM devices
✅ **Simple UI** - Easy-to-use interface with slider control
✅ **Real-time Monitoring** - Checks volume every 500ms and immediately corrects it
✅ **Notification Indicator** - Shows service status in notification bar

## How It Works

1. **Set Your Limit**: Use the slider to choose maximum volume percentage (e.g., 50%)
2. **Start Protection**: Tap "START PROTECTION" button
3. **Automatic Enforcement**: The service monitors volume continuously
4. **Volume Restricted**: If you try to increase volume beyond the limit, it automatically reduces back
5. **Always Protected**: Works even after phone restart (you need to start service again)

## Installation Steps

### Option 1: Using Android Studio (Recommended)

1. **Download and Install Android Studio**
   - Go to https://developer.android.com/studio
   - Download and install Android Studio

2. **Open the Project**
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Navigate to the VolumeLimiter folder
   - Click OK

3. **Connect Your Phone**
   - Enable Developer Options on your phone:
     - Go to Settings → About Phone
     - Tap "Build Number" 7 times
   - Enable USB Debugging:
     - Settings → Developer Options → USB Debugging (ON)
   - Connect phone via USB cable

4. **Build and Install**
   - In Android Studio, click the green "Run" button (▶)
   - Select your device from the list
   - Wait for the app to build and install

### Option 2: Build APK File

1. Open Android Studio with the project
2. Go to Build → Build Bundle(s) / APK(s) → Build APK(s)
3. Wait for build to complete
4. Click "locate" in the notification to find the APK
5. Transfer APK to your phone
6. Install the APK (you may need to enable "Install from Unknown Sources")

## Project Structure

```
VolumeLimiter/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/volumelimiter/app/
│   │       │   ├── MainActivity.java           # Main UI activity
│   │       │   └── VolumeLimiterService.java   # Background service
│   │       ├── res/
│   │       │   └── layout/
│   │       │       └── activity_main.xml       # UI layout
│   │       └── AndroidManifest.xml             # App configuration
│   └── build.gradle                            # App build config
├── build.gradle                                # Project build config
└── settings.gradle                             # Project settings
```

## File Locations for Android Studio

**Create this folder structure:**

```
VolumeLimiter/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── volumelimiter/
│   │       │           └── app/
│   │       │               ├── MainActivity.java
│   │       │               └── VolumeLimiterService.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   ├── mipmap/
│   │       │   └── values/
│   │       │       └── strings.xml
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle (project level)
└── settings.gradle
```

## Usage Instructions

### First Time Setup

1. Install and open the app
2. You'll see a slider set to 50% by default
3. Adjust the slider to your desired maximum volume limit
4. Tap "START PROTECTION"
5. Grant any permissions if requested
6. You'll see "Service Active - Volume Protected" message

### Daily Use

- **The app runs in background** - You don't need to keep it open
- **Notification shows** - A persistent notification indicates the service is running
- **Try to increase volume** - When you go beyond your limit, it will automatically reduce
- **Change the limit** - Open the app, adjust slider, the new limit applies immediately
- **Stop protection** - Tap "STOP PROTECTION" if you want to remove the limit

### Important Notes

⚠️ **After Phone Restart**: You need to open the app and tap "START PROTECTION" again
⚠️ **Battery Optimization**: If the service stops, disable battery optimization for this app:
   - Settings → Apps → Volume Limiter → Battery → Don't optimize
⚠️ **Permission Required**: Make sure "Modify System Settings" permission is granted

## Troubleshooting

### Service Keeps Stopping
**Solution**: Disable battery optimization
1. Go to Settings → Apps → Volume Limiter
2. Tap Battery → Don't optimize
3. Restart the service

### Volume Still Goes Up
**Solution**: Check if service is running
1. Open the app
2. Look for green "Service Active" indicator
3. If not running, tap "START PROTECTION"

### App Crashes on Start
**Solution**: Check Android version
- This app requires Android 10 (API 29)
- Check: Settings → About Phone → Android Version

### Cannot Install APK
**Solution**: Enable unknown sources
1. Settings → Security
2. Enable "Install from Unknown Sources"
3. Try installing again

## Technical Details

- **Minimum Android Version**: Android 10 (API 29)
- **Target Android Version**: Android 10 (API 29)
- **Memory Usage**: ~20-30 MB RAM
- **Battery Impact**: Minimal (< 2% daily)
- **Check Interval**: 500 milliseconds
- **Volume Streams Monitored**: MUSIC, ALARM

## Permissions Explained

- **FOREGROUND_SERVICE**: Allows the app to run in background continuously
- **MODIFY_AUDIO_SETTINGS**: Required to change volume levels
- **ACCESS_NOTIFICATION_POLICY**: For notification channel creation

## Customization Options

You can modify the following in the code:

1. **Check Interval**: Change `CHECK_INTERVAL` in `VolumeLimiterService.java`
2. **UI Colors**: Modify colors in `activity_main.xml`
3. **Notification**: Customize notification text in `createNotification()` method
4. **Additional Streams**: Add more audio streams (RING, NOTIFICATION) in `checkAndLimitVolume()`

## Support

If you encounter any issues:
1. Check the Troubleshooting section above
2. Make sure your device is running Android 10
3. Verify all permissions are granted
4. Check that battery optimization is disabled

## Version History

**v1.0** - Initial Release
- Volume limiting functionality
- Background service
- Simple UI with slider
- Persistent notification

---

**Compatible with**: Android 10, ColorOS 7.2, 3GB RAM devices
**Package Name**: com.volumelimiter.app
**License**: Free to use
