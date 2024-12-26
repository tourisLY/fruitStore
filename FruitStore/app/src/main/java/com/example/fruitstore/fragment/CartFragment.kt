package com.example.fruitstore.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.fruitstore.Adapter.ImageSliderAdapter
import com.example.fruitstore.Adapter.ListGoodLeftAdapter
import com.example.fruitstore.Adapter.ListGoodRightAdapter
import com.example.fruitstore.R
import com.example.fruitstore.activity.ActivityCart
import com.example.fruitstore.databinding.FragmentCartBinding
import com.example.fruitstore.entity.Cart
import com.example.fruitstore.entity.Good
import com.example.fruitstore.entity.GoodLeft
import com.example.fruitstore.entity.StickyHeaderDecoration
import com.example.fruitstore.entity.User
import com.example.fruitstore.repository.CartRepository
import com.example.fruitstore.repository.GoodLeftRepository
import com.example.fruitstore.repository.GoodsRepository
import com.example.fruitstore.repository.OrderRepository
import com.example.fruitstore.repository.UserRepository
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var goodLeftAdapter: ListGoodLeftAdapter
    private lateinit var goodRightAdapter: ListGoodRightAdapter
    private val goodLeftRepository = GoodLeftRepository()
    private val goodsRepository = GoodsRepository()
    private val userRepository = UserRepository()
    private val orderRepository = OrderRepository()

    private val cartItems = mutableListOf<Cart>()
    private var goodLeftList = mutableListOf<GoodLeft>()
    private var goodRightList = mutableListOf<Good>()
    private val cartRepository = CartRepository()


    private lateinit var myIntent: Intent
    private lateinit var userAccount: String
    private var loginState:Boolean= true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        binding.cartIcon.setOnClickListener {
            // 跳转到 ActivityCart 页面
            navigateToActivityCart()
        }
        initList()
        loadCartData()
        initBt()
        setupAutoScroll()
        // 给 RecyclerView 添加 Sticky Header Decoration，并传入 goodsRepository
        binding.listGoodRight.addItemDecoration(
            StickyHeaderDecoration(
                requireContext(),
                binding.listGoodRight,
                goodLeftList,
                goodsRepository
            )
        )
    }

    private fun setupAutoScroll() {
        val images = listOf(
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5
        )
        val adapter = ImageSliderAdapter(images)
        binding.cartImageSlide.adapter = adapter
        binding.cartImageSlide.postDelayed(object : Runnable {
            override fun run() {
                val nextItem = (binding.cartImageSlide.currentItem + 1) % adapter.itemCount
                binding.cartImageSlide.setCurrentItem(nextItem, true)
                binding.cartImageSlide.postDelayed(this, 3000) // 每3秒切换一次
            }
        }, 3000) // 初始延迟
    }

    private fun initBt(){
            binding.btCartJieSuan.setOnClickListener{btJieSuan()}
    }

    private fun btJieSuan():Boolean{
//        Toast.makeText(context, "11", Toast.LENGTH_SHORT).show()
        var userId:Int = -1
        var orderId = -1
        var orderData = mutableListOf<Cart>()
        var goodData = mutableListOf<Good>()
//        Toast.makeText(context, "${binding.cartMoneyNum.text.toString().toFloatOrNull()?:0.0f}", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val users:List<User> = userRepository.getUserByAccount(userAccount)
//                Toast.makeText(context, "${users.size}", Toast.LENGTH_SHORT).show()
                for(user in users){
//                    if(binding.cartMoneyNum.text.toString().toFloatOrNull()?:0.0f > user.userBalance){
//                        Toast.makeText(context, "${binding.cartMoneyNum.text.toString().drop(1).toFloatOrNull()?:0.0f}", Toast.LENGTH_SHORT).show()
//                        return@launch
//                    }
                    userId = user.userId
                    if(user.userBalance < binding.cartMoneyNum.text.toString().drop(1).toFloatOrNull()?:0.0f){
                        Toast.makeText(context, "您的余额不足！", Toast.LENGTH_SHORT).show()
                        orderId = orderRepository.insertOrder(userId, binding.cartMoneyNum.text.toString().drop(1).toFloatOrNull()?:0.0f,
                            0.0f, "未付款", "备注", "一家小店" )
                    }else{
                        orderId = orderRepository.insertOrder(userId, binding.cartMoneyNum.text.toString().drop(1).toFloatOrNull()?:0.0f,
                            0.0f, "进行中", "备注", "一家小店" )
                        val num:Float =binding.cartMoneyNum.text.toString().drop(1).toFloatOrNull()?:0.0f
                        userRepository.updateUserBalance(user.userId, user.userBalance - num)
                    }
//                    val num:Float =binding.cartMoneyNum.text.toString().drop(1).toFloatOrNull()?:0.0f
//                    userRepository.updateUserBalance(user.userId, user.userBalance - num)
//                    Toast.makeText(context, "11", Toast.LENGTH_SHORT).show()

//                    Toast.makeText(context, "${binding.cartMoneyNum.text.toString().toFloat()}", Toast.LENGTH_SHORT).show()
//                    Toast.makeText(context, "12", Toast.LENGTH_SHORT).show()
                    orderData.addAll(cartRepository.getCart(userId))
                    for(data in orderData){
                        goodData.clear()
                        goodData.addAll(goodsRepository.getGoodsById(data.goodId))
//                        Toast.makeText(context, "13", Toast.LENGTH_SHORT).show()
                        for(good in goodData){
                            orderRepository.insertOrderItems(orderId, data.goodId, data.price, data.count, data.price*data.count,
                            good.goodName, good.goodImage)
                        }
//                        Toast.makeText(context, "14", Toast.LENGTH_SHORT).show()
                        cartRepository.removeFromCart(userId, data.goodId)
                        cartItems.clear()
                        Log.d("test", "11")
                        updateCartUI()

                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val inActivity = activity
        if(inActivity != null){
            myIntent = inActivity.intent
            userAccount = myIntent.getStringExtra("userAccount")?:"wrong"
            loginState = myIntent.getBooleanExtra("loginState", false)
        }

    }

    private fun initList() {
        binding.listGoodTitle.layoutManager = LinearLayoutManager(context)
        binding.listGoodRight.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 获取左侧分类列表
                goodLeftList.addAll(goodLeftRepository.getAllGoodLeft())
                goodLeftAdapter = ListGoodLeftAdapter(goodLeftList) { selectedCategory ->
                    onCategorySelected(selectedCategory)
                }
                binding.listGoodTitle.adapter = goodLeftAdapter

                // 初始化右侧商品列表
                goodRightAdapter = ListGoodRightAdapter(goodLeftList, this@CartFragment)  // 传递当前 Fragment 实例

                binding.listGoodRight.adapter = goodRightAdapter

                goodLeftAdapter.notifyDataSetChanged()
                goodRightAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "获取分类信息失败！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onCategorySelected(selectedCategory: GoodLeft) {
        Log.d("CartFragment", "Selected category ID: ${selectedCategory.goodClassifyId}")

        scrollToCategory(selectedCategory.goodClassifyId)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 获取当前分类下的商品
                val goods = goodsRepository.getGoodsById(selectedCategory.goodClassifyId)
                Log.d("CartFragment", "获取到的商品列表大小: ${goods.size}")

                // 如果获取到商品，更新右侧列表
                if (goods.isNotEmpty()) {
                    goodRightList.clear()
                    goodRightList.addAll(goods)
                    goodRightAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "该分类下没有商品", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "获取商品失败！", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // 在 Fragment 中实现滚动逻辑
    private fun scrollToCategory(categoryId: Int) {
        // 获取 RecyclerView
        val recyclerView = binding.listGoodRight

        // 查找该位置对应的 ViewHolder
        val position = goodLeftList.indexOfFirst { it.goodClassifyId == categoryId }

        if (position != -1) {
            // 使用 RecyclerView 的 scrollToPosition 滚动到对应位置
            recyclerView.scrollToPosition(position)

            // 如果需要更精确的滚动到目标位置，可以使用 findViewHolderForAdapterPosition
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)

            if (viewHolder is ListGoodRightAdapter.ViewHolder) {
                // 获取该 ViewHolder 中的 innerRecyclerView
                val layoutManager = viewHolder.innerRecyclerView.layoutManager as LinearLayoutManager
                layoutManager.scrollToPositionWithOffset(position, 0)
            }
        } else {
            Log.d("CartFragment", "未找到对应的分类: $categoryId")
        }
    }
    fun addToCart(good: Good) {
        // 检查购物车中是否已经有该商品
        val existingItem = cartItems.find { it.goodId == good.goodId }

        if (existingItem != null) {
            // 如果商品已存在，增加数量
            existingItem.count += 1

            // 更新后端的购物车数据
            updateCartItem(existingItem)
            updateCartUI()
        } else {
            // 如果商品不存在，添加新商品
            var userId:Int = -1
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val users:List<User> = userRepository.getUserByAccount(userAccount)
                    for(user in users){
                        userId = user.userId
                    }
                    val newCartItem = Cart(userId, goodId = good.goodId, count = 1, price = good.goodPrice)
                    cartItems.add(newCartItem)

                    // 添加到后端购物车
                    addNewCartItem(newCartItem)
                    updateCartUI()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

        // 更新购物车UI

    }

    // 更新购物车
    private fun updateCartItem(cartItem: Cart) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val success = cartRepository.addToCart(cartItem.userId, cartItem.goodId, cartItem.count, cartItem.price)
                if (success) {
                    Log.d("CartFragment", "购物车商品数量更新成功")
                } else {
                    Toast.makeText(context, "更新购物车失败", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "更新购物车失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 添加商品到购物车
    private fun addNewCartItem(cartItem: Cart) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val success = cartRepository.addToCart(cartItem.userId, cartItem.goodId, cartItem.count, cartItem.price)
                if (success) {
                    Log.d("CartFragment", "新增商品成功")
                } else {
                    Toast.makeText(context, "添加商品失败", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "添加商品失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCartUI() {
        var totalCount = 0
        var totalPrice = 0f

        cartItems.forEach {
            totalCount += it.count
            totalPrice += it.count * it.price
        }

        // 更新界面上的数量和总价
        binding.cartItemCount.text = totalCount.toString()
        binding.cartMoneyNum.text = "¥${totalPrice}"
    }
    // 从后端加载购物车数据
    // 从后端获取购物车数据
    private fun loadCartData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val users:List<User> = userRepository.getUserByAccount(userAccount)
                // 获取当前用户的购物车数据
                for (user in users){
//                    Toast.makeText(context, "${user.userId}！", Toast.LENGTH_SHORT).show()
                    val cartData = cartRepository.getCart(user.userId)  // 假设 userId 为 1
//                    Log.d("test", "${cartItems}")
                    cartItems.clear()
                    cartItems.addAll(cartData)
                }


                // 清空现有购物车数据，并添加从后端获取的数据


                // 计算总数量和总金额
                updateCartUI()  // 更新购物车界面上的总数量和总金额
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "加载购物车数据失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToActivityCart() {
        // 创建 Intent 跳转到 ActivityCart
        val intent = Intent(requireContext(), ActivityCart::class.java)

        // 如果需要传递数据，可以通过 putExtra() 方法传递
        intent.putExtra("key", "value")  // 示例传递数据
        intent.putExtra("userAccount", userAccount)
        // 启动 Activity
        startActivity(intent)
    }

    // 清理绑定
    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}