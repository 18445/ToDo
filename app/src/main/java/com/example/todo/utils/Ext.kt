package com.example.todo.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import com.example.todo.R
import com.example.todo.constant.Constant
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      Ext
 * @Author:         Yan
 * @CreateDate:     2022年04月10日 02:07:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
/**
 * 拍照
 *
 * @param path 照片存放的路径
 */
fun captureImage(activity: Activity) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val file = File(
        activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        Constant.HEAD_PIC_PATH
    )
    //7.0版本下才能用file://，7.0以上需要用共享文件的形式：content://URI (在application的onCrate中添加代码也可以解决)
    val uri = Uri.fromFile(file)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
    activity.startActivityForResult(
        intent,
        Constant.IMAGE_CAPTURE
    )
}

/**
 * 从图库中选取图片
 */
fun selectImage(activity: Activity) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_PICK
    activity.startActivityForResult(
        intent,
        Constant.IMAGE_SELECT
    )
}

/**
 * 防止快速点击导致打开多个相同页面
 * @param v  防止多次点击的View
 * @param defaultTime 超时时间,默认300毫秒
 * @return
 */

//fun isInvalidClick(v: View, defaultTime: Long = 500): Boolean {
//    val curTimeStamp = System.currentTimeMillis()
//    val lastClickTimeStamp: Long
//    val o = v.getTag(R.id.)
//    if (o == null) {
//        v.setTag(R.id.invalid_click, curTimeStamp)
//        return false
//    }
//    lastClickTimeStamp = o as Long
//    val isInvalid = curTimeStamp - lastClickTimeStamp < defaultTime
//    if (!isInvalid) v.setTag(R.id.invalid_click, curTimeStamp)
//    return isInvalid
//}

/**
 * 格式化当前日期
 */
fun formatCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}

/**
 * String 转 Calendar
 */
fun String.stringToCalendar(): Calendar {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(this)
    val calendar = Calendar.getInstance()
    if (date != null) {
        calendar.time = date
    }
    return calendar
}