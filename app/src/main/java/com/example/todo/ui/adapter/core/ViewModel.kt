package com.example.todo.ui.adapter.core

import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.core
 * @ClassName:      ViewModel
 * @Author:         Yan
 * @CreateDate:     2022年04月19日 23:42:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    ViewModel
 */

typealias ViewModelType = ViewModel<*,*>

interface ViewModel <M,VH : RecyclerView.ViewHolder>: ViewHolderFactory<VH> {
    var model: M?
    var itemViewType: Int
        get() = layoutRes
        set(value) {}
    @get:LayoutRes // 返回Layout资源

    val layoutRes: Int //布局文件
    var isFirstInit: Boolean //初始化

    fun bindVH(viewHolder: VH, payload: List<Any>) {}
    fun unBindVH(viewHolder: VH) {}
}

//生命周期监听
interface LifecycleViewModel{
    fun onStateChanged(source : LifecycleOwner,event:Lifecycle.Event)
}