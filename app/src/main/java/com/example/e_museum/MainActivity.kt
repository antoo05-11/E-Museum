package com.example.e_museum

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.e_museum.activities.MuseumChoosingActivity
import com.example.e_museum.databinding.ActivityFindingMuseumBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private val url =
        "jdbc:mysql://b8fu1r5tflhnrnqjztht-mysql.services.clever-cloud.com:3306/b8fu1r5tflhnrnqjztht"
    private val username = "unxmdvotjktefgp8"
    private val password = "4XxtC2Ky5Dzz2AEEoC60"

    private lateinit var viewBinding: ActivityFindingMuseumBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFindingMuseumBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Thread {
            sqlConnection = SQLConnection.getSqlConnection()
            sqlConnection.connectServer(url, username, password)
            if (!sqlConnection.isReconnecting) {
                val intent = Intent(this, MuseumChoosingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }.start()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.i("longitude", longitude.toString())
                    Log.i("latitude", latitude.toString())
                }
            }
    }

    companion object {
        lateinit var sqlConnection: SQLConnection
    }
}
