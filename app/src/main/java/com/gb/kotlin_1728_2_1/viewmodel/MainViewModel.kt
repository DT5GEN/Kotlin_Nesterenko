package com.gb.kotlin_1728_2_1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel() {
    fun getLivedata(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherFromServer() {
        liveData.postValue(AppState.Loading(2))
        Thread {

            sleep(2000)
            val rand = (1 .. 20).random()
            if (rand > 10){
                liveData.postValue(AppState.Success(" Норм " , " Тепло " ))
            } else {
                liveData.postValue(AppState.Success(" Холодно " , " " ))
            }
            // liveData.value(AppState.SUCCESS)      // асинхронный с главным потоком запрос
                // liveData.postValue(AppState.Success(" Холодно " , " Very cold " ))      // синхронный с главным потоком запрос
        }.start()
    }
}