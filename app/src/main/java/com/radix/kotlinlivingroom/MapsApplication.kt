package com.radix.kotlinlivingroom

import android.app.Application
import android.util.Log
import com.appboy.Appboy
import com.appboy.configuration.AppboyConfig
import com.appboy.support.AppboyLogger
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class SinkApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppboyLogger.setLogLevel(Log.VERBOSE)
        val config = AppboyConfig.Builder()
            .setApiKey("1d502a81-f92f-48d4-96a7-1cbafc42b425")
            .setCustomEndpoint("sondheim.appboy.com")
            .setIsFirebaseCloudMessagingRegistrationEnabled(true)
            .setFirebaseCloudMessagingSenderIdKey("901477453852")
            .build()
        Appboy.configure(this, config)
//        registerActivityLifecycleCallbacks(AppboyLifecycleCallbackListener())

        var mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
    }
}
