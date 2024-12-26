package com.example.fruitstore.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.entity.GoodLeft

class ListGoodLeftAdapter(
    private val goodLeftList: MutableList<GoodLeft>,
    private val onCategoryClickListener: (GoodLeft) -> Unit
) : RecyclerView.Adapter<ListGoodLeftAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val goodClassify: TextView = view.findViewById(R.id.list_good_title_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_good_title, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category  = goodLeftList[position]
        holder.goodClassify.text = category.goodClassify

        // 确保点击事件被触发
        holder.itemView.setOnClickListener {
            onCategoryClickListener(category)  // 调用回调方法
        }
    }

    override fun getItemCount() = goodLeftList.size
}