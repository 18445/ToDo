package com.example.todo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.extension.StateLiveData
import com.example.todo.httpUtils.UserInfoBody
import com.example.todo.ui.repository.LoginRepository
import kotlinx.coroutines.launch

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.viewModel
 * @ClassName:      RegisterViewModel
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 10:27:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     注册界面ViewModel
 */
class RegisterViewModel : ViewModel(){

    val userInfo = StateLiveData<List<UserInfoBody>>()

    private val registerRepository by lazy {
        LoginRepository()
    }

    fun sendVerify(phone : String){
        viewModelScope.launch {
            registerRepository.sentVerify(phone)
        }
    }



}