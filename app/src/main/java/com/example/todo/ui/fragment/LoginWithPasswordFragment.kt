package com.example.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentLoginPasswordBinding
import com.example.todo.ui.viewModel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login_password.*


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment
 * @ClassName:      LoginWithPasswordFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月08日 00:56:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     密码登录界面的Fragment
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()

    }

    /**
     * 显示错误提示，并获取焦点
     * @param textInputLayout
     * @param error
     */
    private fun showError(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
        textInputLayout.editText!!.isFocusable = true
        textInputLayout.editText!!.isFocusableInTouchMode = true
        textInputLayout.editText!!.requestFocus()
    }

    /**
     * 验证用户名
     * @param account
     * @return
     */
    private fun validateAccount(account: String): Boolean {
        if (account.isEmpty()){
            showError(til_account, "用户名不能为空")
            return false
        }
        return true
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            showError(til_password, "密码不能为空")
            return false
        }
        if (password.length < 6 || password.length > 20) {
            showError(til_password, "密码长度为6-20位")
            return false
        }
        return true
    }

    fun checkIfValidate() : Boolean{

        val account = til_account.editText!!.text.toString()
        val password = til_password.editText!!.text.toString()

        til_account.isErrorEnabled = false
        til_password.isErrorEnabled = false

        //验证用户名和密码
         return validateAccount(account) && validatePassword(password)

    }

}