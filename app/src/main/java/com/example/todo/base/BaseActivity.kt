package com.example.todo.base

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.base
 * @ClassName:      BaseActivity
 * @Author:         Yan
 * @CreateDate:     2022年04月04日 00:27:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //BaseActivity
 */
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat


abstract class BaseActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun startHttp()

    override fun onCreate(savedInstanceState: Bundle?) {
        //防止输入法顶起底部布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        //去掉系统自带标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        super.onCreate(savedInstanceState)
        //设置状态栏透明
        val window = this.window
        val decorView = window.decorView

        // 这是 Android 做了兼容的 Compat 包
        // 下面这个设置后会沉浸式状态栏和导航栏
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = ViewCompat.getWindowInsetsController(decorView)
        // 设置状态栏字体颜色为黑色
        windowInsetsController?.isAppearanceLightStatusBars = true
        //把状态栏颜色设置成透明
        window.statusBarColor = Color.TRANSPARENT


        initData()
        initView()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}