package com.tangerine.core.ultis

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.SystemClock
import android.text.Html
import android.text.Spanned
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@SuppressLint("MissingPermission")
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

fun String.fromHtml(): Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)

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

fun toJson(obj: Any?): String {
    return Gson().toJson(obj)
}

fun <T> mapToObject(any: Any?, type: Class<T>): T? {
    if (any == null) return null
    val map = any as Map<String, Any?>

    val gson = Gson()
    val json = gson.toJson(map)
    return gson.fromJson(json, type)
}

inline fun <reified T> toObject(str: String?): T? {
    return Gson().fromJson(str, T::class.java)
}

/**
 *  Convert str to list Item
 *  Ex: GSonUtils.toList<List<DATACLASS>>(str)
 */
inline fun <reified T> toList(str: String?): T? {
    if (str == null) return null
    return Gson().fromJson(str, object : TypeToken<T>() {}.type)
}