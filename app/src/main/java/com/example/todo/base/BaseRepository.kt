package com.example.todo.base

import com.example.todo.BuildConfig
import com.example.todo.httpUtils.*

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.base
 * @ClassName:      BaseRepository
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 10:42:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
open class BaseRepository {

    suspend fun <T> executeHttp(block : suspend()->ApiResponse<T>):ApiResponse<T>{
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {   data: ApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure {
            return handleHttpError(it)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleHttpError(e: Throwable): ApiErrorResponse<T> {
        if (BuildConfig.DEBUG) e.printStackTrace()
        handlingExceptions(e)
        return ApiErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            handlingApiExceptions(data.code, data.msg)
            ApiFailedResponse(data.code, data.msg)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        return if (response.data == null || response.data is List<*> && (response.data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(response.data!!)
        }
    }


}