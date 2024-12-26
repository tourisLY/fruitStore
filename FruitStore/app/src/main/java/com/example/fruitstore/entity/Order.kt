package com.example.fruitstore.entity

import java.time.LocalDate

data class Order(val orderId:Int, val userId:Int, val orderDate: String, val orderAllPrice:Float,
    val discountPrice:Float, val orderState:String, val orderRemark:String, val createData:String,
    val finishData:String, val shopName:String)
