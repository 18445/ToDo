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
 * @Description:     BaseActivity activity的基类
 */
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.example.todo.R
import com.example.todo.utils.LOADING_STATE
import com.example.todo.utils.SHOW_TOAST
import com.example.todo.utils.getRotateAnimation
import com.example.todo.utils.toast
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar
import kotlinx.android.synthetic.main.toolbar_layout.toolbar_left_image_back
import kotlinx.android.synthetic.main.toolbar_layout.toolbar_subtitle
import kotlinx.android.synthetic.main.toolbar_layout.toolbar_subtitle_image
import kotlinx.android.synthetic.main.toolbar_layout_search.*


abstract class BaseActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName
    val mScreenHeight by lazy{
        val resources: Resources = this.resources
        val dm: DisplayMetrics = resources.displayMetrics
        dm.heightPixels
    }

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
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
        LiveEventBus.get<Boolean>(LOADING_STATE).observe(this) {
            if (it) showLoading() else hideLoading()
        }
    }

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        LiveEventBus.get("isConnected", Boolean::class.java).observe(this) {
            if (it) startHttp()
        }
    }

    open fun showLoading() {
        toolbar_title?.visibility = View.GONE
        toolbar_loading?.visibility = View.VISIBLE
        toolbar_loading?.startAnimation(getRotateAnimation(0f, 360f))
    }

    open fun hideLoading() {
        toolbar_title?.visibility = View.VISIBLE
        toolbar_loading?.visibility = View.GONE
        toolbar_loading?.clearAnimation()
    }

    open fun isLoading() = toolbar_loading?.visibility == View.VISIBLE



    /**
     * 这里只适配了搜索按钮是图片的情况，是文字的话需要判断下
     */
    open fun showSearchLoading() {
        toolbar_subtitle_image?.visibility = View.GONE
        toolbar_search_loading_search?.visibility = View.VISIBLE
        toolbar_search_loading_search?.startAnimation(getRotateAnimation(0f, 360f))
    }

    open fun hideSearchLoading() {
        toolbar_search_subtitle_image?.visibility = View.VISIBLE
        toolbar_search_loading_search?.visibility = View.GONE
        toolbar_search_loading_search?.clearAnimation()
    }

    open fun isSearchLoading() = toolbar_search_loading_search?.visibility == View.VISIBLE

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

        observeUi()

        initData()
        initView()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //设置顶部toolbar相应样式
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    open fun setTop(
        title: String, subTitle: Any? = null, isBack: (() -> Unit)? = {
            toolbar_left_image_back?.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.write_back)
            )
            toolbar_left_image_back?.setOnClickListener { onBackPressed() }
        }
    ) {
        toolbar_title?.text = title
        toolbar_title?.isSelected = true
//        //小窗模式不计算状态栏的高度
//        if (!DensityUtil.isSmallWindow(this)) {
//            val layoutParams = toolbar?.layoutParams
//            layoutParams?.height =
//                getStatusBarHeight(this) + DensityUtil.dip2px(this, Constant.TOOLBAR_HEIGHT)
//            toolbar?.layoutParams = layoutParams
//        }
        //默认显示返回按钮
        isBack?.invoke()
        //根据subtitle的数据类型来显示图片或文字
        when (subTitle) {
            is String -> {
                toolbar_subtitle_image?.visibility = View.GONE
                toolbar_subtitle?.visibility = View.VISIBLE
                toolbar_subtitle?.text = subTitle
            }
            is Int -> {
                toolbar_subtitle?.visibility = View.GONE
                toolbar_subtitle_image?.visibility = View.VISIBLE
                toolbar_subtitle_image?.setImageResource(subTitle)
            }
            else -> {
                toolbar_subtitle?.visibility = View.GONE
                toolbar_subtitle_image?.visibility = View.GONE
            }
        }
    }
}