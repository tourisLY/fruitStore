package com.example.fruitstore.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitstore.databinding.ActivityLocationBinding
import com.example.fruitstore.databinding.ActivityStoreBinding

class Activity_store:AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dianJiTiaoZhuan.setOnClickListener{
            val intent = Intent(baseContext, ActivityLocation::class.java)
            startActivity(intent)
        }
    }
}