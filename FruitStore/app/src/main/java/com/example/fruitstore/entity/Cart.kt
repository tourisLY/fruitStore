package com.example.fruitstore.entity
data class Cart(
    val userId: Int,       // 用户ID
    val goodId: Int,       // 商品ID
    var count: Int,        // 商品数量
    val price: Float         // 商品单价
)
