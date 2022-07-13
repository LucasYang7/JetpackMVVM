package com.xiaozhezhe.jetpackmvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.xiaozhezhe.jetpackmvvm.databinding.ActivityMainBinding
import com.xiaozhezhe.jetpackmvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        const val URL = "https://www.baidu.com"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        initView()
        subscribeData()
    }

    private fun initView() {
        binding.tvGetRequest.setOnClickListener {
            viewModel.fetchData(URL)
        }
    }

    private fun subscribeData() {
        viewModel.reponse.observe(this) {
            binding.tvResult.text = it
        }
    }
}