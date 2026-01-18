# Quick Installation Guide - Volume Limiter App

## For Your Android 10 Device (ColorOS 7.2, 3GB RAM)

### Method 1: Install Using Android Studio (Best Way)

#### Step 1: Download Android Studio
1. Go to: https://developer.android.com/studio
2. Download Android Studio for your computer (Windows/Mac/Linux)
3. Install it (takes about 10-15 minutes)

#### Step 2: Enable Developer Mode on Your Phone
1. Open **Settings** on your phone
2. Go to **About Phone**
3. Find **Build Number**
4. Tap it **7 times** rapidly
5. You'll see "You are now a developer!"

#### Step 3: Enable USB Debugging
1. Go back to **Settings**
2. Open **Developer Options** (should be visible now)
3. Turn on **USB Debugging**
4. Confirm the popup

#### Step 4: Connect Phone to Computer
1. Connect your phone using USB cable
2. On your phone, you'll see "Allow USB Debugging?" popup
3. Tap **Allow** (check "Always allow" box)

#### Step 5: Open Project in Android Studio
1. Open Android Studio
2. Click **"Open an Existing Project"**
3. Navigate to the **VolumeLimiter** folder
4. Click **OK**
5. Wait for Android Studio to load (2-3 minutes first time)

#### Step 6: Install App on Your Phone
1. At the top, click the green **Play button** (▶)
2. Select your phone from the device list
3. Click **OK**
4. Wait 1-2 minutes for the app to build and install
5. The app will automatically open on your phone!

---

### Method 2: Install APK File (Easier but requires building first)

#### Step 1: Build APK in Android Studio
1. Follow Method 1 Steps 1-5 first
2. In Android Studio, go to **Build** menu
3. Click **Build Bundle(s) / APK(s)** → **Build APK(s)**
4. Wait for build to complete (you'll see a notification)
5. Click **"locate"** in the notification
6. You'll find the APK file (usually in `app/build/outputs/apk/debug/`)

#### Step 2: Transfer APK to Your Phone
**Option A: USB Cable**
1. Connect phone to computer
2. Copy the APK file to your phone's Downloads folder

**Option B: Email/Cloud**
1. Email the APK file to yourself
2. Download it on your phone

#### Step 3: Install APK on Your Phone
1. On your phone, go to **File Manager**
2. Navigate to **Downloads** folder
3. Tap the APK file
4. If you see "Install blocked":
   - Go to Settings → Security
   - Enable "Install from Unknown Sources" or "Install Unknown Apps"
   - Allow for File Manager or browser
5. Tap **Install**
6. Tap **Open** when done

---

## After Installation - First Use

### Step 1: Grant Permissions
1. Open the **Volume Limiter** app
2. If asked for permissions, tap **Allow**

### Step 2: Set Your Volume Limit
1. You'll see a slider (default at 50%)
2. Move the slider to your desired maximum volume
   - **Example**: Set to 40% if you want volume max at 40%
3. The percentage shows in big numbers

### Step 3: Start Protection
1. Tap the green **"START PROTECTION"** button
2. You'll see a notification appear
3. Status will show: **"Service Active - Volume Protected"** ✅
4. Green dot indicator appears

### Step 4: Test It
1. Open **YouTube** or any music app
2. Try to increase volume to maximum
3. Volume will automatically stop at your set limit!
4. You can lower volume normally

---

## Project Folder Structure Setup

If you're setting up in Android Studio manually, create this structure:

```
VolumeLimiter/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── volumelimiter/
│   │       │           └── app/
│   │       │               ├── MainActivity.java
│   │       │               └── VolumeLimiterService.java
│   │       │
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   └── values/
│   │       │       └── strings.xml
│   │       │
│   │       └── AndroidManifest.xml
│   │
│   └── build.gradle
│
├── build.gradle (root)
└── settings.gradle
```

**File Placement:**
- `MainActivity.java` → Place in: `app/src/main/java/com/volumelimiter/app/`
- `VolumeLimiterService.java` → Place in: `app/src/main/java/com/volumelimiter/app/`
- `activity_main.xml` → Place in: `app/src/main/res/layout/`
- `AndroidManifest.xml` → Place in: `app/src/main/`
- `strings.xml` → Place in: `app/src/main/res/values/`

---

## Daily Usage

### To Use the App:
1. **Set limit**: Open app → Move slider
2. **Start**: Tap "START PROTECTION"
3. **Close app**: The service keeps running in background
4. **Check status**: Pull down notification bar to see it's active

### To Change Limit:
1. Open the app
2. Move slider to new percentage
3. New limit applies immediately (service keeps running)

### To Stop Protection:
1. Open the app
2. Tap "STOP PROTECTION" button
3. Volume restriction removed

---

## Important Tips

### ⚠️ After Phone Restart
The service **does NOT start automatically** after restart. You need to:
1. Open the app
2. Tap "START PROTECTION" again

### ⚠️ Prevent Service from Stopping
Your phone might kill the app to save battery. To prevent this:
1. Go to **Settings** → **Apps**
2. Find **Volume Limiter**
3. Tap **Battery**
4. Select **"Don't optimize"** or **"No restrictions"**

### ⚠️ If Volume Still Goes Up
1. Open the Volume Limiter app
2. Check if green "Service Active" is showing
3. If not, tap "START PROTECTION" again
4. Check battery optimization is disabled

---

## Common Issues & Solutions

| Problem | Solution |
|---------|----------|
| **Can't install APK** | Enable "Install from Unknown Sources" in Settings → Security |
| **Service stops by itself** | Disable battery optimization for the app |
| **App not in device list** | Check USB Debugging is enabled, try different USB cable |
| **Build fails** | Make sure you have internet connection for first build |
| **Volume still goes up** | Restart the service by tapping "START PROTECTION" again |

---

## Need Help?

1. ✅ Check if Android version is 10
2. ✅ Verify USB Debugging is enabled
3. ✅ Try different USB cable if phone not detected
4. ✅ Disable battery optimization for the app
5. ✅ Make sure all permissions are granted

---

**Estimated Time:**
- **Method 1**: 20-30 minutes (includes Android Studio download/setup)
- **Method 2**: 5-10 minutes (if APK is already built)

**Once installed**: App starts in under 3 seconds! 🚀
