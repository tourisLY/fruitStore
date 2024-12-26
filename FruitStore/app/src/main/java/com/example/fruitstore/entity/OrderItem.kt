package com.example.fruitstore.entity

data class OrderItem(val orderItemId:Int, val orderId:Int, val goodId:Int, val goodPrice:Float,
    val goodNum:Int, val goodSumPrice:Float, val goodName:String, val goodImage:String)
