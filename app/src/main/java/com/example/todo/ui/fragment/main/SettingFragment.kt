package com.example.todo.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentSettingBinding

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment.main
 * @ClassName:      SettingFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月10日 22:40:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    主页面Setting的fragment
 */
class SettingFragment : BaseFragment(){
    override fun initData() {

    }

    override fun initView(view: View) {

    }

    override fun startHttp() {

    }

    private lateinit var settingFragment: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingFragment = FragmentSettingBinding.inflate(inflater)
        return settingFragment.root
    }
}