package com.example.todo.ui.fragment.main

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.base.BaseApplication
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentWeatherBinding
import com.example.todo.ui.viewModel.WeatherViewModel
import com.example.todo.utils.getNoMoreThanTwoDigits

import com.example.todo.utils.toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather


/**
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment.main
 * @ClassName:      TaskFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月10日 22:41:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    主页面Task的fragment
 */

class WeatherFragment :BaseFragment(){
    private lateinit var address:String
    private lateinit var weatherBinding: FragmentWeatherBinding

    private val locationManager : LocationManager by lazy {
        (BaseApplication.instance.getSystemService(LOCATION_SERVICE) as LocationManager)
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initData() {
        getPermission()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        weatherBinding = FragmentWeatherBinding.inflate(inflater)
        return weatherBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getPermission(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d("permission","Manifest.permission.ACCESS_FINE_LOCATION")
                    getLocationInfo()
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("permission","Manifest.permission.ACCESS_COARSE_LOCATION")
                    getLocationInfo()
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
                toast("没有权限")
            }
            }
        }

        //获取用户权限
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

//        if (ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            toast("没有获得用户权限")
//        }else{
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener {
//                    Log.d("fusedLocationClient","********")
//                    Log.d("fusedLocationClient","${it.provider} 经度：${it.longitude}  纬度：${it.latitude}")
//                }
//        }
    }

    private fun locationMonitor(provider: String) {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(
            provider,
            60000.toLong(),		//超过1分钟则更新位置信息
            8.toFloat(),		//位置超过8米则更新位置信息
            locationListener
        )
    }

    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.i(TAG, "onLocationChanged: 经纬度发生变化")
            //调用更新位置
//            updateToNewLocation(location)
        }

        override fun onProviderDisabled(provider: String) {
//            updateToNewLocation(null)
            Log.i(TAG, "onProviderDisabled: ")
        }

        override fun onProviderEnabled(provider: String) {
            Log.i(TAG, "onProviderEnabled: ")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationInfo() {
        //判断是否开启位置服务，没有则跳转至设置来开启
        if (isLocationServiceOpen()) {
            //获取所有支持的provider
            val providers = locationManager.getProviders(true)
            //用来存储最优的结果
            var betterLocation: Location? = null
            for (provider in providers) {
                 if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                         requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }

                val location = locationManager.getLastKnownLocation(provider)
                location?.let {
                    Log.i(TAG, "$provider 精度为：${it.accuracy}")
                    if (betterLocation == null) {
                        betterLocation = it
                    } else {
                        //因为半径等于精度，所以精度越低代表越准确
                        if (it.accuracy < betterLocation!!.accuracy)
                            betterLocation = it
                    }
                }
                if (location == null) {
                    Log.i(TAG, "$provider 获取到的位置为null")
                }
            }
            betterLocation?.let {
                Log.i(TAG, "精度最高的获取方式：${it.provider} 经度：${it.longitude}  纬度：${it.latitude}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    initAddress(it)
                    if(this::address.isLateinit){
                        getWeatherNow()
                        getCity()
                    }
                }else{
                    toast("当前手机版本过低")
                }
            }
            //（四）若所支持的provider获取到的位置均为空，则开启连续定位服务
            if (betterLocation == null) {
                for (provider in locationManager.getProviders(true)) {
                    locationMonitor(provider)
                }
                Log.i(TAG, "getLocationInfo: 获取到的经纬度均为空，已开启连续定位监听")
            }
        } else {
            toast( "请跳转到系统设置中打开定位服务")
        }
    }

    /**
     * 判断定位服务是否开启
     */
    private fun isLocationServiceOpen(): Boolean {
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }

    override fun initView(view: View) {

        weatherViewModel.also { weatherViewModel ->
            weatherViewModel.mWeatherNowBean.observe(this){
                weatherBinding.weather = it.now
            }
            weatherViewModel.mGeoBean.observe(this){
                weatherBinding.location = it.locationBean[0]
            }
        }
        weatherBinding.cardView.setBackgroundResource(R.drawable.card_gradient_background)
    }

    override fun startHttp() {

    }

    private fun getCity(){
        weatherViewModel.getCity(requireContext(),address,object : QWeather.OnResultGeoListener{
            override fun onError(p0: Throwable?) {
                toast("网络出现异常")
                Log.e(TAG,p0.toString())
            }

            override fun onSuccess(p0: GeoBean?) {
                weatherViewModel.mGeoBean.value = p0
            }

        })
    }


    private fun getWeatherNow(){
        weatherViewModel.getWeatherNow(requireContext(),address,object :QWeather.OnResultWeatherNowListener{
            override fun onError(p0: Throwable?) {
                toast("网络出现异常")
                Log.e(TAG,p0.toString())
            }

            override fun onSuccess(p0: WeatherNowBean?) {
                weatherViewModel.mWeatherNowBean.value = p0
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initAddress(location: Location){
        val longitude = location.longitude.getNoMoreThanTwoDigits()
        val latitude = location.latitude.getNoMoreThanTwoDigits()
        address = "${longitude},${latitude}"
    }
}













