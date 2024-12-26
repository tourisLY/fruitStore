package com.example.fruitstore.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.entity.Order
import com.example.fruitstore.entity.OrderItem

class OrderItemAdapter(val OrderItemList:List<OrderItem>) :RecyclerView.Adapter<OrderItemAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view)
    {
        val goodImage:ImageView = view.findViewById(R.id.list_order_item_good_img)
        val goodName:TextView = view.findViewById(R.id.list_order_item_good_name)
        val goodNum:TextView = view.findViewById(R.id.list_order_item_num)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = OrderItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = OrderItemList[position]
        val context = holder.itemView.context
        holder.goodName.text = orderItem.goodName
        holder.goodNum.text = orderItem.goodNum.toString()
        holder.goodImage.setImageResource(context.resources.getIdentifier(orderItem.goodImage, "drawable", context.packageName))
    }

}