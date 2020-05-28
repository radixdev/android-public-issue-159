package com.radix.kotlinlivingroom

import android.os.Bundle
import android.os.Debug
import android.os.SystemClock
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.appboy.Appboy
import com.appboy.events.ContentCardsUpdatedEvent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import java.io.File
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Create our heap dump directory
        PARENT_DIR = File(cacheDir, "braze_heaps")
        PARENT_DIR.mkdirs()

        findViewById<Button>(R.id.bLogEvent).setOnClickListener(this)
        findViewById<Button>(R.id.bLogAttribute).setOnClickListener(this)
        findViewById<Button>(R.id.bGetInstance).setOnClickListener(this)
        findViewById<Button>(R.id.bChangeUser).setOnClickListener(this)
        findViewById<Button>(R.id.bOpenSession).setOnClickListener(this)
        findViewById<Button>(R.id.bGetContentCards).setOnClickListener(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val nyc = LatLng(40.801921, -73.961172)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nyc, 13f))

        // Add our marker points
        mMap.addCircle(
            CircleOptions()
                .center(nyc)
                .radius(500.0)
        )
    }

    override fun onClick(v: View?) {
        if (v !is Button) {
            return
        }
        val action = v.text

        Debug.dumpHprofData(File(PARENT_DIR, "${action}_baseline.hprof").path)

        when (v.id) {
            R.id.bLogEvent -> {
                Appboy.getInstance(this).logCustomEvent("Logged Event")
            }
            R.id.bLogAttribute -> {
                Appboy.getInstance(this).currentUser?.setCustomUserAttribute("Cool Factor", 15.0)
            }
            R.id.bGetInstance -> {
                Appboy.getInstance(this)
            }
            R.id.bChangeUser -> {
                Appboy.getInstance(this).changeUser(UUID.randomUUID().toString())
            }
            R.id.bOpenSession -> {
                Appboy.getInstance(this).openSession(this)
            }
            R.id.bGetContentCards -> {
                Appboy.getInstance(this).subscribeToContentCardsUpdates { contentCardsUpdatedEvent: ContentCardsUpdatedEvent? ->

                }
                Appboy.getInstance(this).requestContentCardsRefresh(false)
            }
        }

        // Wait for things to settle and collect a post heap dump
        Thread(Runnable {
            SystemClock.sleep(5000)
            Debug.dumpHprofData(File(PARENT_DIR, "${action}_post.hprof").path)
        }).start()
    }

    companion object {
        lateinit var PARENT_DIR: File
    }

}
