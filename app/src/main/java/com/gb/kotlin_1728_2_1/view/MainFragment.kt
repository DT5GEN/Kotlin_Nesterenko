package com.gb.kotlin_1728_2_1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.kotlin_1728_2_1.databinding.FragmentMainBinding
import com.gb.kotlin_1728_2_1.viewmodel.AppState
import com.gb.kotlin_1728_2_1.viewmodel.MainViewModel
import com.gb.kotlin_1728_2_1.viewmodel.newErrors
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLivedata().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getWeatherFromServer()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {

                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, "Error", Snackbar.LENGTH_LONG)
                    .setAction("Повторить запрос?") {
                        viewModel.getWeatherFromServer()
                    }.show()

            }

            is AppState.Loading -> {

                binding.loadingLayout.visibility = View.VISIBLE


            }

//            Toast.makeText(
//                requireContext(),
//                "${appState.progress}",
//                Toast.LENGTH_SHORT
//            ).show()
//


            is AppState.Success -> {

                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.mainView,
                    "${appState.weatherData.feelsLike}", Snackbar.LENGTH_LONG
                ).show()
            }
            is newErrors -> {

                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, "New Error", Snackbar.LENGTH_LONG).show()
            }
        }
        // Toast.makeText(requireContext(), " < ВЫПОЛНЕНО! >  ", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
