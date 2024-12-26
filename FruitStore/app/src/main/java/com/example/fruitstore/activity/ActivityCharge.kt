package com.example.fruitstore.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.Adapter.ChargeAdapter
import com.example.fruitstore.entity.User

import com.example.fruitstore.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityCharge : AppCompatActivity() {

    private lateinit var chargeAdapter: ChargeAdapter
    private lateinit var confirmButton: Button
    private val userRepository = UserRepository()

//    private var currentUserId: Int = intent.getIntExtra("userId", 0) // 假设当前用户的 ID，实际情况可以从登录信息中获取
    private var currentUserId: Int = 1 // 假设当前用户的 ID，实际情况可以从登录信息中获取
    private var currentBalance: Float = 1000f // 当前余额，实际情况可以从后台获取
    private var selectedAmount: Int = 0 // 当前选中的充值金额，初始化为0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recharge_layout)

        confirmButton = findViewById(R.id.confirmButton)
        val chargeAmounts = listOf(300, 400, 500, 600, 700, 1000, 10000)

        chargeAdapter = ChargeAdapter(chargeAmounts)

        val recyclerView = findViewById<RecyclerView>(R.id.charge_item)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chargeAdapter

        // 监听选中状态
        confirmButton.setOnClickListener {
//            if (selectedAmount > 0) {
//                // 更新余额
////                val users:List<User> = userRepository.getUserByUserId(currentUserId)
////                for(user in users){
//                    val newBalance:Float = selectedAmount.toFloat()  // 将当前余额与选中金额相加
//                    updateBalance(newBalance)
////                }
//                Toast.makeText(this, "充值成功，已增加 $selectedAmount 元", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "请选择充值金额", Toast.LENGTH_SHORT).show()
//            }
            currentUserId = intent.getIntExtra("userId", 0)
//            Toast.makeText(this, "$currentUserId", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.Main).launch {
                if (selectedAmount > 0) {
                    // 更新余额
                    val users:List<User> = userRepository.getUserByUserId(currentUserId)
                    for(user in users){
                        val newBalance = user.userBalance + selectedAmount  // 将当前余额与选中金额相加
                        updateBalance(newBalance)
                    }
                    Toast.makeText(baseContext, "充值成功，已增加 $selectedAmount 元", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "请选择充值金额", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 更新用户余额
    private fun updateBalance(newBalance: Float) {
        CoroutineScope(Dispatchers.Main).launch {
            // 调用后台接口更新用户余额
            val success = userRepository.updateUserBalance(currentUserId, newBalance)
            if (success) {
                currentBalance = newBalance
                // 更新 UI 或做其他处理
                Toast.makeText(this@ActivityCharge, "余额更新成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ActivityCharge, "余额更新失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 通过 ChargeAdapter 更新 selectedAmount
    fun setSelectedAmount(amount: Int) {
        selectedAmount = amount
        // 显示按钮
        confirmButton.visibility = Button.VISIBLE
    }
}
