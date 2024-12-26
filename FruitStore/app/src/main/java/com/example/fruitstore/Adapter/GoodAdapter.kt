package com.example.fruitstore.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.activity.ActivityGood
import com.example.fruitstore.entity.Good
import com.example.fruitstore.fragment.CartFragment

class GoodAdapter(
    val GoodList: List<Good>,
    private val cartFragment: CartFragment // 传递 CartFragment 用于管理购物车
) : RecyclerView.Adapter<GoodAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodImage: ImageView = view.findViewById(R.id.good_right_img)
        val goodName: TextView = view.findViewById(R.id.good_right_name)
        val goodMsg: TextView = view.findViewById(R.id.good_right_msg)
        val goodRealPrice: TextView = view.findViewById(R.id.list_good_right_in_cart_real_price)
        val goodEarlyPrice: TextView = view.findViewById(R.id.list_good_right_in_cart_early_price)
        val addToCartButton: Button = view.findViewById(R.id.list_good_right_add)  // 添加按钮
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_good_right_in_cart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = GoodList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listGood = GoodList[position]
        val context = holder.itemView.context

        // 设置商品名称、描述和价格
        holder.goodName.text = listGood.goodName
        holder.goodMsg.text = listGood.goodMsg
        holder.goodRealPrice.text = "¥${listGood.goodPrice}"
        holder.goodEarlyPrice.text = "原价: ¥${listGood.goodPrice}"

        // 这里你可以处理商品图片（假设你有URL或者资源ID）
         holder.goodImage.setImageResource(context.resources.getIdentifier(listGood.goodImage, "drawable",context.packageName))  // 设置图片资源

        // 设置点击事件
        holder.addToCartButton.setOnClickListener {
            cartFragment.addToCart(listGood)
            Log.d("cateada","$listGood" )
        }
        holder.itemView.setOnClickListener {
            // 创建 Intent 跳转到商品详情页面
            val intent = Intent(holder.itemView.context, ActivityGood::class.java)

            // 将商品信息传递到商品详情页
            intent.putExtra("goodId", listGood.goodId)
            intent.putExtra("goodName", listGood.goodName)
            intent.putExtra("goodMsg", listGood.goodMsg)
            intent.putExtra("goodPrice", listGood.goodPrice)
            intent.putExtra("goodClassifyId", listGood.goodClassifyId)
            intent.putExtra("goodHeat", listGood.goodHeat)
            intent.putExtra("goodSugar", listGood.goodSugar)
            intent.putExtra("goodImage", listGood.goodImage)

            // 启动商品详情页
            holder.itemView.context.startActivity(intent)
        }
    }
}