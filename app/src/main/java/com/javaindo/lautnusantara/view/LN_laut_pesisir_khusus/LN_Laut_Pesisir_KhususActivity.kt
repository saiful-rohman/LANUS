package com.javaindo.lautnusantara.view.LN_laut_pesisir_khusus

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.javaindo.lautnusantara.BuildConfig
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.databinding.ActivityLnLautPesisirKhususBinding
import com.javaindo.lautnusantara.databinding.DialogLayersBinding
import com.javaindo.lautnusantara.model.LatLongModel
import com.javaindo.lautnusantara.utility.DataState
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.utility.SharedPrefKeys
import com.javaindo.lautnusantara.view.LN_laut_pesisir_khusus.adapter.LNLautPesisirKhususAdapter
import com.javaindo.lautnusantara.viewmodel.LN_Laut_Pesisir_KhususViewModel
import com.javaindo.lautnusantara.viewmodel.LatLongStateEvent
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import javax.inject.Inject
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import java.text.DecimalFormat


//import com.javaindo.lautnusantara.view.LN_laut_pesisir_khusus.adapter.LNLautPesisirKhususAdapter

@AndroidEntryPoint
class LN_Laut_Pesisir_KhususActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "LN_Laut_Pesisir_Khusus"
    private val viewModel : LN_Laut_Pesisir_KhususViewModel by viewModels()
    lateinit var binding : ActivityLnLautPesisirKhususBinding

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var typePage = 0
    private lateinit var latLongAdapter : LNLautPesisirKhususAdapter

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    // The entry point to the Places API.
    private lateinit var placesClient: PlacesClient

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null
    private var likelyPlaceNames: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAddresses: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAttributions: Array<List<*>?> = arrayOfNulls(0)
    private var likelyPlaceLatLngs: Array<LatLng?> = arrayOfNulls(0)

    private var isClickZee = false
    private var isClickSatellite = false
    private var isRoute = false
    private var markerList : ArrayList<Marker> = ArrayList<Marker>()
    private var latLongs :  ArrayList<LatLng> = ArrayList<LatLng>()
    private var polyLines :  ArrayList<Polyline> = ArrayList<Polyline>()

    @Inject
    lateinit var prefHelp: PrefHelper

    companion object{
        private const val DEFAULT_ZOOM = 10
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        // [START maps_current_place_state_keys]
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
        // [END maps_current_place_state_keys]

        // Used for selecting the current place.
        private const val M_MAX_ENTRIES = 5
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLnLautPesisirKhususBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intet = intent
        typePage = intet.getStringExtra("typePage").toString().toInt()

        subscribeObserve()
        viewModel.getLatLongData(LatLongStateEvent.GetLatLong)
        setPartInit()
        setBootomsheet()
        onClick()

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        // [START_EXCLUDE silent]
        // Construct a PlacesClient
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(this)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapln) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun setPartInit(){
        binding.headerPageLN.edtSearchCity.setAdapter(ArrayAdapter(this,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.cities)))

        binding.incldLayerRoute.txvEngine.text = prefHelp.getStringFromShared(SharedPrefKeys.brandEngine())

        setImageLogo(typePage)
        setTitlePage(typePage)
    }

    private fun onClick(){
        binding.headerPageLN.edtSearchCity.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                searchLocation()

                return@OnEditorActionListener true
            }
            false
        })

        binding.incldLayerRoute.imgCloseRouteLayer.setOnClickListener {
            showExceptRoute()
        }

        binding.imgLayerRoute.setOnClickListener {
            routeMap()
        }

        binding.imgLayers.setOnClickListener {
            layersDailog()
        }

        binding.headerPageLN.imgToLocation.setOnClickListener {
            getDeviceLocation()
        }

        binding.headerPageLN.imgVBack.setOnClickListener {
            this.finish()
        }
    }

    private fun subscribeObserve(){
        viewModel.latLongList.observe(this, Observer {
            when(it){
                is DataState.Success -> {
                    setAdapter(it.data)
                }
                is DataState.Error -> {
                    Log.d("aaa","aaa")
                }
                is DataState.Loading -> {
                    Log.d("bbb","bbb")
                }
            }
        })
    }

    private fun setAdapter(dataList : List<LatLongModel>){
        latLongAdapter = LNLautPesisirKhususAdapter(dataList)
        binding.incldBottomSheet.rcylrBujurLintang.layoutManager =LinearLayoutManager(applicationContext)
        binding.incldBottomSheet.rcylrBujurLintang.adapter = latLongAdapter
    }

    private fun setImageLogo(type: Int){
        when(type){
            1 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_laut)
            2 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_pesisir)
            3 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_khusus)
            else -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.noimage)
        }

    }

    private fun setTitlePage(type: Int){
        type.let {
            when(it){
                1 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_laut)
                2 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_pesisir)
                3 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_khusus)
                else -> binding.headerPageLN.txvTitleMenu.text = ""
            }
        }
    }

    private fun setBootomsheet(){
        bottomSheetBehavior = BottomSheetBehavior.from(binding.incldBottomSheet.bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_json)
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
        this.map = googleMap

        this.map!!.setMinZoomPreference(1.0f)
        this.map!!.setMaxZoomPreference(20.0f)
        this.map!!.uiSettings.isMyLocationButtonEnabled = false
        this.map!!.isMyLocationEnabled = false


        this.map!!.setOnMapClickListener(GoogleMap.OnMapClickListener { latLong ->
            if(isRoute){
                var mark = this.map!!.addMarker(MarkerOptions().position(latLong))
                markerList.add(mark!!)

                latLongs.add(latLong)
                var lineOption = this.map!!.addPolyline(PolylineOptions()
                    .color(getColor(R.color.red))
                    .width(15f)
                    .addAll(latLongs))

                polyLines.add(lineOption)
            }
        })

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = false
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun mapSatellite(){
        try {
            map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        } catch (l : Exception){
            Log.e(TAG, "${l}")
        } catch (e : Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    private fun mapNormal(){
        try {
            map?.mapType = GoogleMap.MAP_TYPE_NORMAL
        } catch (l : Exception){
            Log.e(TAG, "${l}")
        } catch (e : Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    private fun mapZEE(){
//        try {
//            val success = map?.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                    this, R.raw.style_json)
//            )
//            if (!success!!) {
//                Log.e(TAG, "Style parsing failed.")
//            }
//        } catch (e: Resources.NotFoundException) {
//            Log.e(TAG, "Can't find style. Error: ", e)
//        }
    }

    private fun layersDailog(){
        val dialog = Dialog(this)
        val _binding = DialogLayersBinding.inflate(LayoutInflater.from(this))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(_binding.root)

        if(isClickSatellite){
            _binding.imgSatellite.setImageResource(R.drawable.button_active)
        } else {
            _binding.imgSatellite.setImageResource(R.drawable.button_passif)
        }

        if(isClickZee){
            _binding.imgZee.setImageResource(R.drawable.button_active)
        } else {
            _binding.imgZee.setImageResource(R.drawable.button_passif)
        }

        _binding.imgZee.setOnClickListener {
            if(isClickZee){
                isClickZee = false
            } else {
                isClickZee = true
                mapZEE()
            }

            dialog.dismiss()
        }

        _binding.imgSatellite.setOnClickListener {
            if(isClickSatellite){
                isClickSatellite = false
                mapNormal()
            } else {
                isClickSatellite = true
                mapSatellite()
            }

            dialog.dismiss()
        }

        dialog.show()

    }

    private fun routeMap(){
        hideExceptRoute()

    }

    private fun removeAllMarker(){
        for(i in markerList.indices){
            markerList.get(i).remove()
        }
        removePolyline()
    }

    private fun removePolyline(){
        for (i in polyLines.indices){
            polyLines.get(i).remove()
        }
        latLongs = ArrayList<LatLng>()
    }

    private fun hideExceptRoute(){
        binding.incldBottomSheet.bottomSheet.visibility = View.GONE
        binding.headerPageLN.lnrTextSearchMap.visibility = View.GONE
        binding.rtlvRouteLayer.visibility = View.GONE
        binding.rltvLayersImg.visibility = View.GONE

        binding.incldLayerRoute.lnrRouteContent.visibility = View.VISIBLE
        isRoute = true
    }

    private fun showExceptRoute(){
        binding.incldBottomSheet.bottomSheet.visibility = View.VISIBLE
        binding.headerPageLN.lnrTextSearchMap.visibility = View.VISIBLE
        binding.rtlvRouteLayer.visibility = View.VISIBLE
        binding.rltvLayersImg.visibility = View.VISIBLE

        binding.incldLayerRoute.lnrRouteContent.visibility = View.GONE
        isRoute = false
        removeAllMarker()
    }

    private fun searchLocation(){
        var location: String = binding.headerPageLN.edtSearchCity.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext,"provide location",Toast.LENGTH_SHORT).show()
        }
        else{
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
//            map!!.addMarker(MarkerOptions().position(latLng).title(location))
            map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//            Toast.makeText(applicationContext, address.latitude.toString() + " " + address.longitude, Toast.LENGTH_LONG).show()
        }
    }

    fun CalculationByDistance(StartP: LatLng, EndP: LatLng): Double {
        val Radius = 6371 // radius of earth in Km
        val lat1 = StartP.latitude
        val lat2 = EndP.latitude
        val lon1 = StartP.longitude
        val lon2 = EndP.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + (Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2)))
        val c = 2 * Math.asin(Math.sqrt(a))
        val valueResult = Radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
//        Log.i(
//            "Radius Value", "" + valueResult + "   KM  " + kmInDec
//                    + " Meter   " + meterInDec
//        )
        binding.incldLayerRoute.txvMIleage.text = "${valueResult} KM"

        return Radius * c
    }

}