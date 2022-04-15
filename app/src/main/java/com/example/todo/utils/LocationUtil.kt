package com.example.todo.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.example.todo.base.BaseApplication
import java.security.Permission
import java.security.Permissions


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      LocationUtil
 * @Author:         Yan
 * @CreateDate:     2022年04月14日 21:14:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    获得用户位置的工具类
 */


//private var latitude = 0.0
//private var longitude = 0.0
//
//
//private fun toggleGPS() {
//    val gpsIntent = Intent()
//    gpsIntent.setClassName(
//        "com.android.settings",
//        "com.android.settings.widget.SettingsAppWidgetProvider"
//    )
//    gpsIntent.addCategory("android.intent.category.ALTERNATIVE")
//    gpsIntent.data = Uri.parse("custom:3")
//    try {
//        PendingIntent.getBroadcast(requireActivity(), 0, gpsIntent, 0).send()
//    } catch (e: PendingIntent.CanceledException) {
//        e.printStackTrace()
//        if (ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        locationManager.requestLocationUpdates(
//            LocationManager.NETWORK_PROVIDER,
//            1000,
//            0f,
//            locationListener
//        )
//        val location1 = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//        if (location1 != null) {
//            latitude = location1.latitude // 经度
//            longitude = location1.longitude // 纬度
//        }
//    }
//}
//
//private fun getLocation() {
//    val location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//    if (location != null) {
//        latitude = location.latitude
//        longitude = location.longitude
//    } else {
//        if (ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        locationManager.requestLocationUpdates(
//            LocationManager.GPS_PROVIDER,
//            1000,
//            0f,
//            locationListener
//        )
//    }
//    Log.d("location", "纬度：$latitude\n经度：$longitude")
//}
//
//
//private var locationListener: LocationListener = object : LocationListener {
//
//    // Provider被enable时触发此函数，比如GPS被打开
//    override fun onProviderEnabled(provider: String) {
//        Log.e(TAG, provider)
//    }
//
//    // Provider被disable时触发此函数，比如GPS被关闭
//    override fun onProviderDisabled(provider: String) {
//        Log.e(TAG, provider)
//    }
//
//    // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
//    override fun onLocationChanged(location: Location) {
//        Log.e(
//            "Map",
//            "Location changed : Lat: " + location.latitude + " Lng: " + location.longitude
//        )
//        latitude = location.latitude // 经度
//        longitude = location.longitude // 纬度
//    }
//}

/*
 *
 * 打开和关闭gps第二种方法
 * private void openGPSSettings() {
//获取GPS现在的状态（打开或是关闭状态）
boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER);
if (gpsEnabled) {
//关闭GPS
Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, false);
} else {
//打开GPS
Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, true);
}
}*/


/*
//class LocationUtil (val context: Context){
//
//    val LOCATION_REQUEST_CODE = 1
//    val LISTENER_REQUEST_CODE = 1
//
//    private val locationManager = BaseApplication.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//    //需要的权限
//    val permissionList = listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
//
//    //回调接口
//    private var locationListener: LocationListener = object : LocationListener {
//        override fun onLocationChanged(location: Location) {
//            Log.i(TAG, "onLocationChanged: 经纬度发生变化")
//            //调用更新位置
////            updateToNewLocation(location)
//        }
//
//        override fun onProviderDisabled(provider: String) {
////            updateToNewLocation(null)
//            Log.i(TAG, "onProviderDisabled: ")
//        }
//
//        override fun onProviderEnabled(provider: String) {
//            Log.i(TAG, "onProviderEnabled: ")
//        }
//    }
//
//
//
//    @SuppressLint("MissingPermission")
//    fun getLocationInfo() {
//
//        //判断是否开启位置服务，没有则跳转至设置来开启
//        if (isLocationServiceOpen()) {
//            //获取所有支持的provider
//            val providers = locationManager.getProviders(true)
//            //用来存储最优的结果
//            var betterLocation: Location? = null
//            for (provider in providers) {
//                val location = locationManager.getLastKnownLocation(provider)
//                location?.let {
//                    Log.i(TAG, "$provider 精度为：${it.accuracy}")
//                    if (betterLocation == null) {
//                        betterLocation = it
//                    } else {
//                        //因为半径等于精度，所以精度越低代表越准确
//                        if (it.accuracy < betterLocation!!.accuracy)
//                            betterLocation = it
//                    }
//                }
//                if (location == null) {
//                    Log.i(TAG, "$provider 获取到的位置为null")
//                }
//            }
//            betterLocation?.let {
//                Log.i(TAG, "精度最高的获取方式：${it.provider} 经度：${it.longitude}  纬度：${it.latitude}")
//            }
//
//            //（四）若所支持的provider获取到的位置均为空，则开启连续定位服务
//            if (betterLocation == null) {
//                for (provider in locationManager.getProviders(true)) {
//                    locationMonitor(provider)
//                }
//                Log.i(TAG, "getLocationInfo: 获取到的经纬度均为空，已开启连续定位监听")
//            }
//        } else {
//            toast("请跳转到系统设置中打开定位服务")
//        }
//    }
//
//    /**
//     * 判断定位服务是否开启
//     */
//    private fun isLocationServiceOpen(): Boolean {
//        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//        //有一个开启就可
//        return gps || network
//    }
//
//    /**
//     * 连续监听
//     */
//    @SuppressLint("MissingPermission")
//    private fun locationMonitor(provider: String) {
//        if (PermissionUtil.requestPermission(
//                LISTENER_REQUEST_CODE,
//                permissionList.toList(),
//                context
//            )
//        ) {
//            locationManager.requestLocationUpdates(
//                provider,
//                60000.toLong(),		//超过1分钟则更新位置信息
//                8.toFloat(),		//位置超过8米则更新位置信息
//                locationListener
//            )
//        }
//    }
//}

 */


