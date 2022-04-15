package com.example.todo


import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.todo.base.BaseActivity
import com.example.todo.base.OnBackPressListener
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.utils.NotificationUtil
import com.example.todo.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import java.security.AccessController.getContext

class MainActivity : BaseActivity() {
    override fun initData() {

    }


    override fun initView() {

    }

    override fun startHttp() {

    }

    lateinit var activityMainBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        activityMainBinding.bottomNavigationView.also {
            val navController = Navigation.findNavController(this,R.id.nav_host_fragment)
            NavigationUI.setupWithNavController(it,navController)
        }
        NotificationUtil.setNotificationChannel(this)
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationUtil.show(baseContext, contentTitle = "收到消息", contentText = "msg")
        }
    }
}