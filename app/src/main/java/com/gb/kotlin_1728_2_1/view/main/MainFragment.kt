package com.gb.kotlin_1728_2_1.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.FragmentMainBinding
import com.gb.kotlin_1728_2_1.model.Weather
import com.gb.kotlin_1728_2_1.view.details.BUNDLE_KEY
import com.gb.kotlin_1728_2_1.view.details.DetailsFragment
import com.gb.kotlin_1728_2_1.viewmodel.AppState
import com.gb.kotlin_1728_2_1.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnMyItemClickListener {

    var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val adapter: MainFragmentAdapter by lazy {
        // эффективность достигается, что пока не придёт Success с сервера, адаптер не будет связываться никаким образом
        MainFragmentAdapter(this)
    }
    private var isRussian = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initView()
        viewModel.getLivedata().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun initView() {
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            /* ниже сидит viewModel в ней сидит liveData, на эту liveData мы подписываемся.
             Лайвдейте нужно знать жизненный цикл фрагмента через viewLifecycleOwner.
             viewLifecycleOwner встроенный во фрагмент и а Активити он тоже встроен.
             observe должен знать куда ему сообщать об изменениях ( тут сообщает в renderData)
             renderData реализацию мы прописывает (рутина) */
            mainFragmentFAB.setOnClickListener {
                sentRequest()
            }
        }
    }

    private fun sentRequest() {
        isRussian = !isRussian // isRussian = if(isRussian) false else true
        with(binding) {
            if (isRussian) {
                viewModel.getWeatherFromLocalSourceRus()
                mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                viewModel.getWeatherFromLocalSourceWorld()
                mainFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
    }

    private fun renderData(appState: AppState) {
        with(binding)
        {
            when (appState) {
                is AppState.Error -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    root.errorHandling(getString(R.string.errors),Snackbar.LENGTH_LONG)
                }

                is AppState.Loading -> {
                    mainFragmentLoadingLayout.visibility = View.VISIBLE
                }

                is AppState.Success -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    adapter.setWeather(appState.weatherData)
                    root.withoutAction(getString(R.string.success), Snackbar.LENGTH_SHORT)
                }
            }
        }
    }

    private fun View.errorHandling(text:String, length: Int){
        Snackbar.make(this,text, length )
            .setAction(context.getString(R.string.repeat_request)) {
                sentRequest()
            }.show()
    }

    private fun View.withoutAction(text:String, length: Int){
        Snackbar.make(this,text, length ).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onItemClick(weather: Weather) {
        activity?.run {
            // создаём контейнер, в который в котором будут данные передаваться и в него помещаем
            // погоду по ключу
            supportFragmentManager.beginTransaction()
                .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                    putParcelable(BUNDLE_KEY, weather)
                }))
                .addToBackStack("")
                .commit()
        }
    }
}
