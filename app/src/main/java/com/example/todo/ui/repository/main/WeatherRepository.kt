package com.example.todo.ui.repository.main

import android.content.Context
import com.example.todo.base.BaseRepository
import com.qweather.sdk.view.QWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.repository.main
 * @ClassName:      WeatherRepository
 * @Author:         Yan
 * @CreateDate:     2022年04月15日 18:54:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    Weather仓库
 */
class WeatherRepository : BaseRepository(){

    suspend fun getWeatherNow(context : Context,location : String,listener : QWeather.OnResultWeatherNowListener){
        withContext(Dispatchers.IO){
            QWeather.getWeatherNow(context,location,listener)
        }
    }

    suspend fun getCity(context: Context,location: String,listener: QWeather.OnResultGeoListener){
        withContext(Dispatchers.IO){
            QWeather.getGeoCityLookup(context,location,listener)
        }
    }


}