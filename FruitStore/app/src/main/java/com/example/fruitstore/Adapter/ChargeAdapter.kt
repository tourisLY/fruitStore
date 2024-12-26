package com.example.fruitstore.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.activity.ActivityCharge
//import com.star.myapplication.R

class ChargeAdapter(private val chargeAmounts: List<Int>) : RecyclerView.Adapter<ChargeAdapter.ChargeViewHolder>() {

    private var selectedPosition = -1  // 默认没有选中

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.charge_item, parent, false)
        return ChargeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChargeViewHolder, position: Int) {
        val amount = chargeAmounts[position]
        holder.bind(amount, position)
    }

    override fun getItemCount(): Int {
        return chargeAmounts.size
    }

    inner class ChargeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountTextView: TextView = itemView.findViewById(R.id.amount_money)
        private val bonusTextView: TextView = itemView.findViewById(R.id.extra_charge)

        fun bind(amount: Int, position: Int) {
            // 设置金额
            amountTextView.text = amount.toString()

            // 设置 "立赠" 的金额，假设它是金额的十分之一
            bonusTextView.text = "立赠 ${amount / 10}"

            // 设置点击事件
            itemView.setOnClickListener {
                selectedPosition = if (selectedPosition == position) {
                    -1 // 取消选中
                } else {
                    position // 设置当前为选中
                }

                // 通知 Activity 更新选中的金额
                (itemView.context as ActivityCharge).setSelectedAmount(amount)

                notifyDataSetChanged() // 更新所有 item 以显示选中状态
            }

            // 根据选中状态设置背景
            if (position == selectedPosition) {
                itemView.setBackgroundResource(R.drawable.item_selected_background) // 选中时的绿色边框
            } else {
                itemView.setBackgroundResource(R.drawable.item_normal_background) // 默认背景
            }
        }
    }
}
