package com.example.todo.utils

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      PermissionKtx
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 17:20:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    索取权限操作
 */
inline fun Fragment.requestPermission(
    permission: String,
    crossinline granted: (permission: String) -> Unit = {},
    crossinline denied: (permission: String) -> Unit = {},
    crossinline explained: (permission: String) -> Unit = {}

) {

    registerForActivityResult(ActivityResultContracts.RequestPermission()) { result: Boolean ->
        when {
            result -> granted.invoke(permission)
            shouldShowRequestPermissionRationale(permission) -> denied.invoke(permission)
            else -> explained.invoke(permission)
        }
    }.launch(permission)
}

inline fun AppCompatActivity.requestPermission(
    permission: String,
    crossinline granted: (permission: String) -> Unit = {},
    crossinline denied: (permission: String) -> Unit = {},
    crossinline explained: (permission: String) -> Unit = {}
) {

    registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        when {
            result -> granted.invoke(permission)
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission) -> denied.invoke(
                permission
            )
            else -> explained.invoke(permission)
        }
    }.launch(permission)
}

