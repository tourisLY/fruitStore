package com.example.fruitstore.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.fruitstore.Adapter.CartAdapter
import com.example.fruitstore.Adapter.ImageSliderAdapter
import com.example.fruitstore.R
import com.example.fruitstore.entity.Cart
import com.example.fruitstore.entity.Good
import com.example.fruitstore.entity.User
import com.example.fruitstore.repository.CartRepository
import com.example.fruitstore.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActivityCart : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var imageAdapter: ImageSliderAdapter
    private var cartItems: List<Cart> = emptyList()  // 使用空列表初始化
    private var goodsList: List<Good> = emptyList()  // 使用空列表初始化

    private val cartRepository = CartRepository()
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.good_cart)

        var userId = 1  // 假设是用户ID

        // 异步加载数据
        lifecycleScope.launch(Dispatchers.Main) {
            // 获取购物车数据
            val users:List<User> = userRepository.getUserByAccount(intent.getStringExtra("userAccount")?:"1")
            for(user in users){
                userId   = user.userId
            }
            Log.d("user", "${userId}")
            cartItems = cartRepository.getCart(userId)

            // 获取所有商品数据
            goodsList = cartRepository.getAllGoods()  // 获取所有商品

            // 初始化 RecyclerView
            recyclerView = findViewById(R.id.list_good_in_cart)
            recyclerView.layoutManager = LinearLayoutManager(this@ActivityCart)

            // 设置适配器
            cartAdapter = CartAdapter(
                context = this@ActivityCart,
                cartItems = cartItems,
                goodsList = goodsList,
                onDeleteClickListener = { cartItem ->
                    removeItemFromCart(cartItem)
                    cartAdapter.notifyDataSetChanged()  // 通知适配器数据变化
                }
            )
            recyclerView.adapter = cartAdapter

            // 更新适配器的数据源并刷新
            cartAdapter.cartItems = cartItems
            cartAdapter.goodsList = goodsList
            cartAdapter.notifyDataSetChanged()  // 确保RecyclerView刷新
        }
    }

    private fun initViewPager2(){
        val images = listOf(
            R.drawable.rounded_image,
            R.drawable.menu,
            R.drawable.profile_img
        )
        imageAdapter = ImageSliderAdapter(images)
        val cartSlide: ViewPager2 = findViewById(R.id.cart_image_slide)
        cartSlide.adapter = imageAdapter

        // 配置 TabLayout 和 ViewPager2 进行联动，去掉 tab.text
//        TabLayoutMediator(binding.tabLayout, binding.cartImageSlide) { _, _ ->
//            // 不设置tab的文字，这样就只会显示小点作为指示器
//        }.attach()

        cartSlide.postDelayed(object : Runnable {
            override fun run() {
                val nextItem = (cartSlide.currentItem + 1) % imageAdapter.itemCount
                cartSlide.setCurrentItem(nextItem, true)
                cartSlide.postDelayed(this, 3000) // 每3秒切换一次
            }
        }, 3000) // 初始延迟

    }

    // 删除购物车中的商品
    private fun removeItemFromCart(cartItem: Cart) {
        cartItems = cartItems.filterNot { it == cartItem }
        cartAdapter.notifyDataSetChanged()  // 删除后刷新RecyclerView
    }
}

