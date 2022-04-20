package com.example.todo.ui.adapter.core

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter.core
 * @ClassName:      Model
 * @Author:         Yan
 * @CreateDate:     2022年04月20日 20:34:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    SameModel
 */


interface SameModel{
    var uniqueId : String
    fun <T:SameModel> isSameModelAs(model : T) : Boolean{
        return this.uniqueId == model.uniqueId
    }
    fun <T:SameModel> isContentTheSameAs(model : T) : Boolean{
        return this ==  model
    }
    fun <T:SameModel> getChangePayload(newItem : T) : Any? = null
}