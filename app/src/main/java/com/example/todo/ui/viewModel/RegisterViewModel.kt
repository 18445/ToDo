package com.example.todo.ui.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.model.UserLoginModel
import com.example.todo.extension.StateLiveData
import com.example.todo.httpUtils.Verify
import com.example.todo.ui.repository.LoginRepository
import kotlinx.coroutines.launch

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.viewModel
 * @ClassName:      RegisterViewModel
 * @Author:         Yan
 * @CreateDate:     2022年04月09日 23:26:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    //注册的Viewmodel
 */
class RegisterViewModel : ViewModel(){

    val verifyInfo = StateLiveData<Verify>()
    val registerInfo = StateLiveData<Any>()

    private val loginModelObservable : ObservableField<UserLoginModel> by lazy {
        val observable = ObservableField<UserLoginModel>()
        observable.set(UserLoginModel("","","",""))
        observable
    }

    //双向绑定确定用户登录信息

    fun getUserAccount() : String? {
        return loginModelObservable.get()?.account
    }

    fun setUserAccount(value : String){
        loginModelObservable.get()?.account = value
    }

    fun getUserPassword() : String? {
        return loginModelObservable.get()?.password
    }

    fun setUserPassword(value : String){
        loginModelObservable.get()?.password = value
    }

    fun getUserPhone() : String? {
        return loginModelObservable.get()?.phone
    }

    fun setUserPhone(value : String){
        loginModelObservable.get()?.phone = value
    }

    fun getUserVerify() : String? {
        return loginModelObservable.get()?.verify
    }

    fun setUserVerify(value : String){
        loginModelObservable.get()?.verify = value
    }

    private val loginRepository by lazy {
        LoginRepository()
    }

    fun sendVerify(phone : String){
        viewModelScope.launch {
            verifyInfo.value = loginRepository.sentVerify(phone)
        }
    }

    fun confirmVerify(username : String ,password : String ,phone:String ,verifyCode:String){
        viewModelScope.launch {
            registerInfo.value = loginRepository.confirmVerify(username,password,phone,verifyCode)
        }
    }
}