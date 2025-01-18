package com.example.sockettest.extensions

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.sockettest.widget.ChatBotRecyclerView

fun RecyclerView.smoothScroll(toPos: Int, duration: Int = 500, onFinish: () -> Unit = {}) {
    try {
        val smoothScroller: RecyclerView.SmoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return duration
            }

            override fun onStop() {
                super.onStop()
                onFinish.invoke()
            }
        }
        smoothScroller.targetPosition = toPos
        layoutManager?.startSmoothScroll(smoothScroller)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun RecyclerView?.getCurrentPosition(): Int? {
    return (this?.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
}

fun Fragment.releaseAdapterOnDestroy(recyclerView: ChatBotRecyclerView) {
    viewLifecycleOwner.lifecycle.addObserver(recyclerView)
}