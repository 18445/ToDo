package com.example.todo.ui.viewModel

import com.example.todo.ui.adapter.core.SameModel

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.viewModel
 * @ClassName:      ViewModels
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 21:43:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */

/**
 * Model
 * // 扩展DiffModel  兼容DiffUtil
 */
data class ModelTest(var title: String, var subTitle: String) : SameModel {
    override fun <T : SameModel> getChangePayload(newItem: T): Any? {
        return null
    }

    override var uniqueId: String = title
}
