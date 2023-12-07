package com.example.e_museum.view_controller.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.e_museum.data_fetching.models.Model
import com.example.e_museum.databinding.ActivityFindingMuseumBinding
import com.example.e_museum.entities.Museum
import com.example.e_museum.view_controller.fragments.fragments_dialog.MuseumGPSConfirmDialogFragment
import com.example.e_museum.data_fetching.SQLConnection
import com.example.e_museum.utils.getDistance
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityFindingMuseumBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onRestart() {
        super.onRestart()
        Thread { findWithGPS() }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityFindingMuseumBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.searchManualButton.setOnClickListener {
            val intent = Intent(this, MuseumChoosingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        Thread {
            modelController = Model.getInstance()
            sqlConnection = modelController.sqlConnection
            if (!modelController.sqlConnection.isReconnecting) {
                findWithGPS()
            }
        }.start()
    }

    fun findWithGPS() {
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

                    Thread {
                        SystemClock.sleep(2000)

                        var nearestMuseum = Museum()
                        var minDistance = Float.MAX_VALUE

                        val museumList = Model.getInstance().museumModel.allMuseums ?: return@Thread
                        museumList.forEach {
                            val posLongitude = it.pos_longitude
                            val posLatitude = it.pos_latitude
                            val tempDistance =
                                getDistance(
                                    Point(posLatitude.toInt(), posLongitude.toInt()),
                                    Point(longitude.toInt(), latitude.toInt())
                                )
                            if (minDistance > tempDistance) {
                                minDistance = tempDistance
                                nearestMuseum = it
                            }
                        }

                        if (nearestMuseum.museumID > 0) {
                            runOnUiThread {
                                if (!supportFragmentManager.isStateSaved) {
                                    val dialogFragment: DialogFragment =
                                        MuseumGPSConfirmDialogFragment(this, nearestMuseum)
                                    dialogFragment.show(supportFragmentManager, "confirm museum")
                                }
                            }
                        }
                    }.start()
                }
            }
    }

    companion object {
        const val fileServerURL = "https://muzik-files-server.000webhostapp.com/emuseum/"
        //const val fileServerURL = "http://10.0.2.2:5500/emuseum/"
        //const val fileServerURL = "http://192.168.33.103:5500/emuseum/"
        lateinit var sqlConnection: SQLConnection
        lateinit var modelController: Model
    }
}
