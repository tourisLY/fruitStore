package com.example.fruitstore.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.entity.Cart
import com.example.fruitstore.entity.Good
import com.example.fruitstore.repository.GoodsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartAdapter(
    private val context: Context,
    var cartItems: List<Cart>,  // 使用可变列表
    var goodsList: List<Good>,  // 使用可变列表
    private val onDeleteClickListener: (Cart) -> Unit  // 删除商品的回调
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val goodsRepository = GoodsRepository()

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goodImage: ImageView = itemView.findViewById(R.id.good_right_img)
        val goodName: TextView = itemView.findViewById(R.id.good_right_name)
        val goodDescription: TextView = itemView.findViewById(R.id.good_right_msg)
        val realPrice: TextView = itemView.findViewById(R.id.list_good_right_in_cart_real_price)
        val quantityText: TextView = itemView.findViewById(R.id.list_good_right_add)
       /* val deleteButton: ImageView = itemView.findViewById(R.id.delete_icon)*/

        /*init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener(cartItems[position])
                }
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_good_in_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        // 通过 goodId 查找对应的商品
        val good = goodsList.find { it.goodId == cartItem.goodId }

        good?.let {
            holder.goodName.text = it.goodName  // 商品名
            holder.goodDescription.text = it.goodMsg  // 商品描述
            holder.realPrice.text = "¥${cartItem.price}"  // 单价
            holder.quantityText.text = cartItem.count.toString()  // 商品数量

            // 加载商品图片（假设商品模型中有 goodImage 字段）
            // holder.goodImage.setImageResource(R.drawable.some_image)  // 设置图片资源
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val goods:List<Good> = goodsRepository.getGoodsById(cartItem.goodId)
                    for(good in goods){
                        holder.goodImage.setImageResource(context.resources.getIdentifier(good.goodImage, "drawable",context.packageName))
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}

