package com.xiaozhezhe.jetpackmvvm.data

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainRepository {
    companion object {
        const val REQUEST_SUCCESS = "request success"
        const val REQUEST_FAILED = "request failed"
    }

    suspend fun fetchData(url: String, callback: (String) -> Unit) = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url(url)
                .build()
            val client = OkHttpClient()
                .newBuilder()
                .build()
            client.newCall(request).execute().run {
                val responseString = if (isSuccessful) {
                    body?.string() ?: REQUEST_SUCCESS
                } else {
                    REQUEST_FAILED
                }
                Handler(Looper.getMainLooper()).post {
                    callback.invoke(responseString)
                }
            }
        } catch (e: Exception) {
            Handler(Looper.getMainLooper()).post {
                callback.invoke(e.toString())
            }
            e.printStackTrace()
        }
    }
}
