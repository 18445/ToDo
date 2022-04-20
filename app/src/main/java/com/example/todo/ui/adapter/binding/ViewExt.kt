package com.example.todo.ui.adapter.binding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.binding
 * @ClassName:      ViewExt
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 21:26:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    ViewExt
 */

fun RecyclerView.ViewHolder.getBinding(): ViewDataBinding {
    return itemView.getTag(R.id.list_adapter_binding) as ViewDataBinding
}