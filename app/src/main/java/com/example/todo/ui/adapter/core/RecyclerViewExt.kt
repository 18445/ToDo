package com.example.todo.ui.adapter.core

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.core
 * @ClassName:      RecyclerViewExt
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 00:14:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    RecyclerViewExt RecyclerView的拓展函数，用于RecyclerView的相关设置
 */

//设置布局模式
fun RecyclerView.Adapter<*>.into(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager? = null
){
    //默认为线性布局
    recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = this
    if(this is LifecycleAdapter){
        val context = recyclerView.context
        if(context is LifecycleOwner){
            context.lifecycle.addObserver(this)
        }
    }
}