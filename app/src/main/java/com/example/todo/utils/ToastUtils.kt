package com.example.todo.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todo.base.BaseApplication
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      ToastUtils
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 17:00:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    toast 工具类
 */

const val SHOW_TOAST = "show_toast"

const val LOADING_STATE = "loading_state"


fun toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseApplication.instance, message, duration).show()
}

/**
 * 自定义Toast
 * Created by jzh on 2021-1-12.
 */
fun Context.toast(resId: Int) {
    val toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun Context.toast(text: CharSequence) {
    val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun Context.longToast(resId: Int) {
    val toast = Toast.makeText(this, resId, Toast.LENGTH_LONG)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun Context.longToast(text: CharSequence) {
    val toast = Toast.makeText(this, text, Toast.LENGTH_LONG)
    toast.run {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun View.toast(resId: Int) = context.toast(resId)

fun View.toast(text: CharSequence) = context.toast(text)

fun View.longToast(resId: Int) = context.longToast(resId)

fun View.longToast(text: CharSequence) = context.longToast(text)
