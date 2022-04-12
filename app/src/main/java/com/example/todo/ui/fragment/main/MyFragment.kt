package com.example.todo.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentLoginPhoneBinding
import com.example.todo.databinding.FragmentMyBinding

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment.main
 * @ClassName:      MyFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月10日 23:06:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
class MyFragment : BaseFragment() {
    override fun initData() {

    }

    override fun initView(view: View) {

    }

    override fun startHttp() {

    }

    private lateinit var myFragment : FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myFragment = FragmentMyBinding.inflate(inflater)
        return myFragment.root
    }
}