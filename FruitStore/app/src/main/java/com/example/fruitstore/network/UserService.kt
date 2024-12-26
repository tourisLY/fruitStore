package com.example.fruitstore.network

import com.example.fruitstore.entity.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("user/loginByAccount")
    suspend fun  loginByAccount(@Query("userAccount")userAccount:String,@Query("userPassword")userPassword:String):Boolean

    @POST("user/registerUserByAccount")
    suspend fun registerUserByAccount(@Query("userAccount")userAccount: String, @Query("userPassword")userPassword: String):Boolean

    @GET("user/getByAccount")
    suspend fun getUserByAccount(@Query("userAccount")userAccount: String):List<User>

    @GET("user/getByUserId")
    suspend fun getUserByUserId(@Query("userId")userId: Int):List<User>

    @POST("user/updateUserName")
    suspend fun updateUserName(@Query("userId")userId:Int, @Query("userName")userName:String):Boolean

    @POST("user/updateUserBalance")
    suspend fun updateUserBalance(
        @Query("userId") userId: Int,
        @Query("userBalance") userBalance: Float
    ): Boolean
}