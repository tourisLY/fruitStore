package com.example.fruitstore.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.entity.Order
import com.example.fruitstore.entity.OrderItem
import com.example.fruitstore.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderAdapter(val OrderList:List<Order>):RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    val OrderRepository = OrderRepository()
    lateinit var orderItemAdapter: OrderItemAdapter

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val shopName:TextView = view.findViewById(R.id.order_shop_name)
        val orderState:TextView = view.findViewById(R.id.order_list_state)
        val orderItemRecyclerView:RecyclerView = view.findViewById(R.id.list_order_item)
        val orderAllPrice:TextView = view.findViewById(R.id.list_order_all_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = OrderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = OrderList[position]
        holder.shopName.text = order.shopName
        holder.orderState.text = order.orderState
        holder.orderAllPrice.text = order.orderAllPrice.toString()

        var orderItemList = mutableListOf<OrderItem>()
        holder.orderItemRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.orderItemRecyclerView.isNestedScrollingEnabled = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                orderItemList.addAll(OrderRepository.getAllOrderItems(order.orderId))
                orderItemAdapter = OrderItemAdapter(orderItemList)
                holder.orderItemRecyclerView.adapter = orderItemAdapter
                orderItemAdapter.notifyDataSetChanged()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


}