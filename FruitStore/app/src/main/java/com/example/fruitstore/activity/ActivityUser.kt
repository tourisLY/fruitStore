package com.example.fruitstore.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitstore.databinding.ActivityUserBinding
import com.example.fruitstore.entity.User
import com.example.fruitstore.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityUser:AppCompatActivity() {
    private lateinit var binding:ActivityUserBinding
    private val userRepository = UserRepository()
    private lateinit var users:List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initBt()
    }

    private fun initView(){
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                users = userRepository.getUserByAccount(intent.getStringExtra("userAccount")?:"wrong")
                for(user in users){
                    binding.userNameChange.text = user.userName
                    binding.userSexChange.text = user.userSex
                    binding.userPhoneChange.text = user.userPhoneNumber
                    binding.userHeadChange.setImageResource(resources.getIdentifier(user.userHead, "drawable", packageName))
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun initBt(){
        binding.btUserMsgChange.setOnClickListener{btUserMsgChange()}
        binding.btUserMsgBackNoChange.setOnClickListener{btUserMsgBackNoChange()}
    }

    private fun btUserMsgChange(){
        var userMsgChangeState:Boolean = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                for(user in users){
                    userMsgChangeState = userMsgChangeState && userRepository.updateUserName(user.userId, user.userName)
                    //TODO 这里还有别的更新，但是服务器端没写， 这里也暂时不写
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun btUserMsgBackNoChange(){
        val intent = Intent(this, ActivityMain::class.java)
        for(user in users){
            intent.putExtra("userAccount", user.userAccount)
            intent.putExtra("loginState", true)
        }
        finish()
    }
}