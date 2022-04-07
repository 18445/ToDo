package com.example.todo.httpUtils

import com.example.todo.utils.toast
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.httpUtils
 * @ClassName:      HttpError
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 16:59:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     错误处理
 */

enum class HttpError(var code: Int, var errorMsg: String) {
    TOKEN_EXPIRE(3001, "token is expired"),
    PARAMS_ERROR(4003, "params is error")
    // ...... more
}

internal fun handlingApiExceptions(code: Int?, errorMsg: String?) = when (code) {
    HttpError.TOKEN_EXPIRE.code -> toast(HttpError.TOKEN_EXPIRE.errorMsg)
    HttpError.PARAMS_ERROR.code -> toast(HttpError.PARAMS_ERROR.errorMsg)
    else -> errorMsg?.let { toast(it) }
}

internal fun handlingExceptions(e: Throwable) = when (e) {
    is HttpException -> toast(e.message())

    is CancellationException -> {

    }

    is SocketTimeoutException -> {
    }

    is JsonParseException -> {
    }

    else -> {
    }
}