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
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.example.todo.utils.SHOW_TOAST
import com.example.todo.utils.toast
import com.jeremyliao.liveeventbus.LiveEventBus


abstract class BaseActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName
    var mScreenHeight = 0

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

    private fun observeUi() {
        LiveEventBus.get<String>(SHOW_TOAST).observe(this) {
            toast(it)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //防止输入法顶起底部布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        //去掉系统自带标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        val resources: Resources = this.resources
        val dm: DisplayMetrics = resources.displayMetrics
        mScreenHeight = dm.heightPixels

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

        observeUi()

        initData()
        initView()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}