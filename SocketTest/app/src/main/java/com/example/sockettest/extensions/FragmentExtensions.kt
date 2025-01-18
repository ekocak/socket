package com.example.sockettest.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData


fun <T> Fragment.observe(
    liveData: LiveData<T>,
    observer: (t: T) -> Unit
) {
    liveData.observe(this) { observer(it) }
}

fun <T> Fragment.observeNonNull(
    liveData: LiveData<T>,
    observer: (t: T) -> Unit
) {
    liveData.observe(this) { it?.let(observer) }
}

fun <T> AppCompatActivity.observeNonNull(
    liveData: LiveData<T>,
    observer: (t: T) -> Unit
) {
    liveData.observe(this) { it?.let(observer) }
}