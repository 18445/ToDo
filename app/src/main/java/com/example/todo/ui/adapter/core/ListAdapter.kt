package com.example.todo.ui.adapter.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.core
 * @ClassName:      ListAdapter
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 20:37:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    ListAdapter
 */
class ListAdapter : BaseAdapter<ViewModelType>(){

    var dataList = mutableListOf<ViewModelType>()
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): ViewModelType? {
        return dataList[position]
    }

    fun clear(){
        val oldSize = itemCount
        dataList.clear()
        if(oldSize != 0){
            notifyItemRangeRemoved(0,oldSize)
        }
    }

    fun add(vm : ViewModelType):Boolean{
        val result = dataList.add(vm)
        notifyItemRangeInserted(itemCount - 1,1)
        return result
    }

    fun add(index : Int,element : ViewModelType){
        dataList.add(index,element)
        notifyItemRangeInserted(index,1)
    }

    fun removeAt(index : Int){
        dataList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun set(index : Int ,vm:ViewModelType){
        dataList[index] = vm
        notifyItemChanged(index,vm.model)
    }

    fun addAll(elements : Collection<ViewModelType>): Boolean{
        val oldSize = itemCount
        val added = dataList.addAll(elements)
        if(added){
            notifyItemRangeInserted(oldSize,itemCount - oldSize)
        }
        return added
    }

    fun remove(vm : ViewModelType) : Boolean{
        val index = dataList.indexOf(vm)
        if(index >=0){
            removeAt(index)
            return true
        }
        return false
    }

    fun replaceAll(list : List<ViewModelType>){
        dataList.clear()
        dataList.addAll(list)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        dataList.forEach {
            if(it is LifecycleViewModel){
                it.onStateChanged(source, event)
            }
        }
        if(event == Lifecycle.Event.ON_DESTROY){
            dataList.clear()
        }

        super.onStateChanged(source, event)
    }


}