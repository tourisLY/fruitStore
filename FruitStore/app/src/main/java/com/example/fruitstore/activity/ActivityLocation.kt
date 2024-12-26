package com.example.fruitstore.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.ui.text.style.TextAlign
//import androidx.media3.effect.TextOverlay
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BitmapDescriptor
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.MapStatusUpdate
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MultiPointItem
import com.baidu.mapapi.map.MultiPointOption
import com.baidu.mapapi.map.TextOptions
import com.baidu.mapapi.model.LatLng
import com.example.fruitstore.R
import com.example.fruitstore.databinding.ActivityLocationBinding

class ActivityLocation : AppCompatActivity() {
    private lateinit var binding: ActivityLocationBinding
    private lateinit var locationClient: LocationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            SDKInitializer.setAgreePrivacy(applicationContext, true)
            LocationClient.setAgreePrivacy(true)
            SDKInitializer.initialize(applicationContext)
            SDKInitializer.setCoordType(CoordType.BD09LL)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "init failed!", Toast.LENGTH_SHORT).show()
        }

        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化定位客户端
        initLocationClient()

        // 获取地图对象
        val mBaiduMap = binding.BDmapView.map

        // 设置一个默认的经纬度（可省略，等待定位结果）
        val latLng: LatLng = LatLng(30.2909, 119.7131)
        val update = MapStatusUpdateFactory.newLatLngZoom(latLng, 15f)  // 设置默认缩放级别为15
        mBaiduMap.animateMapStatus(update)

        drawCircle()
        drawText()
    }

    // 定位功能：监听定位回调
    private inner class MyLocationListener : BDLocationListener {
        override fun onReceiveLocation(position: BDLocation?) {
            position?.let {
                val latLng = LatLng(30.2909, 119.7131)

                // 更新地图状态，定位到当前位置并进行缩放
                val update = MapStatusUpdateFactory.newLatLngZoom(latLng, 16f)  // 可以根据需求调整缩放级别
                binding.BDmapView.map.animateMapStatus(update)

                // Log 输出定位结果
                val builder = StringBuilder()
                builder.append("经度：${it.longitude}--纬度：${it.latitude}")
                Log.d("baidu", builder.toString())

                // 你可以在这里显示位置信息，例如：
                // binding.tvResult.text = builder.toString()
            }
        }
    }

    private fun drawCircle(){
        val mBaiduMap = binding.BDmapView.map

        val locations = ArrayList<LatLng>()
        locations.add(LatLng(30.2909, 119.7131))

        val originalBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.point)

        // 调整图标大小，例如将图标缩放为原来的1.5倍
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.width * 4, originalBitmap.height * 4, true)

        // 将缩放后的Bitmap转换为BitmapDescriptor
        val bitmap: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(scaledBitmap)
//        val bitmap: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.point)

        val multiPointItems = ArrayList<MultiPointItem>()
        for(location in locations){
            val multiPointItem: MultiPointItem = MultiPointItem(location)
            multiPointItems.add(multiPointItem)
        }

        val multiPointOption: MultiPointOption = MultiPointOption()
        multiPointOption.setMultiPointItems(multiPointItems)
        multiPointOption.setIcon(bitmap)

        val mMultiPoint = mBaiduMap.addOverlay(multiPointOption)


    }

    private fun drawText() {
        val mBaiduMap = binding.BDmapView.map

        // 设置文字的坐标
        val location = LatLng(30.2909, 119.7131) // 这里使用浙江农林大学的经纬度

        // 创建文字的Overlay
//        val textOverlay = TextOverlay()
        val text = "浙江农林大学店" // 你要显示的文字
        val textOptions = TextOptions()
            .position(location) // 文字的位置
            .fontSize(24) // 文字大小
            .fontColor(0xFFFF0000.toInt()) // 文字颜色 (红色)
            .text(text) // 显示的文字
//            .align(TextAlign.CENTER) // 文字对齐方式（居中）

        // 添加Overlay
        mBaiduMap.addOverlay(textOptions)
    }
    // 初始化定位
    private fun initLocationClient() {
        locationClient = LocationClient(applicationContext)
        val option = LocationClientOption()
        option.openGps = true // 开启GPS
        option.setCoorType("bd09ll") // 设置坐标类型
        option.setScanSpan(1000) // 设置定位间隔时间

        locationClient.locOption = option
        locationClient.registerLocationListener(MyLocationListener()) // 注册监听器
        locationClient.start() // 启动定位
    }

    // 清理资源
    override fun onDestroy() {
        super.onDestroy()
        locationClient.stop() // 停止定位
        binding.BDmapView.map.isBaiduHeatMapEnabled = false // 关闭热力图
        binding.BDmapView.onDestroy() // 销毁地图
    }

    override fun onPause() {
        super.onPause()
        binding.BDmapView.onPause() // 暂停地图
    }

    override fun onResume() {
        super.onResume()
        binding.BDmapView.onResume() // 恢复地图
    }
}
