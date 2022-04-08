package com.example.todo.ui.fragment

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
import com.google.android.material.button.MaterialButton

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

    //时间
    private val mOneMinute = 60000L

    private lateinit var loginPhoneBinding: FragmentLoginPhoneBinding
    private val mLoginViewModel: LoginViewModel by activityViewModels()

    override fun initData() {
        loginPhoneBinding.loginViewModel = mLoginViewModel

        loginPhoneBinding.mbtnSend.also {
            it.setOnClickListener { _ ->
                countTime(it)
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

}