package com.example.qrstockmateapp.api.models

data class Warehouse(

    val id:Int,
    val name:String,
    val location:String,
    val organization:String,
    val idAdministrator:Int,
    val idItems:String,
    val url:String
)