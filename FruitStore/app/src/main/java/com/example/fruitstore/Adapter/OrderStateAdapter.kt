package com.example.fruitstore.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.Adapter.ListGoodLeftAdapter.ViewHolder
import com.example.fruitstore.R
import com.example.fruitstore.utils.OrderStateListener

class OrderStateAdapter(val OrderStateList:List<String>,
    val listener: OrderStateListener) :RecyclerView.Adapter<OrderStateAdapter.ViewHolder>(){
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val OrderStateName: TextView = view.findViewById(R.id.order_list_state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_order_state, parent, false)

        val viewHolder = ViewHolder(view)
//        viewHolder.itemView.setOnClickListener{
//            val position = viewHolder.adapterPosition
//            val state = OrderStateList[position]
//            Toast.makeText(parent.context, "$position  +++ $state", Toast.LENGTH_SHORT).show()
//            listener.onItemClicked(state.toString())
//
//        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderState = OrderStateList[position]
        holder.OrderStateName.text = orderState.toString()
    }


    override fun getItemCount() = OrderStateList.size
}