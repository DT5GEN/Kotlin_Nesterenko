package com.gb.kotlin_1728_2_1.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.kotlin_1728_2_1.databinding.FragmentDetailsBinding
import com.gb.kotlin_1728_2_1.model.Weather
import com.gb.kotlin_1728_2_1.viewmodel.MainViewModel

const val BUNDLE_KEY = "BUNDLE_KEY"

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val weather = arguments?.getParcelable<Weather>(BUNDLE_KEY)
//        if (weather != null) {      // первый вариант записи, который позволяет понять новичок ты или опытный
//            setWeatherData(weather)
//        }
        arguments?.let {
            it.getParcelable<Weather>(BUNDLE_KEY)?.run { setWeatherData(this) }
        }

    }

//    private fun setWeatherData(weather: Weather) {
//        with(binding) {
//            cityName.text = weather.city.name
//            cityCoordinates.text = " ${weather.city.lat}  ${weather.city.lon}"
//            temperatureValue.text = "${weather.temperature}"
//            feelsLikeValue.text = "${weather.feelsLike}"
//        }
//    }

    // можно ещё оптимизировыть функцию setWeatherData , но наглядность теряется

    private fun setWeatherData(weather: Weather) {
        with(binding) {
            with(weather) {
                cityName.text = city.name
                cityCoordinates.text = " ${city.lat}  ${city.lon}"
                temperatureValue.text = "$temperature"
                feelsLikeValue.text = "$feelsLike"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    companion object {
//
//        // @JvmStatic
//        fun newInstance(bundle: Bundle): DetailsFragment { // в этом контейнере (бандле) будет сидеть Погода
//            val fragment = DetailsFragment() // внутрь помещаем бандл
//            fragment.arguments = bundle
//            return fragment
//
//        }
//    }


    companion object {

        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
        /*
         создали фрагмент, получили его как ресивер ( через apply его получили )
         { this.arguments = bundle } - this можно опустить
        * */
    }

}

