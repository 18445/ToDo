package com.example.todo.utils

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
 * @Description:     //TODO
 */

const val SHOW_TOAST = "show_toast"

const val LOADING_STATE = "loading_state"

fun toast(msg: String) {
    LiveEventBus
        .get<String>(SHOW_TOAST)
        .post(msg)
}