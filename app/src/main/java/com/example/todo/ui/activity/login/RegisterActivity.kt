package com.example.todo.ui.activity.login

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.base.BaseActivity
import com.example.todo.databinding.ActivityRegisterBinding
import com.example.todo.ui.viewModel.LoginViewModel
import com.example.todo.ui.viewModel.RegisterViewModel
import com.example.todo.utils.toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.activity.login
 * @ClassName:      RegisterActivity
 * @Author:         Yan
 * @CreateDate:     2022年04月07日 19:15:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    用户注册界面
 */
class RegisterActivity : BaseActivity(){
    override fun initData() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun initView() {

    }

    override fun startHttp() {

    }

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityRegisterBinding : ActivityRegisterBinding= DataBindingUtil.setContentView(this, R.layout.activity_register)
        activityRegisterBinding.loginViewModel = registerViewModel
        setTop("登录")

        activityRegisterBinding.mbtnSend.also { materialButton ->
            materialButton.setOnClickListener {
                sendVerify(materialButton)
            }
        }

        activityRegisterBinding.ibtnRegister.also {
            it.setOnClickListener {
                confirmVerify()
            }
        }

    }

    private fun sendVerify(btnSend:MaterialButton){
        val userPhone : String? = registerViewModel.getUserPhone()

        til_phone.isErrorEnabled = false

        if( userPhone != null && validatePhone(userPhone)){
            countTime(btnSend)
            toast("发送成功")
            registerViewModel.sendVerify(userPhone)
        }
    }

    private fun confirmVerify(){
        val userAccount : String? = registerViewModel.getUserAccount()
        val userPhone : String? = registerViewModel.getUserPhone()
        val userPassword : String? = registerViewModel.getUserPassword()
        val userVerify : String? = registerViewModel.getUserVerify()

        til_account.isErrorEnabled = false
        til_password.isErrorEnabled = false
        til_account.isErrorEnabled = false
        til_verify.isErrorEnabled = false


        if( userAccount != null && userPassword != null && userPhone != null &&  userVerify != null &&
            validateAccount(userAccount) && validatePassword(userPassword) &&validatePhone(userPhone) &&  validateVerify(userVerify) ){
            registerViewModel.confirmVerify(userAccount,userPassword,userPassword,userVerify)
        }


    }

    /**
     * 点击后进行六十秒倒计时并不可点击
     */
    @SuppressLint("SetTextI18n")
    fun countTime(materialButton: MaterialButton ){
        ValueAnimator.ofInt(60,0).also { valueAnimator ->
            valueAnimator.duration = 600000L
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Int
                materialButton.text = "$value s"
                materialButton.setTextColor(Color.GRAY)
                materialButton.isClickable = false
                if(value == 0){
                    materialButton.text = "SEND"
                    materialButton.setTextColor(Color.BLACK)
                    materialButton.isClickable = true
                }
            }
        }.start()
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

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    private fun validatePhone(phone: String): Boolean {
        if (phone.isEmpty()){
            showError(til_phone, "手机号不能为空")
            return false
        }else if (phone.length != 11) {
            showError(til_phone, "手机必须为11位号码")
            return false
        }
        return true
    }

    /**
     * 验证验证码
     * @param verify
     * @return
     */
    private fun validateVerify(verify: String): Boolean {
        if (verify.isEmpty()) {
            showError(til_verify, "验证码不能为空")
            return false
        }else if (verify.length != 6) {
            showError(til_verify, "验证码必须是六位")
            return false
        }
        return true
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

}