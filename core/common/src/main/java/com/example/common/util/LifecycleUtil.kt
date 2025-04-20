package com.example.common.util

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun ComposableLifecycle(
    lifecycle: Lifecycle? = (LocalActivity.current as? ComponentActivity)?.lifecycle,
    onStart: ((LifecycleOwner) -> Unit)? = null,
    onStop: ((LifecycleOwner) -> Unit)? = null,
) {
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onStart?.invoke(source)
                Lifecycle.Event.ON_STOP -> onStop?.invoke(source)
                else -> {}
            }
        }

        lifecycle?.addObserver(observer)

        onDispose {
            lifecycle?.removeObserver(observer)
        }
    }
}