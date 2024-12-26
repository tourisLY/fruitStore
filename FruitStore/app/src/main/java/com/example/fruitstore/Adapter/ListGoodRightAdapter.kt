package com.example.fruitstore.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.entity.Good
import com.example.fruitstore.entity.GoodLeft
import com.example.fruitstore.fragment.CartFragment
import com.example.fruitstore.instance.RetrofitInstance
import com.example.fruitstore.repository.GoodsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListGoodRightAdapter(
    private val goodLeftList: MutableList<GoodLeft>,
    private val cartFragment: CartFragment // 传递 CartFragment 用于管理购物车
) : RecyclerView.Adapter<ListGoodRightAdapter.ViewHolder>() {

    private val goodsRepository = GoodsRepository()
    private lateinit var goodAdapter: GoodAdapter

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodClassify: TextView = view.findViewById(R.id.list_good_right_name)
        val innerRecyclerView: RecyclerView = view.findViewById(R.id.good_right_list_msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_good_right, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = goodLeftList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = goodLeftList[position]
        holder.goodClassify.text = category.goodClassify  // 显示分类名称

        val innerGoodMsgList = mutableListOf<Good>()
        holder.innerRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.innerRecyclerView.isNestedScrollingEnabled = false

        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 获取当前分类下的商品
                innerGoodMsgList.addAll(goodsRepository.getGoodsById(category.goodClassifyId))

                // 设置右侧分类标题：显示分类名称和商品数量
                val itemCount = innerGoodMsgList.size
                holder.goodClassify.text = "${category.goodClassify} ($itemCount)"

                // 设置商品列表
                goodAdapter = GoodAdapter(innerGoodMsgList,cartFragment);
                holder.innerRecyclerView.adapter = goodAdapter
                goodAdapter.notifyDataSetChanged()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
