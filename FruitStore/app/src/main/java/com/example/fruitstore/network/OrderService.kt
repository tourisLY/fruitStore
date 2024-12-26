package com.example.fruitstore.network

import com.example.fruitstore.entity.Order
import com.example.fruitstore.entity.OrderItem
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderService {
    @GET("order/byOrderState")
    suspend fun getOrdersByState(@Query("orderState")orderState:String, @Query("userId")userId: Int):List<Order>

    @GET("order/getAllOrderItems")
    suspend fun getAllOrderItems(@Query("orderId")orderId:Int):List<OrderItem>

    @GET("/order")
    suspend fun getAllOrders(@Query("userId")userId:Int):List<Order>

    @POST("/order/insertOrder")
    suspend fun insertOrder(@Query("userId")userId: Int,
                            @Query("orderAllPrice")orderAllPrice:Float,
                            @Query("discountPrice")discountPrice: Float,
                            @Query("orderState")orderState: String,
                            @Query("orderRemark")orderRemark:String,
                            @Query("shopName")shopName:String):Int

    @POST("/order/insertOrderItems")
    suspend fun insertOrderItems(@Query("orderId")orderId:Int,
                                 @Query("goodId")goodId:Int,
                                 @Query("goodPrice")goodPrice:Float,
                                 @Query("goodNum")goodNum:Int,
                                 @Query("goodSumPrice")goodSumPrice:Float,
                                 @Query("goodName")goodName:String,
                                 @Query("goodImage")goodImage:String):Boolean
}