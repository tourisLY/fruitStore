package com.example.fruitstore.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.fruitstore.R
import com.example.fruitstore.activity.ActivityLogin
import com.example.fruitstore.activity.Activity_store
import com.example.fruitstore.databinding.FragmentHomeBinding
import com.example.fruitstore.entity.GoodLeft
import com.example.fruitstore.entity.User
import com.example.fruitstore.repository.GoodLeftRepository
import com.example.fruitstore.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var myIntent: Intent
    private var loginState: Boolean = false
    private lateinit var userAccount: String

    private val userRepository = UserRepository()

    // 获取布局中的元素
    private lateinit var notLoggedInLayout: LinearLayout
    private lateinit var loggedInLayout: LinearLayout
    private lateinit var userNameTextView: TextView
    private lateinit var userAvatarImageView: ImageView
    private lateinit var loginButton: TextView
    private lateinit var vipStatusTextView: TextView
    private lateinit var balanceTextView: TextView
    private lateinit var ticketNumTextView: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val inActivity = activity
        if (inActivity != null) {
            myIntent = inActivity.intent
            loginState = myIntent.getBooleanExtra("loginState", false)
            userAccount = myIntent.getStringExtra("userAccount") ?: "wrong"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        // 获取布局中的元素
        notLoggedInLayout = binding.notLoggedInLayout
        loggedInLayout = binding.loggedInLayout
        userNameTextView = binding.userName
        userAvatarImageView = binding.userImg
        loginButton = binding.loginButton
        vipStatusTextView = binding.vipStatus
        balanceTextView = binding.balanceNum
        ticketNumTextView = binding.ticketNum

        // 根据登录状态更新布局
        updateUI()

        // 设置登录按钮点击事件
        loginButton.setOnClickListener {
            // 当用户点击登录按钮时，跳转到登录页面
            startActivity(Intent(context, ActivityLogin::class.java))
        }

        binding.menDianZiQu.setOnClickListener{
            val intent = Intent(context, Activity_store::class.java)
            startActivity(intent)
        }

        // 获取用户信息
        if (loginState) {
            initView()
        }
    }

    // 根据登录状态更新UI
    private fun updateUI() {
        if (loginState) {
            notLoggedInLayout.visibility = View.GONE
            loggedInLayout.visibility = View.VISIBLE
        } else {
            notLoggedInLayout.visibility = View.VISIBLE
            loggedInLayout.visibility = View.GONE
        }
    }

    private fun initView(){
        CoroutineScope(Dispatchers.Main).launch {
            try {

                val users:List<User> = userRepository.getUserByAccount(userAccount)
                if(users.isEmpty()){
                    Toast.makeText(context, "用户信息获取失败",Toast.LENGTH_SHORT).show()
                }else{
                    for(user in users){
                        binding.userName.text = user.userName
                        binding.balanceNum.text = user.userBalance.toString()
                        binding.userImg.setImageResource(resources.getIdentifier(user.userHead, "drawable", requireContext().packageName))
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(context, "用户信息获取失败",Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 调整布局的高度，基于登录状态
    private fun adjustLayoutHeight() {
        val layoutParams: ViewGroup.LayoutParams = binding.notLoggedInLayout.layoutParams
        if (loginState) {
            layoutParams.height = dpToPx(160)
        } else {
            layoutParams.height = dpToPx(80)
        }
        binding.notLoggedInLayout.layoutParams = layoutParams
    }

    // dp转换为px
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}