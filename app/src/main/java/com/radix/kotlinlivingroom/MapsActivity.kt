package com.radix.kotlinlivingroom

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.appboy.Appboy
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<Button>(R.id.logEventButton).setOnClickListener {
            performAction(
                LOG_CUSTOM_EVENT
            )
        }
        findViewById<Button>(R.id.logAttributeButton).setOnClickListener {
            performAction(
                LOG_ATTRIBUTE
            )
        }
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

    private fun performAction(action: String?) {
        when (action) {
            LOG_CUSTOM_EVENT -> {
                Appboy.getInstance(this).logCustomEvent("Logged Event")
            }
            LOG_ATTRIBUTE -> {
                Appboy.getInstance(this).currentUser?.setCustomUserAttribute("Cool Factor", 15.0)
            }
        }
    }

    companion object {
        val LOG_CUSTOM_EVENT = "customEvent"
        val LOG_ATTRIBUTE = "customAttribute"
    }

}
