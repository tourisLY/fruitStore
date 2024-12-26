package com.example.fruitstore.repository

import com.example.fruitstore.entity.Good
import com.example.fruitstore.instance.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoodsRepository {
    suspend fun getAllGoods():List<Good>{
        return withContext(Dispatchers.IO){
            RetrofitInstance.goodsapi.getAllGoods()
        }
    }

    suspend fun getGoodsById(classifyId:Int):List<Good>
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.goodsapi.getGoodByClassifyId(classifyId)
        }
    }

    suspend fun getGoodsByGoodName(goodName:String):List<Good>
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.goodsapi.getGoodByGoodName(goodName)
        }
    }
}