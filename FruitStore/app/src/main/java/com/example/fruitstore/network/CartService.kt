package com.example.fruitstore.network

import com.example.fruitstore.entity.Cart
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CartService {

    // 添加商品到购物车
    @POST("cart/add")
    suspend fun addToCart(
        @Query("userId") userId: Int,
        @Query("goodId") goodId: Int,
        @Query("quantity") quantity: Int,
        @Query("price") price: Float
    ): Boolean

    // 从购物车中删除商品
    @POST("cart/delete")
    suspend fun removeFromCart(
        @Query("userId") userId: Int,
        @Query("goodId") goodId: Int
    ): Boolean

    // 获取某个用户购物车中的商品
    @GET("cart/get")
    suspend fun getCart(
        @Query("userId") userId: Int
    ): List<Cart>

}
