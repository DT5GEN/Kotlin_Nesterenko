package com.gb.kotlin_1728_2_1.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.FragmentHistoryBinding
import com.gb.kotlin_1728_2_1.model.Weather
import com.gb.kotlin_1728_2_1.view.main.OnMyItemClickListener
import com.gb.kotlin_1728_2_1.viewmodel.AppState
import com.gb.kotlin_1728_2_1.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment(), OnMyItemClickListener {

    var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() {
            return _binding!!
        }

    private val adapter: CitiesHistoryAdapter by lazy {
        // эффективность достигается, что пока не придёт Success с сервера, адаптер не будет связываться никаким образом
        CitiesHistoryAdapter(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getLivedata().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getAllHistory()
        binding.historyFragmentRecyclerview.adapter = adapter
    }


    private fun renderData(appState: AppState) {
        with(binding)
        {
            when (appState) {
                is AppState.Error -> {
                    // TODO HW
                }
                is AppState.Loading -> {}
                is AppState.Success -> {
                    adapter.setWeather(appState.weatherData)
                }
            }
        }
    }

    fun View.errorHandling(text: String, length: Int) {
        Snackbar.make(this, text, length)
            .setAction(context.getString(R.string.repeat_request)) {
            }.show()
    }

    private fun View.withoutAction(text: String, length: Int) {
        Snackbar.make(this, text, length).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    override fun onItemClick(weather: Weather) {
        TODO("Not yet implemented") // можно что-то добавить своё
    }

}
