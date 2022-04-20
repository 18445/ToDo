package com.example.todo.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.extension.StateLiveData
import com.example.todo.ui.repository.main.WeatherRepository
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import kotlinx.coroutines.launch

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.viewModel
 * @ClassName:      WeatherViewModel
 * @Author:         Yan
 * @CreateDate:     2022年04月15日 20:51:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    天气的ViewModel
 */

class WeatherViewModel : ViewModel() {

    private val mWeatherRepository = WeatherRepository()

    val mWeatherNowBean : MutableLiveData<WeatherNowBean> = MutableLiveData()
    val mGeoBean : MutableLiveData<GeoBean> = MutableLiveData()
    val mWeatherHourlyBean : MutableLiveData<WeatherHourlyBean> = MutableLiveData()


    //获得当前时间的天气
    fun getWeatherNow(context : Context, location : String, listener : QWeather.OnResultWeatherNowListener){
        viewModelScope.launch {
            mWeatherRepository.getWeatherNow(context, location, listener)
        }
    }

    fun getCity(context: Context,location: String,listener: QWeather.OnResultGeoListener){
        viewModelScope.launch {
            mWeatherRepository.getCity(context, location, listener)
        }
    }

    fun getWeather24Hourly(context: Context,location: String,listener:QWeather.OnResultWeatherHourlyListener){
        viewModelScope.launch {
            mWeatherRepository.getWeather24Hourly(context,location,listener)
        }
    }


}