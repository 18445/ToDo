package com.example.todo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentLoginPasswordBinding
import com.example.todo.ui.activity.login.LoginActivity
import com.example.todo.ui.viewModel.LoginViewModel

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment
 * @ClassName:      LoginWithPasswordFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月08日 00:56:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
class LoginWithPasswordFragment : BaseFragment(){

    private lateinit var loginPasswordBinding: FragmentLoginPasswordBinding
    private val mLoginViewModel: LoginViewModel by activityViewModels()

    override fun initData() {

        loginPasswordBinding.loginViewModel = mLoginViewModel

    }

    override fun initView(view: View) {

    }

    override fun startHttp() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginPasswordBinding = FragmentLoginPasswordBinding.inflate(inflater)
        return loginPasswordBinding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()

    }

}