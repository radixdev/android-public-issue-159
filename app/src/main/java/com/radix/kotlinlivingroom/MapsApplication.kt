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
            .setApiKey("da8f263e-1483-4e9f-ac0c-7b40030c8f40")
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
