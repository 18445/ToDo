package com.example.todo.ui.activity.login

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todo.R
import com.example.todo.base.BaseActivity
import com.example.todo.ui.fragment.LoginWithPasswordFragment
import com.example.todo.ui.fragment.LoginWithPhoneFragment
import com.example.todo.ui.repository.LoginRepository
import com.example.todo.ui.viewModel.LoginViewModel
import com.example.todo.utils.toast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread


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

    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun initData() {

        fragments.also {
            it.add(LoginWithPhoneFragment())
            it.add(LoginWithPasswordFragment())
        }
    }

    override fun initView() {

    }

    override fun startHttp() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityLoginBinding : com.example.todo.databinding.ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setTop("登录")

        activityLoginBinding.mbtnLogin.also {
            it.setOnClickListener {
                val password : String? = loginViewModel.getUserAccount()
                val userAccount : String? = loginViewModel.getUserPassword()

                if (password != null) {
                    Log.d("activityLoginBinding",password)
                }
                if (userAccount != null) {
                    Log.d("activityLoginBinding",userAccount)
                }

            }
        }

        activityLoginBinding.viewPager2.also{

            it.adapter = object : FragmentStateAdapter(supportFragmentManager,lifecycle) {

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }

                override fun getItemCount(): Int {
                    return fragments.size
                }
            }
        }

        val tabs : ArrayList<String> = arrayListOf("Phone","Password")

        val mediator = TabLayoutMediator(activityLoginBinding.tabLayout,activityLoginBinding.viewPager2
        ) { tab, position -> //这里可以自定义TabView
            val tabView = TextView(this@LoginActivity);

            tabView.text = tabs[position]
            tabView.textSize = 12f
            tabView.gravity = Gravity.CENTER

            tab.customView = tabView;
        }
        mediator.attach()
    }
}