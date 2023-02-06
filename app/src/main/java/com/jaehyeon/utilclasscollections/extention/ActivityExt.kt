package com.jaehyeon.utilclasscollections.extention

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> ComponentActivity.collectLatestLifecycleFlow(lifecycle: Lifecycle.State = Lifecycle.State.STARTED, flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycle) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> ComponentActivity.collectLifecycleFlow(lifecycle: Lifecycle.State = Lifecycle.State.STARTED, flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycle) {
            flow.collect(collect)
        }
    }
}
