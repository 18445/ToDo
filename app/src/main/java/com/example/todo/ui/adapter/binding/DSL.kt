package com.example.todo.ui.adapter.binding

import com.example.todo.ui.adapter.core.DefaultViewHolder

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.binding
 * @ClassName:      DSL
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 21:26:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    bindingViewModelDsl
 */

inline fun <M> bindingViewModelDsl(
    layoutRes: Int,
    variableId: Int,
    model:M,
    crossinline init: DefaultViewHolder.() -> Unit
): BindingViewModel<M> {
    return BindingViewModel<M>(layoutRes, variableId).apply {
        this.model = model
        onCreateViewHolder{
            init.invoke(this)
        }
    }
}