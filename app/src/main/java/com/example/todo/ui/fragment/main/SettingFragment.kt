package com.example.todo.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import com.example.todo.BR
import com.example.todo.R
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentSettingBinding
import com.example.todo.ui.adapter.animators.firstAnimation
import com.example.todo.ui.adapter.animators.updateAnimation
import com.example.todo.ui.adapter.binding.BindingViewModel
import com.example.todo.ui.adapter.binding.bindingViewModelDsl

import com.example.todo.ui.adapter.core.*
import com.example.todo.ui.viewModel.ModelTest
import kotlinx.android.synthetic.main.fragment_setting.*

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

    private val mListAdapter by lazy {
        ListAdapter()
    }

    override fun initData() {
        mListAdapter.into(rv_binding_list)
        mListAdapter.addAll(createBindingViewModelList(20))
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

    private fun createBindingViewModelList(max: Int = 10) = (0..max).map {
        bindingViewModelDsl(
            R.layout.item_binding_layout,
            BR.model,
            ModelTest("title", "bindingViewModelDsl")
        ) {
            itemView.setOnClickListener {
                val viewModel = getViewModel<BindingViewModel<ModelTest>>()
                viewModel?.model?.title = "${java.util.Random().nextInt(100)}"
                if (viewModel != null) {
                    getAdapter<ListAdapter>()?.set(absoluteAdapterPosition, viewModel)
                }
            }
            onViewAttachedToWindow {
                firstAnimation()
                updateAnimation()
            }
        }
    }
}
