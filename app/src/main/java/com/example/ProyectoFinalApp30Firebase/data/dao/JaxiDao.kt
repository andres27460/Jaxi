package com.example.ProyectoFinalApp30Firebase.data.dao

import androidx.room.*
import com.example.ProyectoFinalApp30Firebase.data.entities.ProyectoFinalApp30FirebaseCommentsConductor
import com.example.ProyectoFinalApp30Firebase.data.entities.ProyectoFinalApp30FirebaseRegisterConductor
import com.example.ProyectoFinalApp30Firebase.data.entities.ProyectoFinalApp30FirebaseRegisterPassenger

@Dao
interface ProyectoFinalApp30FirebaseConductorDao {

    @Insert
    fun createConductorUser(ProyectoFinalApp30Firebase: ProyectoFinalApp30FirebaseRegisterConductor)

    @Query("SELECT * FROM table_register_conductor_ProyectoFinalApp30Firebase WHERE userName LIKE :name")
    fun readConductorUser(name: String) : ProyectoFinalApp30FirebaseRegisterConductor
}

@Dao
interface ProyectoFinalApp30FirebasePassengerDao {
    @Insert
    fun createPassengerUser(ProyectoFinalApp30Firebase: ProyectoFinalApp30FirebaseRegisterPassenger)

    @Query("SELECT * FROM table_register_passenger_ProyectoFinalApp30Firebase WHERE userName LIKE :name")
    fun readPassengerUser(name: String) : ProyectoFinalApp30FirebaseRegisterPassenger
}

@Dao
interface ProyectoFinalApp30FirebaseCommentsDao {

    @Insert
    fun createConductorComment(ProyectoFinalApp30Firebase: ProyectoFinalApp30FirebaseCommentsConductor)

    @Query("SELECT * FROM table_comments_conductor_ProyectoFinalApp30Firebase WHERE userName LIKE :name")
    fun readCommentsConductorUser(name: String) : ProyectoFinalApp30FirebaseCommentsConductor
}