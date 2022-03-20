package com.gb.kotlin_1728_2_1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.kotlin_1728_2_1.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {
    fun getLivedata(): LiveData<AppState> {
        return liveData
    }


    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalServer(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalServer(
        isRussian =
        false
    )

    fun getWeatherFromRemoteSource() =
        getWeatherFromLocalServer(isRussian = true) //заглушка на 5й урок

    fun getWeatherFromLocalServer(isRussian: Boolean) {
        liveData.postValue(AppState.Loading(50))
        Thread {

            sleep(2000)

          //  val rand = (1..20).random()
            if (true) {
                liveData.postValue(
                    AppState.Success(
                        if (isRussian) repositoryImpl.getWeatherFromLocalStorageRus()
                        else repositoryImpl.getWeatherFromLocalStorageWorld()
                    )
                )
            } else {
               // liveData.postValue(AppState.Error(IllegalStateException("m( -__-)m")))
            }
            // liveData.value(AppState.SUCCESS)                                        // асинхронный с главным потоком запрос
            // liveData.postValue(AppState.Success(" Холодно " , " Very cold " ))      // синхронный с главным потоком запрос
        }.start()
    }


}