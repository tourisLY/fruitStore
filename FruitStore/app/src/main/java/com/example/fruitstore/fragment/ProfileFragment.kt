package com.example.fruitstore.fragment

import android.accounts.Account
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.fruitstore.R
import com.example.fruitstore.activity.ActivityCharge
import com.example.fruitstore.activity.ActivityLocation
import com.example.fruitstore.activity.ActivityLogin
import com.example.fruitstore.activity.ActivityUser
import com.example.fruitstore.activity.Activity_store
import com.example.fruitstore.databinding.FragmentProfileBinding
import com.example.fruitstore.entity.Order
import com.example.fruitstore.entity.User
import com.example.fruitstore.repository.OrderRepository
import com.example.fruitstore.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding:FragmentProfileBinding ?= null
    private val binding get() = _binding!!
    private lateinit var myIntent:Intent
    private lateinit var userAccount: String

    private var listener: OnTextUpdatedListener? = null

    val userRepository = UserRepository()
    val orderRepository = OrderRepository()
    private lateinit var user: User

    interface OnTextUpdatedListener {
        fun onTextUpdated(newText: Int)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProfileBinding.bind(view)
        initButton()
        initView()
    }

    private fun initButton(){
        binding.balanceLayout.setOnClickListener{btBalance()}               //余额
        binding.orderLayout.setOnClickListener{btOrder()}                   //订单
        binding.rechargeLayout.setOnClickListener{btRecharge()}             //充值
        binding.ticketLayout.setOnClickListener{btTicket()}                 //卡券
        binding.addressLayout.setOnClickListener{btAddress()}               //地址
        binding.usLayout.setOnClickListener{btUs()}                         //我们
        binding.settingLayout.setOnClickListener{btSetting()}               //设置
        binding.exitLogin.setOnClickListener{btExit()}                      //退出登录
        binding.userMsgLayout.setOnClickListener{btUser()}                  //用户信息
    }

    private fun btBalance(){

    }
    private fun btRecharge(){
        // 创建 Intent 来启动 FragmentChargeActivity
        val intent = Intent(requireContext(), ActivityCharge::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val users:List<User> = userRepository.getUserByAccount(userAccount)
                for(user in users){
                    intent.putExtra("userId", user.userId)
                }

                // 你可以通过 putExtra 传递一些数据
                intent.putExtra("user_account", userAccount)
                startActivity(intent)
            }catch (e:Exception ){
                e.printStackTrace()
            }
        }



        // 启动 Activity

    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            try {

                val users:List<User> = userRepository.getUserByAccount(userAccount)
                if(users.isEmpty()){
//                    Toast.makeText(context, "用户信息获取失败",Toast.LENGTH_SHORT).show()
                }else{
                    for(user in users){
//                        binding.userName.text = user.userName
                        binding.balanceNum.text = user.userBalance.toString()
//                        binding.userImg.setImageResource(resources.getIdentifier(user.userHead, "drawable", requireContext().packageName))
//                        Log.d("头像","${resources.getIdentifier(user.userHead, "drawable", requireContext().packageName)}++++${user.userHead}")
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(context, "用户信息获取失败",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btOrder(){
        listener?.onTextUpdated(3)
    }
    private fun btTicket(){

    }
    private fun btAddress(){
        val intent = Intent(context, Activity_store::class.java)
        startActivity(intent)
    }
    private fun btUs(){
        Toast.makeText(context, "women", Toast.LENGTH_SHORT).show()
    }
    private fun btSetting(){
    }

    private fun btExit(){
        val exitIntent = Intent(context, ActivityLogin::class.java)
        startActivity(exitIntent)
    }

    private fun btUser(){
        val userIntent = Intent(context, ActivityUser::class.java)
        if(myIntent.getBooleanExtra("loginState", false)){
            userIntent.putExtra("userAccount", myIntent.getStringExtra("userAccount")?:"wrong")
            startActivity(userIntent)
        }else{
            Toast.makeText(context, "您还未登录！", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView(){
        if(myIntent.getBooleanExtra("loginState", false)){
            CoroutineScope(Dispatchers.Main).launch {
                try {

                    val users:List<User> = userRepository.getUserByAccount(userAccount)
                    if(users.isEmpty()){
                        Toast.makeText(context, "用户信息获取失败",Toast.LENGTH_SHORT).show()
                    }else{
                        for(user in users){
                            binding.userName.text = user.userName
                            binding.balanceNum.text = user.userBalance.toString()
                            binding.userImg.setImageResource(resources.getIdentifier(user.userHead, "drawable", requireContext().packageName))
                            Log.d("头像","${resources.getIdentifier(user.userHead, "drawable", requireContext().packageName)}++++${user.userHead}")

                            val Orders:List<Order> = orderRepository.getAllOrders(user.userId)
                            binding.orderNum.text = Orders.size.toString()
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    Toast.makeText(context, "用户信息获取失败",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            binding.exitLogin.text = "登录"
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val inActivity = activity
        if(inActivity != null){
            myIntent = inActivity.intent
            userAccount = myIntent.getStringExtra("userAccount")?:"wrong"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}