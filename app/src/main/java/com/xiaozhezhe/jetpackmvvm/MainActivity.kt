package com.xiaozhezhe.jetpackmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaozhezhe.coroutine.Coroutine

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Coroutine()
    }
}