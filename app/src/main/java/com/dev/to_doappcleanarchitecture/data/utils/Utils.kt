package com.dev.to_doappcleanarchitecture.data.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusView = activity.currentFocus
    currentFocusView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusView.windowToken, HIDE_NOT_ALWAYS
        )
    }
}

fun <T> LiveData<T>.observerOnce(
    lifecycleOwner: LifecycleOwner,
    observer: Observer<T>
) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}