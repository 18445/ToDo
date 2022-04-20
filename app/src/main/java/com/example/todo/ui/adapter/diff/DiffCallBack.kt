package com.example.todo.ui.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.todo.ui.adapter.core.DefaultViewModel
import com.example.todo.ui.adapter.core.SameModel

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.diff
 * @ClassName:      DiffCallBack
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 21:19:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     DiffCallBack
 */

typealias ViewModelDiffType = DefaultViewModel<out SameModel>

class ArrayListAdapterCallBack<VM : ViewModelDiffType>(
    private val oldItems: List<VM>,
    private val newItems: List<VM>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].model?.isSameModelAs(newItems[newItemPosition].model as SameModel)
            ?: false
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].model?.isContentTheSameAs(newItems[newItemPosition].model as SameModel)
            ?: false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val result = oldItems[oldItemPosition].model?.getChangePayload(
            newItems[newItemPosition].model as SameModel
        )
        return result ?: super.getChangePayload(oldItemPosition, newItemPosition)
    }
}