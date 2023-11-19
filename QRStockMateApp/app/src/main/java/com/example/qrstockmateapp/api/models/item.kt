package com.example.qrstockmateapp.api.models

data class Item (

    val id:Int,
    val name:String,
    val warehouseId:Int,
    val location:String,
    val stock:Int,
    val url:String

)