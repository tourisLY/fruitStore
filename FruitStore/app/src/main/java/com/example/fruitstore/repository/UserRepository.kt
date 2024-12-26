package com.example.fruitstore.repository

import com.example.fruitstore.entity.User
import com.example.fruitstore.instance.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    suspend fun loginByAccount(userAccount:String, userPassword:String):Boolean
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.userapi.loginByAccount(userAccount,userPassword)
        }
    }

    suspend fun registerUserByAccount(userAccount: String, userPassword: String):Boolean
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.userapi.registerUserByAccount(userAccount, userPassword)
        }
    }

    suspend fun getUserByAccount(userAccount:String):List<User>
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.userapi.getUserByAccount(userAccount)
        }
    }

    suspend fun getUserByUserId(userUserId:Int):List<User>
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.userapi.getUserByUserId(userUserId)
        }
    }

    suspend fun updateUserName(userId:Int, userName:String):Boolean
    {
        return withContext(Dispatchers.IO){
            RetrofitInstance.userapi.updateUserName(userId, userName)
        }
    }

    suspend fun updateUserBalance(userId: Int, newBalance: Float): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // 调用后端的接口来更新余额
                val response = RetrofitInstance.userapi.updateUserBalance(userId, newBalance)
                response // 假设接口返回的是成功与否的布尔值
            } catch (e: Exception) {
                e.printStackTrace()
                false // 如果请求失败，返回 false
            }
        }
    }
}