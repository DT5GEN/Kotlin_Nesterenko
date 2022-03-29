package com.gb.kotlin_1728_2_1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.kotlin_1728_2_1.repository.RepositoryLocalImpl

class HistoryViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData()

) :
    ViewModel() {
    private val repositoryLocalImpl: RepositoryLocalImpl by lazy { RepositoryLocalImpl() }

    fun getLivedata() = liveData

    fun getAllHistory() {
        Thread{
            val listWeather = repositoryLocalImpl.getAllHistoryWeather()
            liveData.postValue(AppState.Success(listWeather))
        }.start()

    }
}