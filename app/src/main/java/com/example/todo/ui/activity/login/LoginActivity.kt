package com.example.todo.ui.activity.login

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.todo.R
import com.example.todo.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout_search.view.*


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.activity
 * @ClassName:      RegisterActivity
 * @Author:         Yan
 * @CreateDate:     2022年04月07日 12:59:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    用户注册activity
 */

class LoginActivity : BaseActivity(){


    override fun initData() {
        setTop("111")
    }

    override fun initView() {

    }

    override fun startHttp() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityLoginBinding : com.example.todo.databinding.ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)







    }
}