/*
     *
    private var locationManager: LocationManager = BaseApplication.instance.getSystemService(LOCATION_SERVICE) as LocationManager
    private var locationProvider: String? = null

    //2.获取位置提供器，GPS或是NetWork
    private val location: Unit
    get() {

    //2.获取位置提供器，GPS或是NetWork
    val providers = locationManager.getProviders(true)
    //                if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    //如果是GPS
    locationProvider = LocationManager.GPS_PROVIDER
    Log.v("TAG", "定位方式GPS")
    } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
    //如果是Network
    locationProvider = LocationManager.NETWORK_PROVIDER
    Log.v("TAG", "定位方式Network")
    } else {
    toast( "没有可用的位置提供器")
    return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
    if (ContextCompat.checkSelfPermission(
    requireActivity(),
    Manifest.permission.ACCESS_FINE_LOCATION
    )
    != PackageManager.PERMISSION_GRANTED ||
    ActivityCompat.checkSelfPermission(
    requireActivity(),
    Manifest.permission.ACCESS_COARSE_LOCATION
    )
    != PackageManager.PERMISSION_GRANTED
    ) {
    //请求权限
    ActivityCompat.requestPermissions(requireActivity(),
    arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
    ), LOCATION_CODE
    )
    } else {
    //3.获取上次的位置，一般第一次运行，此值为null
    val location = locationManager.getLastKnownLocation(
    locationProvider!!
    )
    if (location != null) {
    toast(location.longitude.toString() + " " + location.latitude + "")
    Log.v(
    "TAG",
    "获取上次的位置-经纬度：" + location.longitude + "   " + location.latitude
    )
    getAddress(location)
    } else {
    //监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
    locationManager.requestLocationUpdates(
    locationProvider!!,
    3000,
    1f,
    locationListener
    )
    }
    }
    } else {
    val location = locationManager.getLastKnownLocation(
    locationProvider!!
    )
    if (location != null) {
    toast(location.longitude.toString() + " " + location.latitude + "", Toast.LENGTH_SHORT)
    Log.v(
    "TAG",
    "获取上次的位置-经纬度：" + location.longitude + "   " + location.latitude
    )
    getAddress(location)
    } else {
    //监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
    locationManager.requestLocationUpdates(
    locationProvider!!,
    3000,
    1f,
    locationListener
    )
    }
    }
    }
    private var locationListener: LocationListener = object : LocationListener {

    // Provider被enable时触发此函数，比如GPS被打开
    override fun onProviderEnabled(provider: String) {}

    // Provider被disable时触发此函数，比如GPS被关闭
    override fun onProviderDisabled(provider: String) {}

    //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
    override fun onLocationChanged(location: Location) {
    //如果位置发生变化，重新显示地理位置经纬度
    toast(location.longitude.toString() + " " + location.latitude + "", Toast.LENGTH_SHORT)
    Log.v("TAG", "监视地理位置变化-经纬度：" + location.longitude + "   " + location.latitude)
    }
    }

    override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
    ) {
    when (requestCode) {
    LOCATION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
    toast( "申请权限")
    try {
    val providers = locationManager.getProviders(true)
    if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
    //如果是Network
    locationProvider = LocationManager.NETWORK_PROVIDER
    } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
    //如果是GPS
    locationProvider = LocationManager.GPS_PROVIDER
    }
    val location = locationManager.getLastKnownLocation(
    locationProvider!!
    )
    if (location != null) {
    toast(location.longitude.toString() + " " + location.latitude + "")
    Log.v(
    "TAG",
    "获取上次的位置-经纬度：" + location.longitude + "   " + location.latitude
    )
    } else {
    // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
    locationManager.requestLocationUpdates(
    locationProvider!!,
    0,
    0f,
    locationListener
    )
    }
    } catch (e: SecurityException) {
    e.printStackTrace()
    }
    } else {
    toast("缺少权限")
    }
    }
    }

    //获取地址信息:城市、街道等信息
    private fun getAddress(location: Location?): List<Address>? {
    var result: List<Address>? = null
    try {
    if (location != null) {
    val gc = Geocoder( requireActivity(), Locale.getDefault())
    result = gc.getFromLocation(
    location.latitude,
    location.longitude, 1
    )
    toast( "获取地址信息：$result")
    Log.v("TAG", "获取地址信息：$result")
    }
    } catch (e: Exception) {
    e.printStackTrace()
    }
    return result
    }

    override fun onDestroy() {
    super.onDestroy()
    locationManager.removeUpdates(locationListener)
    }

    companion object {
    const val LOCATION_CODE = 301
    }
 */
