package com.example.ProyectoFinalApp30Firebase

import android.app.Application
import androidx.room.Room
import com.example.ProyectoFinalApp30Firebase.data.ProyectoFinalApp30FirebaseCommentsDatabase
import com.example.ProyectoFinalApp30Firebase.data.ProyectoFinalApp30FirebaseConductorDatabase
import com.example.ProyectoFinalApp30Firebase.data.ProyectoFinalApp30FirebasePassengerDatabase


class ProyectoFinalApp30FirebaseApp : Application() {

    companion object{
        lateinit var databaseConductor: ProyectoFinalApp30FirebaseConductorDatabase
        lateinit var databasePassenger: ProyectoFinalApp30FirebasePassengerDatabase
        lateinit var databaseComments: ProyectoFinalApp30FirebaseCommentsDatabase

    }

    override fun onCreate() {
        super.onCreate()

        databaseConductor = Room.databaseBuilder(this, ProyectoFinalApp30FirebaseConductorDatabase::class.java, "ProyectoFinalApp30Firebase_conductor_db").allowMainThreadQueries().build()
        databasePassenger = Room.databaseBuilder(this, ProyectoFinalApp30FirebasePassengerDatabase::class.java, "ProyectoFinalApp30Firebase_passenger_db").allowMainThreadQueries().build()
        databaseComments = Room.databaseBuilder(this, ProyectoFinalApp30FirebaseCommentsDatabase::class.java, "ProyectoFinalApp30Firebase_conductor_comments_db").allowMainThreadQueries().build()
    }

}