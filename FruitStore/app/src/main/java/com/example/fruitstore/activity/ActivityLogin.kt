package com.example.fruitstore.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitstore.R
import com.example.fruitstore.databinding.ActivityLoginBinding
import com.example.fruitstore.entity.User
import com.example.fruitstore.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityLogin:AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private val userRepository = UserRepository()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test","im in?")
        initView()
        initButton()
    }

    private fun initView(){
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginImage.setImageResource(R.drawable.app)
    }

    private fun initButton(){
        binding.gotoLogin.setOnClickListener{bt_login()}
        binding.gotoRegister.setOnClickListener{bt_register()}
//        binding.changeLoginWay.setOnClickListener{bt_change_login_way()}
        binding.loginBackLoginStateFalse.setOnClickListener{btNoLogin()}
    }

    private fun btNoLogin(){
        val intent = Intent(this, ActivityMain::class.java)
        finish()
    }

    private fun bt_login(){
        val intent = Intent(this, ActivityMain::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if(userRepository.loginByAccount(binding.loginAccount.text.toString(),
                    binding.loginPassword.text.toString()))
                {
                    Toast.makeText(baseContext,"登录成功！", Toast.LENGTH_SHORT).show()
                    intent.putExtra("userAccount", binding.loginAccount.text.toString())
                    intent.putExtra("loginState",true)
                    val users:List<User> = userRepository.getUserByAccount(binding.loginAccount.text.toString())
                    if(users.isEmpty()){
                        Toast.makeText(baseContext, "用户信息获取失败",Toast.LENGTH_SHORT).show()
                    }else{
                        for (user in users){
                            intent.putExtra("userId", user.userId)
                        }
                    }
                    finish()
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(baseContext, "账号或密码错误", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(baseContext,"未知错误", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bt_register(){
        val intent = Intent(this, ActivityMain::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if(userRepository.registerUserByAccount(binding.loginAccount.text.toString(),
                    binding.loginPassword.text.toString())){
                    Toast.makeText(baseContext, "注册成功", Toast.LENGTH_SHORT).show()
                    intent.putExtra("userAccount", binding.loginAccount.text.toString())
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(baseContext, "注册失败，该账号可能已被注册", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun bt_change_login_way(){}
}
