package com.example.todo.ui.fragment.login

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.activityViewModels
import com.example.todo.base.BaseFragment
import com.example.todo.databinding.FragmentLoginPhoneBinding
import com.example.todo.ui.viewModel.LoginViewModel
import com.example.todo.utils.toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login_phone.*


/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.fragment
 * @ClassName:      LoginWithPhoneFragment
 * @Author:         Yan
 * @CreateDate:     2022年04月08日 00:55:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     手机登录界面的Fragment
 */
class LoginWithPhoneFragment : BaseFragment() {

    //时间
    private val mOneMinute = 60000L

    private lateinit var loginPhoneBinding: FragmentLoginPhoneBinding
    private val mLoginViewModel: LoginViewModel by activityViewModels()

    override fun initData() {
        loginPhoneBinding.loginViewModel = mLoginViewModel

        loginPhoneBinding.mbtnSend.also {
            it.setOnClickListener {
                sendVerify()
            }
        }
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

    /**
     * 点击后进行六十秒倒计时并不可点击
     */
    @SuppressLint("SetTextI18n")
    fun countTime(materialButton: MaterialButton ){
        ValueAnimator.ofInt(60,0).also { valueAnimator ->
            valueAnimator.duration = mOneMinute
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


    /**
     * 向用户发送验证码
     */
    private fun sendVerify(){
        val userPhone : String? = mLoginViewModel.getUserPhone()

        if(checkPhoneIfValidate() && userPhone != null){
            countTime(loginPhoneBinding.mbtnSend)
            toast("发送成功")
//            mLoginViewModel.sendVerify(userPhone)
            //TODO
        }
    }

    /**
     * 验证手机是否合法
     */
    private fun checkPhoneIfValidate() : Boolean{

        val phone = til_phone.editText!!.text.toString()

        til_phone.isErrorEnabled = false


        return validatePhone(phone)
    }

    /**
     * 验证验证码和手机是否合法
     */
    fun checkVerify() : Boolean{
        val phone = til_verify.editText!!.text.toString()
        val verify = til_phone.editText!!.text.toString()

        til_phone.isErrorEnabled = false
        til_verify.isErrorEnabled = false

        return validatePhone(phone) && validateVerify(verify)
    }
}