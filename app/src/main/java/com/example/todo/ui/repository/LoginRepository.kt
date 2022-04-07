package com.example.todo.ui.repository

import com.example.todo.base.BaseRepository
import com.example.todo.httpUtils.ApiResponse
import com.example.todo.httpUtils.RetrofitClient
import com.example.todo.httpUtils.UserInfoBody

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

    suspend fun sentVerify(phone : String) : ApiResponse<List<UserInfoBody>>{
        return executeHttp {
            mApiService.sentVerify(phone)
        }
    }

}