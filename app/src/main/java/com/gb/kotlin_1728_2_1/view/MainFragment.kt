package com.gb.kotlin_1728_2_1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.kotlin_1728_2_1.databinding.FragmentMainBinding
import com.gb.kotlin_1728_2_1.viewmodel.AppState
import com.gb.kotlin_1728_2_1.viewmodel.MainViewModel
import com.gb.kotlin_1728_2_1.viewmodel.newErrors

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

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> Toast.makeText(
                requireContext(),
                appState.error.message,
                Toast.LENGTH_SHORT
            ).show()

            is AppState.Loading -> Toast.makeText(
                requireContext(),
                "${appState.progress}",
                Toast.LENGTH_SHORT
            ).show()

            is AppState.Success -> Toast.makeText(
                requireContext(),
                appState.weatherData,
                Toast.LENGTH_SHORT
            ).show()
            is newErrors -> Toast.makeText(
                requireContext(),
                appState.error.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        Toast.makeText(requireContext(), " < ВЫПОЛНЕНО! >  ", Toast.LENGTH_SHORT).show()
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
