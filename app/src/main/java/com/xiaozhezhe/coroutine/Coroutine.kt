package com.xiaozhezhe.coroutine

import android.util.Log
import kotlinx.coroutines.*

class Coroutine {
    companion object {
        const val TAG = "Coroutine"
    }

    init {
        whichThread()
        testRunBlocking()
        fetchTwoData()
        fetchTwoDataWaitAll()
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

    private suspend fun fetchData(data: Long): Long {
        var result = 0L
        runBlocking {
            launch {
                delay(data)
                result = data
            }
        }
        return result
    }

    private fun fetchTwoData() {
        GlobalScope.launch {
            coroutineScope {
                // 实现类似RxJava zip操作
                Log.d(TAG, "fetchTwoData start")
                val deferredOne = async { fetchData(100) }
                val deferredTwo = async { fetchData(200) }
                Log.d(TAG, "fetchTwoData fetchData start")
                val result1 = deferredOne.await()
                Log.d(TAG, "fetchTwoData fetchData end 1")
                val result2 = deferredTwo.await()
                Log.d(TAG, "fetchTwoData fetchData end 2")
                Log.d(TAG, "fetchTwoData result is " + (result1 + result2))
            }
        }
    }

    private fun fetchTwoDataWaitAll() {
        GlobalScope.launch {
            coroutineScope {
                Log.d(TAG, "fetchTwoDataWaitAll start")
                var result = 0L
                // fetch two data at the same time
                val deferredList =
                    listOf(async { fetchData(100) }, async { fetchData(200) })
                // use awaitAll to wait for both fetch operation finish
                val resultList = deferredList.awaitAll()
                resultList.forEach {
                    result += it
                }
                Log.d(TAG, "fetchTwoDataWaitAll result is $result")
            }
        }
    }
}