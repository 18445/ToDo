package com.example.todo.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentTaskBinding

/**
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment.main
 * @ClassName:      TaskFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月10日 22:41:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    主页面Task的fragment
 */

class TaskFragment :BaseFragment(){

    override fun initData() {

    }

    override fun initView(view: View) {

    }

    override fun startHttp() {

    }
    private lateinit var taskFragment: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskFragment = FragmentTaskBinding.inflate(inflater)
        return taskFragment.root
    }


}