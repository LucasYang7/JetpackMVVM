package com.xiaozhezhe.jetpackmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaozhezhe.jetpackmvvm.data.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var mainRepository = MainRepository()
    var reponse = MutableLiveData<String>()

    fun fetchData(url: String) {
        viewModelScope.launch {
            mainRepository.fetchData(url) { result ->
                reponse.postValue(result)
            }
        }
    }
}