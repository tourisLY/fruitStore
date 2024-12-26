package com.example.fruitstore.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.fruitstore.databinding.ActivityGoodBinding
import com.example.fruitstore.entity.Good
import com.example.fruitstore.entity.User
import com.example.fruitstore.repository.GoodsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityGood : BaseActivity() {

    private lateinit var binding: ActivityGoodBinding

    // 接收商品信息的变量
    private lateinit var goodName: String
    private lateinit var goodMsg: String
    private var goodPrice: Float = 0f
    private lateinit var goodImage: String

    private val goodsRepository = GoodsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取传递的商品信息
        goodName = intent.getStringExtra("goodName") ?: "未知商品"
        goodMsg = intent.getStringExtra("goodMsg") ?: "暂无描述"
        goodPrice = intent.getFloatExtra("goodPrice", 0f)
        goodImage = intent.getStringExtra("goodImage") ?: ""

        // 设置商品信息到视图
        val goodNameTextView: TextView = binding.goodNameTextView
        val goodMsgTextView: TextView = binding.goodMsgTextView
        val goodPriceTextView: TextView = binding.goodPriceTextView
        val goodImageView: ImageView = binding.goodImageView

        goodNameTextView.text = goodName
        goodMsgTextView.text = goodMsg
        goodPriceTextView.text = "¥$goodPrice"
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val goods:List<Good> = goodsRepository.getGoodsByGoodName(goodName)
                Toast.makeText(baseContext, "${goods.size}", Toast.LENGTH_SHORT).show()
                for(good in goods){
                    goodImageView.setImageResource(resources.getIdentifier(good.goodImage, "drawable",packageName))
                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        // TODO: 设置商品图片，暂时以资源图片为例
        // goodImageView.setImageResource(R.drawable.some_image)
        // 如果你有 URL，可以使用 Glide 或 Picasso 加载图片
        // Glide.with(this).load(goodImage).into(goodImageView)
    }
}