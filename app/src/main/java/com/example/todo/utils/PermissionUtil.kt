package com.example.todo.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      PermissionUtil
 * @Author:         Yan
 * @CreateDate:     2022年04月14日 21:26:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    索取权限
 */
object PermissionUtil {

    /**
     * 验证是否有权限，没有则申请
     */
    fun requestPermission(requestCode: Int, permissionList: List<String>, context: Context): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //没有同意需要申请的权限
            val requestPermissionList = mutableListOf<String>()
            for (permission in permissionList) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissionList.add(permission)
                }
            }
            return if (requestPermissionList.size > 0) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    requestPermissionList.toTypedArray(),
                    requestCode
                )
                false
            } else {
                true
            }
        } else {
            return true
        }
    }

    /**
     *验证权限申请的结果
     */
    fun verifyResult(grantResults: IntArray,context: Context): Boolean {
        if (grantResults.isNotEmpty()) {
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    toast("必须要同意权限才能使用该功能")
                    return false
                }
            }
            return true
        } else {
            toast("发生未知错误")
            return false
        }
    }
}
