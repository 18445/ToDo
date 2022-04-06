package com.example.todo.base

import android.app.Application
import com.example.todo.httpUtils.ApiService

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
object BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }

    private var apiService : ApiService? = null


}