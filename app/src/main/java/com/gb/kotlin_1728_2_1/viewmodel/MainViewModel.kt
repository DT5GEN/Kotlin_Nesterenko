package com.gb.kotlin_1728_2_1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.kotlin_1728_2_1.repository.RepositoryLocalImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData()

) :
    ViewModel() {
    private val repositoryLocalImpl: RepositoryLocalImpl by lazy { RepositoryLocalImpl() }

    fun getLivedata() = liveData


    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalServer(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalServer(
        isRussian =
        false
    )

    fun getWeatherFromLocalServer(isRussian: Boolean) {
        liveData.postValue(AppState.Loading(0))
        Thread {
            sleep(1000)
            val rand = (1..40).random()
            liveData.postValue(
                AppState.Success(
                    with(repositoryLocalImpl) {
                        if (isRussian) {
                            getWeatherFromLocalStorageRus()
                        } else {
                            getWeatherFromLocalStorageWorld()
                        }
                    }
                )
            )
        }.start()
    }
}