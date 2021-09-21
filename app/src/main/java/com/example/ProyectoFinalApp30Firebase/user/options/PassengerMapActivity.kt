package com.example.ProyectoFinalApp30Firebase.user.options

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.ProyectoFinalApp30Firebase.user.PassengerActivity
import com.example.buttonnavigation.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


import android.util.Log
import com.example.ProyectoFinalApp30Firebase.*
import com.example.ProyectoFinalApp30Firebase.driver.options.dFlag
import com.google.android.libraries.places.api.Places
import com.google.firebase.database.*
import java.text.DecimalFormat
import kotlin.math.roundToInt

var tflag = true


class PassengerMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var userMap: GoogleMap
    var flag = true
    var tflagg = false
    val locationAddress = GeoCodingLocation()
    private var mapReady = false
    private var apiKey = "AIzaSyBuI2ax9ZEL08s6pl-bwmNj50z2xnkvTfs"
    private lateinit var myLocationLatitude: String
    private lateinit var myLocationLongitude: String
    private lateinit var destinyLocationLatitude: String
    private lateinit var destinyLocationLongitude: String
    private lateinit var database : DatabaseReference

    var dist = 0.0
    var cash = 0.0

    companion object{
        const val REQUEST_CODE_LOCATION = 0
        private class GeoCoderHandler(private val passengerMapActivity: PassengerMapActivity) : Handler() {
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
        setContentView(R.layout.passenger_map_fragment)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        val map = supportFragmentManager.findFragmentById(R.id.userMap) as SupportMapFragment
        map.getMapAsync {
                googleMap -> userMap = googleMap
            mapReady = true
            driverActiveFlag = "false"
            passengerActiveFlag = "true"

            enableLocation()
        }




        findViewById<Button>(R.id.button15).setOnClickListener(){
            val i = Intent(this, PassengerActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
        findViewById<Button>(R.id.search_button).setOnClickListener() {
            tflag = true
            if(flag) {
                userMap.clear()
                val myLocation = findViewById<EditText>(R.id.search_text).getText().toString()
                val destinyLocation = findViewById<EditText>(R.id.search_text2).getText().toString()

                locationAddress.getAddressFromLocation(
                    myLocation, applicationContext,
                    GeoCoderHandler(this)
                )
                Thread.sleep(2_000)

                myLocationLatitude = latitud
                myLocationLongitude = longitud
                val loc = LatLng(myLocationLatitude.toDouble(), myLocationLongitude.toDouble())

                setLocation()
                locationAddress.getAddressFromLocation(
                    destinyLocation, applicationContext,
                    GeoCoderHandler(this)
                )
                Thread.sleep(2_000)

                destinyLocationLatitude = latitud
                destinyLocationLongitude = longitud

                val dest =
                    LatLng(destinyLocationLatitude.toDouble(), destinyLocationLongitude.toDouble())


                setDestiny()

                dist = CalculationByDistance(loc, dest) * 1000
                cash = (dist * (110 / 78))

                flag = false
                findViewById<Button>(R.id.search_button).setText("Viajar")
                //confirmTravel(cash)

            }
            else {
                flag = true
                confirmTravel(cash)

            }





        }
    }

    private fun confirmTravel(cash: Double) {

        val alertDialog: AlertDialog? = let{
            val builder = AlertDialog.Builder(it)
            builder.apply{
                setTitle("Confirmar Viaje")
                setMessage("El destino actual tiene un valor de: ${(3600 + cash).roundToInt()} pesos, ¿desea realizarlo?")
                setPositiveButton("Si"){ dialog, id ->
                    tflagg = true
                  isSearchingConductor()

                }
                setNegativeButton("No"){ dialog, id ->
                    findViewById<Button>(R.id.search_button).setText("Buscar")
                    findViewById<EditText>(R.id.search_text).setText("")
                    findViewById<EditText>(R.id.search_text2).setText("")

                }
            }
            builder.create()
        }
        alertDialog?.show()

    }

    private fun isSearchingConductor() {
        database = FirebaseDatabase.getInstance().getReference("Conductors")
        var getdataA = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(!passengerActiveFlag.isNullOrBlank()){
                if(tflag && dFlag){
                if(tflagg) {
                    for (i in p0.children) {
                        //var activeValue = i.child("active/activeValue").getValue().toString()
                        //var activeValues = i.child("active").getValue().toString()
                        var activeValue = i.child("active").child("activeValue").getValue().toString()
                        var tlatitude = i.child("active").child("latitudeValue").getValue().toString()
                        var tlongitude = i.child("active").child("longitudeValue").getValue().toString()
                        var llave  = i.key.toString()

                        if (activeValue == "1") {

                            database.child(llave).child("service").child("serviceLatitudeIn").setValue(myLocationLatitude)
                            database.child(llave).child("service").child("serviceLongitudeIn").setValue(myLocationLongitude)
                            database.child(llave).child("service").child("serviceLatitudeOut").setValue(destinyLocationLatitude)
                            database.child(llave).child("service").child("serviceLongitudeOut").setValue(destinyLocationLongitude)
                            database.child(llave).child("active").child("activeValue").setValue(0)
                            conductorLocation(tlatitude,tlongitude)
                            tflagg = false
                            tflag = false
                            Toast.makeText(baseContext, "El conductor ya se encuentra en camino, que disfrute su viaje.",Toast.LENGTH_LONG).show()

                        }


                    }
                }
            }
        }
        }
        }
        database.addValueEventListener(getdataA)


    }


    private fun setLocation() {
        val coordinates = LatLng(myLocationLatitude.toDouble(),myLocationLongitude.toDouble())
        val marker = MarkerOptions().position(coordinates)


        userMap.addMarker(marker.title("1"))


        userMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),4000,null
        )
    }


    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

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
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
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

    private fun setDestiny() {
        val coordinates = LatLng(destinyLocationLatitude.toDouble(),destinyLocationLongitude.toDouble())
        val marker = MarkerOptions().position(coordinates)
        userMap.addMarker(marker.title("2"))


        userMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),4000,null
        )
    }

    private fun conductorLocation(lat: String, long: String) {


        val coordinates = LatLng(lat.toDouble(),long.toDouble())
        val marker = MarkerOptions().position(coordinates)
        userMap.addMarker(marker.title("3"))


        userMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),4000,null
        )
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

