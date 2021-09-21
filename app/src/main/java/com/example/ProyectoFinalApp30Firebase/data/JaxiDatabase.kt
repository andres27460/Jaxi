package com.example.ProyectoFinalApp30Firebase.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ProyectoFinalApp30Firebase.data.dao.ProyectoFinalApp30FirebaseCommentsDao
import com.example.ProyectoFinalApp30Firebase.data.dao.ProyectoFinalApp30FirebaseConductorDao
import com.example.ProyectoFinalApp30Firebase.data.dao.ProyectoFinalApp30FirebasePassengerDao

import com.example.ProyectoFinalApp30Firebase.data.entities.ProyectoFinalApp30FirebaseCommentsConductor
import com.example.ProyectoFinalApp30Firebase.data.entities.ProyectoFinalApp30FirebaseRegisterConductor
import com.example.ProyectoFinalApp30Firebase.data.entities.ProyectoFinalApp30FirebaseRegisterPassenger


@Database(entities = [ProyectoFinalApp30FirebaseRegisterConductor::class], version = 1)
abstract class ProyectoFinalApp30FirebaseConductorDatabase: RoomDatabase() {

        abstract  fun ProyectoFinalApp30FirebaseConductorDao(): ProyectoFinalApp30FirebaseConductorDao
}

@Database(entities = [ProyectoFinalApp30FirebaseRegisterPassenger::class], version = 1)
abstract class ProyectoFinalApp30FirebasePassengerDatabase: RoomDatabase() {

    abstract  fun ProyectoFinalApp30FirebasePassengerDao(): ProyectoFinalApp30FirebasePassengerDao
}

@Database(entities = [ProyectoFinalApp30FirebaseCommentsConductor::class], version = 1)
abstract class ProyectoFinalApp30FirebaseCommentsDatabase: RoomDatabase() {

    abstract  fun ProyectoFinalApp30FirebaseComentsConductorDao(): ProyectoFinalApp30FirebaseCommentsDao
}

