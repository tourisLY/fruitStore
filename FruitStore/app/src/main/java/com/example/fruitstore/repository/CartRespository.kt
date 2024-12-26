package com.example.fruitstore.repository

import android.util.Log
import com.example.fruitstore.entity.Cart
import com.example.fruitstore.entity.Good
import com.example.fruitstore.instance.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository {

    // 添加商品到购物车
    suspend fun addToCart(userId: Int, goodId: Int, quantity: Int, price: Float): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.cartapi.addToCart(userId, goodId, quantity, price)
                Log.d("CartRepository", "Response: $response")  // 打印后端返回的数据
                return@withContext response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CartRepository", "请求失败: ${e.message}")  // 打印异常
                return@withContext false
            }
        }
    }


    // 获取用户的购物车数据
    suspend fun getCart(userId: Int): List<Cart> {
        return withContext(Dispatchers.IO) {
            try {
                // 调用 Retrofit API 获取购物车数据
                val response = RetrofitInstance.cartapi.getCart(userId)
                Log.d("CartRepository", "Response: $response")  // 打印返回值
                response  // 返回购物车中的商品列表
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList<Cart>()  // 如果请求失败返回空列表
            }
        }
    }
    // 获取所有商品
    suspend fun getAllGoods(): List<Good> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.goodsapi.getAllGoods()  // 调用 getAllGoods()
                Log.d("CartRepository", "Response: $response")  // 打印返回值
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CartRepository", "请求失败: ${e.message}")
                emptyList()  // 如果请求失败返回空列表
            }
        }
    }

    // 根据分类ID获取商品
    suspend fun getGoodByClassifyId(classifyId: Int): List<Good> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.goodsapi.getGoodByClassifyId(classifyId)  // 调用 getGoodByClassifyId()
                Log.d("CartRepository", "Response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CartRepository", "请求失败: ${e.message}")
                emptyList()  // 如果请求失败返回空列表
            }
        }
    }

    suspend fun removeFromCart(userId: Int, goodId: Int)
    {
        return withContext(Dispatchers.IO){
            try {
                val response = RetrofitInstance.cartapi.removeFromCart(userId,goodId)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}
