package com.example.sockettest.widget

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.sockettest.extensions.releaseAdapterOnDestroy


class ChatBotRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), DefaultLifecycleObserver {

    fun setAdapter(adapter: Adapter<*>?, fragment: Fragment) {
        setAdapter(adapter)
        fragment.releaseAdapterOnDestroy(this)
    }

    /**
     * Clearing the adapter because it leaks after fragment's onDestroyView lifecycle.
     */
    override fun onDestroy(owner: LifecycleOwner) {
        adapter = null
    }
}