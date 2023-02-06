package com.jaehyeon.utilclasscollections.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaehyeon.utilclasscollections.R
import com.jaehyeon.utilclasscollections.utils.DefaultException
import com.jaehyeon.utilclasscollections.utils.ErrorEvent
import com.jaehyeon.utilclasscollections.utils.Logg
import com.jaehyeon.utilclasscollections.utils.UiText
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException

/**
 * Created by Jaehyeon on 2023/02/06.
 */
abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableSharedFlow<ErrorEvent?>()
    val error = _error.asSharedFlow()

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Logg.e("Coroutine Exception Handler ${javaClass.simpleName}\ncaused by = ${throwable.message}")
        setLoadingState(false)
        when (throwable) {
            is IOException -> {
                _error.tryEmit(
                    ErrorEvent.ShowToastError(
                        UiText.StringResource(
                            0,
                            emptyList()
                        )
                    )
                )
            }

            is TimeoutCancellationException -> {
                _error.tryEmit(
                    ErrorEvent.ShowToastError(
                        UiText.StringResource(
                            0,
                            emptyList()
                        )
                    )
                )
            }

            is DefaultException.NotFoundError-> {
                _error.tryEmit(
                    ErrorEvent.ShowToastError(
                        UiText.StringResource(
                            R.string.app_name,
                            emptyList()
                        )
                    )
                )
            }

            is DefaultException.UnAuthorizationError -> {
                _error.tryEmit(
                    ErrorEvent.ShowToastError(
                        UiText.StringResource(
                            0,
                            emptyList()
                        )
                    )
                )
            }

            else -> {

            }
        }
    }

    protected val mainScope = CoroutineScope(Dispatchers.Main + job + exceptionHandler)
    protected val ioScope = CoroutineScope(Dispatchers.IO + job + exceptionHandler)
    protected val modelScope = viewModelScope + job + exceptionHandler

    fun setLoadingState(value: Boolean) {
        _loading.value = value
    }

    fun sendError(event: ErrorEvent) {
        _error.tryEmit(event)
    }

    override fun onCleared() {
        super.onCleared()
        if (!job.isCancelled || !job.isCompleted) job.cancel()
    }
}