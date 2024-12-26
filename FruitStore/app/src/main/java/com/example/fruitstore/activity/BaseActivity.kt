package com.example.fruitstore.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitstore.R
import com.example.fruitstore.databinding.ActivityBaseBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity:AppCompatActivity() {
    private lateinit var binding:ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initBottomNavigation()
    }

//    private fun initBottomNavigation(){                             //导航栏跳转
//        val bottomNav: BottomNavigationView = binding.bottomNavigation
//        bottomNav.setOnNavigationItemSelectedListener{ menuItem ->
//            when(menuItem.itemId){
//                R.id.nav_home->{
//                    startActivity(Intent(this, ActivityHome::class.java))
//                    true
//                }
//                R.id.nav_cart->{
//                    startActivity(Intent(this, ActivityCart::class.java))
//                    true
//                }
//                R.id.nav_order->{
//                    startActivity(Intent(this, ActivityOrder::class.java))
//                    true
//                }
//                R.id.nav_profile->{
//                    startActivity(Intent(this, ActivityProfile::class.java))
//                    true
//                }
//
//                else -> false
//            }
//        }
//    }

    private fun setActivityContent(layoutResId:Int){
        val container = binding.container

        layoutInflater.inflate(layoutResId, container, true)
    }
}