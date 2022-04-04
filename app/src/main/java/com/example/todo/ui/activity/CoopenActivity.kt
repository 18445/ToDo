package com.example.todo.ui.activity


import android.os.Bundle
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.example.todo.R
import com.example.todo.base.BaseActivity
import com.example.todo.databinding.ActivityCoopenBinding

class CoopenActivity : BaseActivity() {

    override fun initData() {

    }

    override fun initView() {

    }

    override fun startHttp() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coopenBinding : ActivityCoopenBinding = DataBindingUtil.setContentView(this, R.layout.activity_coopen)
        coopenBinding.logo.also {
            it.fillLogoTextArray("To Do Something")
            it.startAnim()
        }

    }
}