package com.example.todo.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.base
 * @ClassName:      BaseFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月04日 00:35:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */

abstract class BaseFragment : Fragment() {

    val TAG = javaClass.simpleName
    /**
     * 列表接口每页请求的条数
     */
    val pageSize = 20

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView(view: View)

    /**
     * 开始请求
     */
    abstract fun startHttp()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
    }

}