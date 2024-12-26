package com.example.fruitstore.repository

import com.example.fruitstore.entity.Order
import com.example.fruitstore.entity.OrderItem
import com.example.fruitstore.instance.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query

class OrderRepository {
    suspend fun getAllOrders(userId:Int):List<Order>
    {
        return  withContext(Dispatchers.IO){
            RetrofitInstance.orderapi.getAllOrders(userId)
        }
    }

    suspend fun getOrderByState(orderState:String, userId: Int):List<Order>
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.orderapi.getOrdersByState(orderState, userId)
        }
    }

    suspend fun getAllOrderItems(orderId:Int):List<OrderItem>
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.orderapi.getAllOrderItems(orderId)
        }
    }

    suspend fun insertOrder(userId: Int,
                            orderAllPrice:Float,
                            discountPrice:Float,
                            orderState: String,
                            orderRemark:String,
                            shopName:String):Int
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.orderapi.insertOrder(userId, orderAllPrice, discountPrice, orderState, orderRemark, shopName)
        }
    }

    suspend fun insertOrderItems(orderId:Int,
                                 goodId:Int,
                                 goodPrice:Float,
                                 goodNum:Int,
                                 goodSumPrice:Float,
                                 goodName:String,
                                 goodImage:String):Boolean
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.orderapi.insertOrderItems(orderId, goodId, goodPrice, goodNum, goodSumPrice, goodName, goodImage)
        }
    }
}