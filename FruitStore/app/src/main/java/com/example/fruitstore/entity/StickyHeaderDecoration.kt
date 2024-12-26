package com.example.fruitstore.entity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.R
import com.example.fruitstore.repository.GoodsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StickyHeaderDecoration(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val goodLeftList: List<GoodLeft>,
    private val goodsRepository: GoodsRepository
) : RecyclerView.ItemDecoration() {

    private var stickyHeaderView: View? = null
    private var headerHeight: Int = 0

    // 通过回调获取商品数量
    private fun getItemCountForCategory(category: GoodLeft, callback: (Int) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 异步获取商品列表
                val goods = goodsRepository.getGoodsById(category.goodClassifyId)
                callback(goods.size)  // 返回商品数量
            } catch (e: Exception) {
                e.printStackTrace()
                callback(0)  // 如果出现异常，返回 0
            }
        }
    }

    // 绘制粘性头部
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // 获取第一个完全可见的 item
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        // 如果第一个可见的 item 是有效的
        if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
            val category = goodLeftList[firstVisibleItemPosition]  // 获取当前类别

            // 判断是否已经创建过粘性头部视图
            if (stickyHeaderView == null) {
                stickyHeaderView = LayoutInflater.from(context).inflate(R.layout.list_good_right, parent, false)

                // 设置粘性头部的尺寸
                stickyHeaderView?.measure(
                    View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                stickyHeaderView?.layout(0, 0, stickyHeaderView?.measuredWidth ?: 0, stickyHeaderView?.measuredHeight ?: 0)

                val headerTextView = stickyHeaderView?.findViewById<TextView>(R.id.list_good_right_name)

                // 使用回调获取商品数量
                getItemCountForCategory(category) { itemCount ->
                    // 设置类别名和商品数量
                    headerTextView?.text = "${category.goodClassify} ($itemCount)"
                }

                stickyHeaderView?.bringToFront()

                // 获取头部高度
                headerHeight = stickyHeaderView?.measuredHeight ?: 0
            }

            // 继续更新类别名称和商品数量
            val headerTextView = stickyHeaderView?.findViewById<TextView>(R.id.list_good_right_name)
            getItemCountForCategory(category) { itemCount ->
                headerTextView?.text = "${category.goodClassify} ($itemCount)"
            }

            // 计算粘性头部的位置
            val topOffset = 0  // 粘性头部的顶部位置

            // 在正确的位置绘制粘性头部
            c.save()
            c.translate(0f, topOffset.toFloat())
            stickyHeaderView?.draw(c)
            c.restore()
        }
    }

    // 处理偏移量，防止头部遮挡
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        // 给第一个 item 预留空间，防止头部遮挡
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = headerHeight
        }
    }
}


