package com.example.todo.httpUtils

import retrofit2.http.GET
import retrofit2.http.Path

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
     * 获取轮播图
     */
    @GET("/api/verify/sms/{phoneNumber}")
    suspend fun login(@Path("phoneNumber") phone : String) : ApiResponse<List<UserInfoBody>>



}