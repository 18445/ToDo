package com.example.todo.utils

import com.tencent.mmkv.MMKV

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.utils
 * @ClassName:      MyMMKV
 * @Author:         Yan
 * @CreateDate:     2022年04月10日 18:54:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     MMKV 的全局初始化
 */
object MyMMKV {

    private const val fileName = "todo_mvvm"

    /**
     * 初始化mmkv
     */
    val mmkv: MMKV
        get() = MMKV.mmkvWithID(fileName)

    /**
     * 删除全部数据(传了参数就是按key删除)
     */
    fun deleteKeyOrAll(key: String? = null) {
        if (key == null) mmkv.clearAll()
        else mmkv.removeValueForKey(key)
    }

    /** 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    fun contains(key: String) = mmkv.contains(key)
}