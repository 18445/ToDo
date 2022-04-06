package com.example.todo.extension

import androidx.lifecycle.Observer
import com.example.todo.httpUtils.*

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.extension
 * @ClassName:      IStateObserver
 * @Author:         Yan
 * @CreateDate:     2022年04月06日 16:32:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
abstract class IStateObserver <T> : Observer<ApiResponse<T>> {
    override fun onChanged(apiResponse: ApiResponse<T>?) {
        when (apiResponse) {
            is ApiSuccessResponse -> onSuccess(apiResponse.response)
            is ApiEmptyResponse -> onDataEmpty()
            is ApiFailedResponse -> onFailed(apiResponse.errorCode, apiResponse.errorMsg)
            is ApiErrorResponse -> onError(apiResponse.throwable)
        }
        onComplete()
    }

    abstract fun onSuccess(data: T)

    abstract fun onDataEmpty()

    abstract fun onError(e: Throwable)

    abstract fun onFailed(errorCode: Int?, errorMsg: String?)

    abstract fun onComplete()
}