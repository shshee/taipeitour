package com.tangerine.core.ultis

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.SystemClock
import android.text.Html
import android.view.View

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

fun String.fromHtml() = Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)

class OnSingleClickListener(
    private val timeDisableInMillisecond: Int,
    private val block: () -> Unit
) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < timeDisableInMillisecond) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()

        block()
    }
}

fun View.setOnSingleClickListener(timeDelayInSecond: Int = 500, block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(timeDelayInSecond, block))
}