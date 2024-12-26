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
import com.example.fruitstore.Adapter.OrderAdapter
import com.example.fruitstore.Adapter.OrderStateAdapter
import com.example.fruitstore.R
import com.example.fruitstore.databinding.FragmentOrderBinding
import com.example.fruitstore.entity.Order
import com.example.fruitstore.repository.OrderRepository
import com.example.fruitstore.utils.OrderStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderFragment : Fragment(R.layout.fragment_order), OrderStateListener {
    private var _binding:FragmentOrderBinding ?= null
    private val binding get() = _binding!!

    private val orderStateList = ArrayList<String>()
    private val OrderRepository = OrderRepository()

    private var orderList = mutableListOf<Order>()
    private lateinit var myIntent: Intent
    private lateinit var userAccount: String
    private lateinit var orderAdapter: OrderAdapter
    private var loginState:Boolean= true



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding  = FragmentOrderBinding.bind(view)

        initOrderStateAdapter()
        initBt()
        binding.orderListBig.layoutManager = LinearLayoutManager(context)
//        Toast.makeText(context, "${binding.orderListBig::class.java.name}", Toast.LENGTH_SHORT).show()
        if(loginState){
            initList()
        }
    }

    private fun initBt(){
        binding.btOrderAll.setOnClickListener{btState("1")}
        binding.btsStateNopay.setOnClickListener{btState("未付款")}
        binding.btsStateNounity.setOnClickListener{btState("待成团")}
        binding.btsStateIng.setOnClickListener{btState("进行中")}
        binding.btsStateEnd.setOnClickListener{btState("已结束")}
    }

    private fun btState(state:String){
        orderList.clear()
//        Toast.makeText(context, "${orderList.size}", Toast.LENGTH_SHORT).show()
//        Toast.makeText(context, "$state +++ ${orderList.size}", Toast.LENGTH_SHORT).show()
            refreshList(state)

    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    private fun refreshList(state:String){
//        binding.orderListBig.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.Main).launch {
            try {
//                Log.d("userid", "${myIntent.getIntExtra("userId", -1)}")
                if(state == "1"){
                    orderList.addAll(OrderRepository.getAllOrders(myIntent.getIntExtra("userId", 1)))
                }else
                orderList.addAll(OrderRepository.getOrderByState(state, myIntent.getIntExtra("userId", 1)))
//                for(order in orderList){
//                    Log.d("order", "$order")
//                }
//                Toast.makeText(context, "$state +++ ${orderList.size}", Toast.LENGTH_SHORT).show()
//                orderAdapter = OrderAdapter(orderList)
//                if(binding.orderListBig == null)Toast.makeText(context, "list is null", Toast.LENGTH_SHORT).show()
//                if(orderAdapter == null)Toast.makeText(context, "order is null", Toast.LENGTH_SHORT).show()
//                Toast.makeText(context, "${binding.orderListBig.adapter}", Toast.LENGTH_SHORT).show()
//                binding.orderListBig.adapter = orderAdapter

                orderAdapter.notifyDataSetChanged()
//                Toast.makeText(context, "aaaa", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(context, "获取订单信息失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initList(){


        CoroutineScope(Dispatchers.Main).launch {
            try {
//                Log.d("userid", "${myIntent.getIntExtra("userId", -1)}")

                orderList.addAll(OrderRepository.getAllOrders(myIntent.getIntExtra("userId", 1)))
//                for(order in orderList){
//                    Log.d("order", "$order")
//                }
                orderAdapter = OrderAdapter(orderList)
//                Toast.makeText(context, "state +++ ${orderList.size}", Toast.LENGTH_SHORT).show()
//                if(binding.orderListBig == null)Toast.makeText(context, "list is null", Toast.LENGTH_SHORT).show()
//                if(orderAdapter == null)Toast.makeText(context, "order is null", Toast.LENGTH_SHORT).show()
//                Toast.makeText(context, "${binding.orderListBig.adapter}", Toast.LENGTH_SHORT).show()
                binding.orderListBig.adapter = orderAdapter

                orderAdapter.notifyDataSetChanged()
//                Toast.makeText(context, "aaaa", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(context, "获取订单信息失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initOrderState(){
        orderStateList.add("全部订单")
        orderStateList.add("待付款")
        orderStateList.add("待成团")
        orderStateList.add("进行中")
        orderStateList.add("已结束")
//        orderStateList.add("")
    }

    private fun initOrderStateAdapter(){
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

    override fun onItemClicked(state: String) {
        Log.d("state", "$state")
    }

}