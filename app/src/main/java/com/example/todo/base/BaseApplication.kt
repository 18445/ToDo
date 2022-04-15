package com.example.todo.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.todo.httpUtils.ApiService
import com.qweather.plugin.view.QWeatherConfig
import com.qweather.sdk.view.HeConfig
import com.tencent.mmkv.MMKV

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo
 * @ClassName:      MyApplication
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 09:05:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     Application 管理全局变量
 */

class BaseApplication : Application() {


    companion object {


        lateinit var instance: Application
    }




    override fun onCreate() {
        super.onCreate()

        QWeatherConfig.init("d3da066cd5f94ab69445c4763b2531a3")
        HeConfig.init("HE2204142033381774","c7c64dfc56ef41349784636d554d6d15")
        HeConfig.switchToDevService()

        instance = this

        val rootDir = MMKV.initialize(this)
        Log.d(TAG, "mmkv_root------:${rootDir}")

    }

    private var apiService : ApiService? = null


}


