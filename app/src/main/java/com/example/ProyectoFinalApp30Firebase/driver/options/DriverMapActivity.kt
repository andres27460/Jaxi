package com.example.ProyectoFinalApp30Firebase.driver.options

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.ProyectoFinalApp30Firebase.*
import com.example.ProyectoFinalApp30Firebase.driver.DriverActivity

import com.example.ProyectoFinalApp30Firebase.user.options.tflag

import com.example.buttonnavigation.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DecimalFormat
import kotlin.math.roundToInt

var travelFlag = true
var dFlag = true

class DriverMapActivity : AppCompatActivity(), OnMapReadyCallback {


    lateinit var userMap: GoogleMap
    val locationAddress = GeoCodingLocation()
    private var mapReady = false
    private var apiKey = "AIzaSyBuI2ax9ZEL08s6pl-bwmNj50z2xnkvTfs"
    private lateinit var myLocationLatitude: String
    private lateinit var myLocationLongitude: String
    private lateinit var destinyLocationLatitude: String
    private lateinit var destinyLocationLongitude: String

    private lateinit var tRealLatitude: String
    private lateinit var tRealLongitude: String
    var database = FirebaseDatabase.getInstance().reference

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    var dist = 0.0
    var cash = 0.0

    companion object{
        const val REQUEST_CODE_LOCATION = 0
        private class GeoCoderHandler(private val driverapActivity: DriverMapActivity) : Handler() {
            override fun handleMessage(message: Message) {
                val locationAddress: String?
                locationAddress = when (message.what) {
                    1 -> {
                        val bundle = message.data
                        bundle.getString("address")
                    }
                    else -> null
                }


            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_map_fragment)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val map = supportFragmentManager.findFragmentById(R.id.userMap1) as SupportMapFragment
        map.getMapAsync {
                googleMap -> userMap = googleMap
            mapReady = true
            driverActiveFlag = "true"
            passengerActiveFlag = "false"
            getLastLocation()

            onMapReady(userMap)

        }


        findViewById<Button>(R.id.driverButton).setOnClickListener(){
            val i = Intent(this, DriverActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
       // Thread.sleep(1_000)


        findViewById<Button>(R.id.buttonUpdate).setOnClickListener() {
            if(travelFlag){
                    isSearchingPassenger()
            }
            else{
                database = FirebaseDatabase.getInstance().getReference("Conductors/${currentMail}")
                Toast.makeText(this,"Viaje finalizado, vuelve a quedar disponible", Toast.LENGTH_SHORT).show()
                findViewById<Button>(R.id.buttonUpdate).setText("Actualizar")

                database.child("service").child("availableService").setValue(0)
                database.child("active").child("activeValue").setValue(1)
                database.child("service").child("serviceLatitudeIn").setValue(0)
                database.child("service").child("serviceLongitudeIn").setValue(0)
                database.child("service").child("serviceLatitudeOut").setValue(0)
                database.child("service").child("serviceLongitudeOut").setValue(0)
                Thread.sleep(4_000)

                travelFlag = true
                dFlag = true

        }
        }






    }





    @SuppressLint("MissingPermission")
    fun getLastLocation(){

                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location: Location? = task.result
                    if(location == null){
                    }else{



                        tRealLongitude = location.longitude.toString()
                        tRealLatitude = location.latitude.toString()
                        database.child("Conductors").child(currentMail).child("active").child("latitudeValue").setValue(tRealLatitude)
                        database.child("Conductors").child(currentMail).child("active").child("longitudeValue").setValue(tRealLongitude)
                      }
                }

    }




    private fun setLocation(lat: String, long: String) {
        val coordinates = LatLng(lat.toDouble(),long.toDouble())
        val marker = MarkerOptions().position(coordinates)



        userMap.addMarker(   MarkerOptions()
            .position(coordinates)
            .title("Inicio"))


        userMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),4000,null
        )
    }


    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if(!::userMap.isInitialized) return
        if(isLocationPermissionGranted()){
            userMap.isMyLocationEnabled = true



        }
        else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                userMap.isMyLocationEnabled = true
            }else{
                Toast.makeText(this,"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else ->{ }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        userMap = googleMap



    }

    private fun setDestiny(lat: String, long: String) {
        val coordinates = LatLng(lat.toDouble(),long.toDouble())
        val marker = MarkerOptions().position(coordinates)



        userMap.addMarker(   MarkerOptions()
            .position(coordinates)
            .title("Destino"))


        userMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),4000,null
        )
    }

    private fun isSearchingPassenger() {
        database = FirebaseDatabase.getInstance().getReference("Conductors/${currentMail}")
        var getdataA = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(!driverActiveFlag.isNullOrEmpty()){
                if(tflag && dFlag){
                if(dFlag) {
                    for (i in p0.children) {

                        var inLatitude = i.child("serviceLatitudeIn").getValue().toString()
                        var inLongitude = i.child("serviceLongitudeIn").getValue().toString()
                        var outLatitude = i.child("serviceLatitudeOut").getValue().toString()
                        var outLongitude = i.child("serviceLongitudeOut").getValue().toString()

                        var llave = i.key.toString()
                        if (inLongitude != "null")
                            if (inLatitude.toDouble() != 0.0 && inLongitude.toDouble() != 0.0 && outLatitude.toDouble() != 0.0 && outLongitude.toDouble() != 0.0) {
                                    dFlag = false
                                var cash = CalculationByDistance(
                                    LatLng(
                                        inLatitude.toDouble(),
                                        inLongitude.toDouble()
                                    ), LatLng(outLatitude.toDouble(), outLongitude.toDouble())
                                )
                                cash *= (110 / 78)
                                Toast.makeText(
                                    baseContext,
                                    "Acaban de solicitar servicio",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Toast.makeText(
                                    baseContext,
                                    "El valor a cobrar es: ${(3600 + cash).roundToInt()}",
                                    Toast.LENGTH_LONG
                                ).show()

                                findViewById<Button>(R.id.buttonUpdate).setText("Finalizar viaje")
                                travelFlag = false
                                Toast.makeText(
                                    baseContext,
                                    "Su viaje se encuentra marcado en el mapa.",
                                    Toast.LENGTH_LONG
                                ).show()
                                database.child("active")
                                    .child("activeValue").setValue(0)
                                setLocation(inLatitude, inLongitude)
                                setDestiny(outLatitude, outLongitude)



                            }


                    }
                }
            }
                }
            }
        }
        database.addValueEventListener(getdataA)


    }



    fun CalculationByDistance(StartP: LatLng, EndP: LatLng): Double {
        val Radius = 6371 // radius of earth in Km
        val lat1 = StartP.latitude
        val lat2 = EndP.latitude
        val lon1 = StartP.longitude
        val lon2 = EndP.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + (Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2)))
        val c = 2 * Math.asin(Math.sqrt(a))
        val valueResult = Radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
        Log.i(
            "Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec
        )
        return Radius * c
    }







}