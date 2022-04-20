package com.example.todo.ui.adapter.core

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.core
 * @ClassName:      DSL
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 20:38:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */

inline fun listAdapter(block : ListAdapter.() -> Unit):ListAdapter = ListAdapter().apply{
    block()
}

inline fun <M> layoutViewModelDsl(
    layoutRes : Int,
    model : M,
    crossinline init : DefaultViewHolder.() -> Unit
) = LayoutViewModel<M>(layoutRes).apply {
    this.model = model
    onCreateViewHolder {
        init.invoke(this)
    }
}