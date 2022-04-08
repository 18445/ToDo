package com.example.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentLoginPhoneBinding
import com.example.todo.ui.viewModel.LoginViewModel

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment
 * @ClassName:      LoginWithPhoneFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月08日 00:55:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
class LoginWithPhoneFragment : BaseFragment() {

    private lateinit var loginPhoneBinding: FragmentLoginPhoneBinding
    private val mLoginViewModel: LoginViewModel by activityViewModels()

    override fun initData() {
        loginPhoneBinding.loginViewModel = mLoginViewModel
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
        loginPhoneBinding = FragmentLoginPhoneBinding.inflate(inflater)
        return loginPhoneBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
    }

}