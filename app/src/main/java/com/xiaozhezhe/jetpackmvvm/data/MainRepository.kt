package com.xiaozhezhe.jetpackmvvm.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException

class MainRepository {
    suspend fun fetchData(url: String, callback: (String) -> Unit) = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(url)
            .build()
        val client = OkHttpClient()
            .newBuilder()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response.body?.byteString().toString())
            }

        })
    }
}