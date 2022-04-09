package com.example.todo.ui.repository

import com.example.todo.base.BaseRepository
import com.example.todo.httpUtils.*

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.repository
 * @ClassName:      RegisterRepository
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 09:50:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     注册登录的Repository
 */


class LoginRepository : BaseRepository(){

    private val mApiService = RetrofitClient.service

    /**
     * 账户 密码登录
     */
    suspend fun loginIn(account : String,password : String) : ApiResponse<UserToken>{
        return executeHttp {
            mApiService.loginIn(account,password)
        }
    }

    /**
     * 注册发送验证码
     */
    suspend fun sentVerify(phone : String) : ApiResponse<Verify>{
        return executeHttp {
            mApiService.sentRegisterVerify(phone)
        }
    }

    /**
     * 验证验证码
     */
    suspend fun confirmVerify(username : String ,password : String ,phone:String ,verifyCode:String) : ApiResponse<Any>{
        return executeHttp {
            mApiService.register(username,password,phone,verifyCode)
        }
    }

}