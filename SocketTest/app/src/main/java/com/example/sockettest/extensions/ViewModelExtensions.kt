package com.example.sockettest.extensions

import androidx.lifecycle.MutableLiveData
import com.example.sockettest.common.Resource
import com.example.sockettest.ui.base.BaseViewModel

fun <T> BaseViewModel.checkResult(
    resource: Resource<T>,
    responseLiveData: MutableLiveData<T>? = null,
    onFailure: ((Resource.Failure<T>) -> Unit)? = null,
    onWarning: ((Resource.Warning<T>) -> Unit)? = null,
    onSuccess: (T) -> Unit,
) {
    when (resource) {
        is Resource.Success -> {

            onSuccess(resource.data)
            responseLiveData?.value = resource.data
        }

        is Resource.Failure -> {
            onFailure?.invoke(resource)
        }

        is Resource.Warning -> {
            onWarning?.invoke(resource)
        }
    }
}

