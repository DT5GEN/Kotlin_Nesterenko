package com.gb.kotlin_1728_2_1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.kotlin_1728_2_1.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData()

) :
    ViewModel() {
    private val repositoryImpl: RepositoryImpl by lazy { RepositoryImpl() }

    fun getLivedata() = liveData


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

                liveData.postValue(
                    AppState.Success(
                        with(repositoryImpl)
                        {
                            if (isRussian) getWeatherFromLocalStorageRus()
                            else getWeatherFromLocalStorageWorld()
                        }
                    )
                )
            // liveData.postValue(AppState.Error(IllegalStateException("m( -__-)m")))
            // liveData.value(AppState.SUCCESS)                                        // асинхронный с главным потоком запрос
            // liveData.postValue(AppState.Success(" Холодно " , " Very cold " ))      // синхронный с главным потоком запрос
        }.start()
    }
}