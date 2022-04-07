package com.example.todo.utils

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import androidx.annotation.RequiresApi

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      DensityUtil
 * @Author:         Yan
 * @CreateDate:     2022年04月04日 21:38:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     关于像素的工具类
 */


val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

inline val Double.dp: Float
    get() = run {
    return toFloat().dp
}

inline val Int.dp: Float
    get() = run {
    return toFloat().dp
}

