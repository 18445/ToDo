package com.example.todo.utils

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      Animation
 * @Author:         Yan
 * @CreateDate:     2022年04月07日 14:10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     动画的工具类
 */
/**
 * 获取旋转动画
 * @param fromDegress X轴顺时针转动到fromDegrees为旋转的起始点，
 * @param toDegress X轴顺时针转动到toDegrees为旋转的结束点
 */
fun getRotateAnimation(fromDegress: Float, toDegress: Float): Animation {
    val rotateAnimation = RotateAnimation(
        fromDegress,
        toDegress,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    rotateAnimation.fillAfter = true
    rotateAnimation.duration = 2000
    rotateAnimation.repeatCount = Animation.INFINITE
    rotateAnimation.repeatMode = Animation.RESTART
    rotateAnimation.interpolator = LinearInterpolator()
    return rotateAnimation
}