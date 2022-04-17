package com.gb.kotlin_1728_2_1.lesson10

import com.gb.kotlin_1728_2_1.model.Weather
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.fragment.app.Fragment
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.FragmentGoogleMapsMainBinding
import com.gb.kotlin_1728_2_1.model.City
import com.gb.kotlin_1728_2_1.model.utils.BUNDLE_KEY
import com.gb.kotlin_1728_2_1.view.details.DetailsFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {

    var _binding: FragmentGoogleMapsMainBinding? = null
    private val binding: FragmentGoogleMapsMainBinding
        get() {
            return _binding!!
        }

    lateinit var map: GoogleMap
    val markers = arrayListOf<Marker>()
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val msc = LatLng(54.0, 37.0)
        googleMap.addMarker(MarkerOptions().position(msc).title("Marker in Msc"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(msc))
        googleMap.setOnMapClickListener {
            getAddress(it)
            addMarker(it)
            drawLine()

        }
        googleMap.setOnMapLongClickListener {
            //showWeatherTouch()
           // getLocation() // как - то перенёс
            val tochAdres = getAddress(it)
         //   val lat =
           // toDetailWeatherOnMap(Weather(City(address, location.latitude, location.longitude)))

        }

        googleMap.isMyLocationEnabled = true  // TODO сделать проверку на разрешение геолокации
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true

    }


    private fun drawLine() {
        val lastIndex = markers.size
        if (lastIndex > 1) {
            map.addPolyline(
                PolylineOptions().add(
                    markers[lastIndex - 1].position,
                    markers[lastIndex - 2].position
                ).color(
                    Color.RED
                ).width(5F)
            )
        }
    }


    private fun addMarker(location: LatLng) {
        val marker = map.addMarker(
            (MarkerOptions().position(location)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)))
        )
        markers.add(marker!!)
    }

    private fun showWeatherTouch(address2: String, location: Location) {  // TODO найти способ передать данные в DetailsFragment о погодев выбранном месте на карте
        Thread {
            val geocoder =
                Geocoder(requireContext())  // Geocoder - библиотека/инструмент , который по координатам проверяет в базе список ближайших адресов
            val listAddress = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            requireActivity().runOnUiThread {
               // address2 = listAddress[0].getAddressLine(0).toString()
            }
        }.start()
    }


    private fun getAddress2(location: LatLng) {
        Log.d("loc", "getAddress() called with: location = $location")

        Thread {
            val geocoder =
                Geocoder(requireContext())  // Geocoder - библиотека/инструмент , который по координатам проверяет в базе список ближайших адресов
            val listAddress = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            requireActivity().runOnUiThread {
                binding.textAddress.text = listAddress[0].getAddressLine(0)

                
            }
        }.start()
    }



    private fun getAddress(location: LatLng) {
        Log.d("loc", "getAddress() called with: location = $location")

        Thread {
            val geocoder =
                Geocoder(requireContext())  // Geocoder - библиотека/инструмент , который по координатам проверяет в базе список ближайших адресов
            val listAddress = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            requireActivity().runOnUiThread {
                binding.textAddress.text = listAddress[0].getAddressLine(0)
            }
        }.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoogleMapsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.buttonSearch.setOnClickListener {
            searchTextPosition()
        }
    }

    private fun searchTextPosition() {
        Thread {
            val geocoder =
                Geocoder(requireContext())  // Geocoder - библиотека/инструмент , который по координатам проверяет в базе список ближайших адресов
            val listLocation =
                geocoder.getFromLocationName(binding.searchAddress.text.toString(), 1)
            requireActivity().runOnUiThread {
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            listLocation[0].latitude,
                            listLocation[0].longitude
                        ), 15F
                    )
                )
                map.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            listLocation[0].latitude,
                            listLocation[0].longitude
                        )
                    ).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                )
                //addMarker(LatLng(listLocation[0].latitude, listLocation[0].longitude))
            }
        }.start()
    }

    private fun toDetailWeatherOnMap (weather: Weather){
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