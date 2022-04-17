package com.gb.kotlin_1728_2_1.view.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.FragmentMainBinding
import com.gb.kotlin_1728_2_1.model.City
import com.gb.kotlin_1728_2_1.model.Weather
import com.gb.kotlin_1728_2_1.model.utils.BUNDLE_KEY
import com.gb.kotlin_1728_2_1.view.details.DetailsFragment
import com.gb.kotlin_1728_2_1.viewmodel.AppState
import com.gb.kotlin_1728_2_1.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), OnMyItemClickListener {

    var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val adapter: CitiesAdapter by lazy {
        // эффективность достигается, что пока не придёт Success с сервера, адаптер не будет связываться никаким образом
        CitiesAdapter(this)
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
        mainFragmentFABLocation.setOnClickListener {
            checkPermission()
        }
    }


    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialogRationalisation()
                }
                else -> {
                    myRequestPermission()
                }
            }
        }
    }


    private val MIN_DISTANCE = 100f
    private val REFRESH_PERIOD = 60000L

    private val listenerLocation = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            getAddress(location)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

    }

    private fun showAddressDialog(address: String, location: Location) {
        address.let {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_rationale_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    toDetails(Weather(City(address, location.latitude, location.longitude)))
                }

                .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun getAddress(location: Location) {
        Log.d("loc", "getAddress() called with: location = $location")

        Thread {
            val geocoder =
                Geocoder(requireContext())  // Geocoder - библиотека/инструмент , который по координатам проверяет в базе список ближайших адресов
            val listAddress = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            requireActivity().runOnUiThread {
                showAddressDialog(listAddress[0].getAddressLine(0), location)
            }
        }.start()
    }

    public fun getLocation() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(  // обязательная проверка пермишинсов
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val providerGPS = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    providerGPS?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MIN_DISTANCE,
                            listenerLocation
                        )
                    }

                } else {
                    val lastLocation =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    lastLocation?.let {
                        getAddress(it)
                    }
                }
            }
        }

    }


    private fun showDialogLocation() {

    }

    val REQUEST_CODE = 999
    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialogRationalisation()
                }
                else -> {
                    Log.d(
                        "TAG",
                        "onRequestPermissionsResult() called with: requestCode = $requestCode, permissions = $permissions, grantResults = $grantResults"
                    )
                }
            }
        }

    }


    private fun showDialogRationalisation() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_rationale_title))
            .setMessage(getString(R.string.dialog_message_no_gps))
            .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { _, _ ->
                myRequestPermission()
            }
            .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
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
                    root.errorHandling(getString(R.string.errors), Snackbar.LENGTH_LONG)
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

    fun View.errorHandling(text: String, length: Int) {
        Snackbar.make(this, text, length)
            .setAction(context.getString(R.string.repeat_request)) {
                sentRequest()
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
        fun newInstance() = MainFragment()
    }

    override fun onItemClick(weather: Weather) {
        toDetails(weather)
    }

    fun toDetails(weather: Weather) {
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
