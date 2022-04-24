package com.example.todo.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.example.todo.utils.toast
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlin.random.Random

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
        //目标RecyclerView
        mListAdapter.into(rv_binding_list)
        //ListAdapter的加载项
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

    //Test ： 数据测试
    private fun createBindingViewModelList(max: Int = 10) = (0..max).map {
        if(it < 5){
            mTitleDemo
        }else{
            mRecyclerView2Demo
        }
    }


    private val mTitleDemo = layoutViewModelDsl(R.layout.toolbar_layout, ModelTest("title", "subTitle")) {

        itemView.setOnClickListener {
            val vm = getViewModel<LayoutViewModel<ModelTest>>()
            //修改Model数据
            toast("get View model")
            //用Adapter更新数据
            if (vm != null) {
                getAdapter<ListAdapter>()?.set(absoluteAdapterPosition, vm)
            }
        }
    }

    private val mRecyclerView2Demo = bindingViewModelDsl(
        //Item View
        R.layout.item_binding_layout,
        BR.model,
        //记载的Model类，差分处理
        ModelTest("title", "bindingViewModelDsl")
    ) {
        //点击事件
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

