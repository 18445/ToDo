package com.example.todo.ui.activity.login

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.base.BaseActivity
import com.example.todo.constant.Constant
import com.example.todo.databinding.ActivityLoginBinding
import com.example.todo.ui.fragment.login.LoginWithPasswordFragment
import com.example.todo.ui.fragment.login.LoginWithPhoneFragment
import com.example.todo.ui.viewModel.LoginViewModel
import com.example.todo.utils.MyMMKV.mmkv
import com.example.todo.utils.toast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_login.*


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
    private val pagerCount = 2
    private val activeSize = 15f
    private val prepareSize = 12f
    private var mCurrentPage = 0
    private val mPhoneFragment = 0
    private val mPasswordFragment = 1


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
        val activityLoginBinding : ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setTop("登录")

        activityLoginBinding.mbtnLogin.also {
            it.setOnClickListener {
                loginIn(loginViewModel)
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

            it.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mCurrentPage = position
                    activityLoginBinding.mbtnLogin.setBackgroundColor(
                        if(mCurrentPage == mPhoneFragment) Color.parseColor("#ffb3a7")
                        else Color.parseColor("#FF03DAC5"))

                    for(i in 0 until pagerCount){
                        val tab = tabLayout.getTabAt(position)
                        val tabView = tab?.customView as TextView
                        if (tab.position == position) {
                            tabView.textSize = activeSize
                            tabView.typeface = Typeface.DEFAULT_BOLD
                        } else {
                            tabView.textSize = prepareSize
                            tabView.typeface = Typeface.DEFAULT
                        }
                    }

                }
            })


        val tabs : ArrayList<String> = arrayListOf("Phone","Password")

        val mediator = TabLayoutMediator(activityLoginBinding.tabLayout,activityLoginBinding.viewPager2
        ) { tab, position -> //这里可以自定义TabView
            val tabView = TextView(this@LoginActivity);

            tabView.text = tabs[position]
            tabView.textSize = prepareSize
            tabView.gravity = Gravity.CENTER

            tab.customView = tabView;
        }
        mediator.attach()
    }

    }
    private fun loginIn(loginViewModel:LoginViewModel){

        if(mCurrentPage == mPasswordFragment){
            val userAccount : String? = loginViewModel.getUserAccount()
            val password : String? = loginViewModel.getUserPassword()

            val passwordFragment = fragments[mPasswordFragment] as LoginWithPasswordFragment
            if (passwordFragment.checkIfValidate() && userAccount != null && password != null) {
                loginViewModel.loginIn(userAccount,password)
                loginViewModel.userToken.observeState(this){
                    onSuccess {
                        toast("登录成功")
                        mmkv.putString(Constant.Access_USER_TOKEN,it.token.accessToken)
                        mmkv.putString(Constant.Refresh_USER_TOKEN,it.token.refreshToken)
                    }
                }
            }
        }else if(mCurrentPage == mPhoneFragment){
            val userPhone : String? = loginViewModel.getUserPhone()
            val userVerify : String? = loginViewModel.getUserVerify()
            startActivity(Intent(this,MainActivity::class.java))
//            toast("暂不支持手机登录")
//            val phoneFragment = fragments[mPhoneFragment] as LoginWithPhoneFragment
//            if(phoneFragment.checkVerify() && userPhone != null && userVerify != null){
                  //TODO
//                验证验证码是否合法
//            }
        }

    }
}