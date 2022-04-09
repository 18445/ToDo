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
     * 发送验证码(注册)
     */
    @FormUrlEncoded
    @POST("/verify/sms/register")
    suspend fun sentRegisterVerify(@Field("phone") phone : String) : ApiResponse<Verify>

    /**
     * 密码登录
     */
    @FormUrlEncoded
    @POST("/user/login/pwd")
    suspend fun loginIn(@Field("account") account : String,@Field("password")password : String) : ApiResponse<UserToken>

    /**
     * 注册账号
     */
    @FormUrlEncoded
    @POST("/verify/sms/register")
    suspend fun register(@Field("username") username : String,@Field("password")password : String,@Field("phone")phone:String,@Field("verify_code")verifyCode:String) : ApiResponse<Any>


}