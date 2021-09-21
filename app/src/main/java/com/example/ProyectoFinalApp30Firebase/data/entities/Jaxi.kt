package com.example.ProyectoFinalApp30Firebase.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable



@Entity(tableName = "table_register_conductor_ProyectoFinalApp30Firebase")
data class ProyectoFinalApp30FirebaseRegisterConductor (

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "userMail")       val userMail: String,
    @ColumnInfo(name = "userName")       val userName: String,
    @ColumnInfo(name = "userPass")       val userPass: String,
    @ColumnInfo(name = "UserCar")        val userCar: String,
    @ColumnInfo(name = "UserCarPlate")   val UserCarPlate: String,
    @ColumnInfo(name = "userCC")         val CC: Long

) : Serializable

@Entity(tableName = "table_register_passenger_ProyectoFinalApp30Firebase")
data class ProyectoFinalApp30FirebaseRegisterPassenger (

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "userMail")  val userMail: String,
    @ColumnInfo(name = "userName")  val userName: String,
    @ColumnInfo(name = "userPass")  val userPass: String,
    @ColumnInfo(name = "UserCC")    val CC: Long

) : Serializable

@Entity(tableName = "table_comments_conductor_ProyectoFinalApp30Firebase")
data class ProyectoFinalApp30FirebaseCommentsConductor (

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "userName")  val userName: String,
    @ColumnInfo(name = "comments")  val comments: String,

) : Serializable
