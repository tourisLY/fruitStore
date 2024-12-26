package com.example.fruitstore.network

import com.example.fruitstore.entity.GoodLeft
import retrofit2.http.GET

interface ApiService {
    @GET("goodLeft")
    suspend fun findAllGoodLeft():List<GoodLeft>
}