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
 * @UpdateRemark:   更新说明： 1.1 更新注释
 * @Version:        1.1
 * @Description:    ListAdapter Adapter加载的基本类 通过加载不同ViewModelType来显示不同的Item
 *                  每一个Item都可以看作一个ViewModelType
 */

class ListAdapter : BaseAdapter<ViewModelType>(){
    //总数据
    var dataList = mutableListOf<ViewModelType>()

    //总数据项
    override fun getItemCount(): Int {
        return dataList.size
    }

    //加载数据项
    override fun getItem(position: Int): ViewModelType? {
        return dataList[position]
    }

    //清楚所有数据
    fun clear(){
        val oldSize = itemCount
        dataList.clear()
        if(oldSize != 0){
            notifyItemRangeRemoved(0,oldSize)
        }
    }

    //添加数据
    fun add(vm : ViewModelType):Boolean{
        val result = dataList.add(vm)
        notifyItemRangeInserted(itemCount - 1,1)
        return result
    }

    //在指定的位置添加数据
    fun add(index : Int,element : ViewModelType){
        dataList.add(index,element)
        notifyItemRangeInserted(index,1)
    }

    //移除指定位置上的数据
    fun removeAt(index : Int){
        dataList.removeAt(index)
        notifyItemRemoved(index)
    }

    //更改某个位置上的数据
    fun set(index : Int ,vm:ViewModelType){
        dataList[index] = vm
        notifyItemChanged(index,vm.model)
    }

    //将集合中的元素添加进入
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

    //用集合替代原有数据
    fun replaceAll(list : List<ViewModelType>){
        dataList.clear()
        dataList.addAll(list)
    }

    //改变回调
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