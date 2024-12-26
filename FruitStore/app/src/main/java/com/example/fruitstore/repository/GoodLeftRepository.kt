package com.example.fruitstore.repository

import android.util.Log
import com.example.fruitstore.entity.GoodLeft
import com.example.fruitstore.instance.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoodLeftRepository {

    suspend fun getAllGoodLeft():List<GoodLeft>{

//        return
        val goodLeftList = withContext(Dispatchers.IO){
            RetrofitInstance.api.findAllGoodLeft()
        }
        return goodLeftList
    }


}