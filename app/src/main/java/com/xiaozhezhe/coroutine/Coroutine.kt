package com.xiaozhezhe.coroutine

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine {
    companion object {
        const val TAG = "Coroutine"
    }

    init {
        whichThread()
        testRunBlocking()
    }

    private fun whichThread() {
        repeat(3) {
            GlobalScope.launch {
                Log.d(TAG, "Hi from ${Thread.currentThread()}")
            }
        }
    }

    private fun testRunBlocking() {
        Log.d(TAG, "start")
        runBlocking {
            // runBlocking会阻塞线程，只有当runBlocking内部相同作⽤域的所有协程都运⾏结束后，才会执行runBlocking后面的逻辑
            launch {
                delay(200)
                Log.d(TAG, "runBlocking launch 11")
            }

            launch {
                delay(100)
                Log.d(TAG, "runBlocking launch 22")
            }

            // 下面的GlobalScope已经开辟了一个新的作用域，其内部的协程已经不再属于runBlocking作用域，GlobalScope不会阻塞当前线程
            GlobalScope.launch {
                launch {
                    delay(300)
                    Log.d(TAG, "GlobalScope launch 1")
                }

                launch {
                    delay(400)
                    Log.d(TAG, "GlobalScope launch 2")
                }
            }
        }
        Log.d(TAG, "end")
    }
}