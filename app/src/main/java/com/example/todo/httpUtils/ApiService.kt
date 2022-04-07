package com.example.todo.httpUtils

import retrofit2.http.*

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.httpUtils
 * @ClassName:      ApiService
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 09:11:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     网络请求API
 */
interface ApiService {

    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("/verify/sms/register")
    suspend fun sentVerify(@Field("phone") phone : String) : ApiResponse<List<UserInfoBody>>



}