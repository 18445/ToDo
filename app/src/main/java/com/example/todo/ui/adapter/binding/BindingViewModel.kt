package com.example.todo.ui.adapter.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.todo.R
import com.example.todo.ui.adapter.core.DefaultViewHolder
import com.example.todo.ui.adapter.core.DefaultViewModel

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.binding
 * @ClassName:      BindingViewModel
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 21:24:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */

open class BindingViewModel<M>(override val layoutRes: Int, private val variableId: Int) :
    DefaultViewModel<M>() {

    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutRes,
            parent,
            false
        )
        val view = binding.root
        view.setTag(R.id.list_adapter_binding, binding)
        return view
    }

    override fun bindVH(viewHolder: DefaultViewHolder, payloads: List<Any>) {
        viewHolder.getBinding().setVariable(variableId, model)
        viewHolder.getBinding().executePendingBindings()
    }

    override fun unBindVH(viewHolder: DefaultViewHolder) {
        viewHolder.getBinding().unbind()
    }

}