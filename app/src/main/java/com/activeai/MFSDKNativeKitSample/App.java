package com.activeai.MFSDKNativeKitSample;

import android.app.Application;

/**
 * App is the Application Class
 * @author  Active.Ai
 * @version 1.0
 * @since   2020-01-09
 */
public class App extends Application {

    public static final String TAG = "MFSDK_APP_LOG";

    private boolean initialized;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initializing  SDK instance
        if (!initialized) {
            MyMFSDKMessagingManager.createInstance(this);
            initialized = true;
        }
    }
}
