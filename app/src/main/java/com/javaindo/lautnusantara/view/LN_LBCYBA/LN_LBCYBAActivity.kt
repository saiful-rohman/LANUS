package com.javaindo.lautnusantara.view.LN_LBCYBA

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
import com.javaindo.lautnusantara.databinding.ActivityLnLbcybaactivityBinding
import com.javaindo.lautnusantara.databinding.DialogLayersBinding
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.utility.SharedPrefKeys
import com.javaindo.lautnusantara.viewmodel.LN_LBCYBAViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import javax.inject.Inject
import android.widget.TextView.OnEditorActionListener
import java.text.DecimalFormat

@AndroidEntryPoint
class LN_LBCYBAActivity : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var binding : ActivityLnLbcybaactivityBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel : LN_LBCYBAViewModel by viewModels()
    private var typePage = 0

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
    private var distMileage : Double = 0.0
    private lateinit var lateLatlong : LatLng

    @Inject
    lateinit var prefHelp: PrefHelper

    companion object{
        private val TAG = LN_LBCYBAActivity::class.java.simpleName
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
        binding = ActivityLnLbcybaactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intet = intent
        typePage = intet.getStringExtra("typePage").toString().toInt()

        setPartInit()
        setBootomsheet()
        setColorPotential()
        onClick()

        // [START_EXCLUDE silent]
        // Construct a PlacesClient
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(this)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapln2) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setPartInit(){
        binding.headerPageLN.edtSearchCity.setAdapter(ArrayAdapter(this,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.cities)))

        binding.incldLayerRoute2.txvEngine.text = prefHelp.getStringFromShared(SharedPrefKeys.brandEngine())

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

        binding.incldLayerRoute2.imgCloseRouteLayer.setOnClickListener {
            showExceptRoute()
        }

        binding.imgLayerRoute2.setOnClickListener {
            routeMap()
        }

        binding.imgLayers2.setOnClickListener {
            layersDailog()
        }

        binding.headerPageLN.imgToLocation.setOnClickListener {
            getDeviceLocation()
        }

        binding.headerPageLN.imgVBack.setOnClickListener {
            this.finish()
        }
    }

    private fun setColorPotential(){
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#fe0000"),
                Color.parseColor("#ffeb3c"),
            Color.parseColor("#0000fe"))
        )
        gradientDrawable.cornerRadius = 0f;

        //Set Gradient
        binding.incldBottomSheet.incldPotential.colorGradientPotential.setBackground(gradientDrawable);
    }

    private fun setImageLogo(type: Int){
        when(type){
            4 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_lemuru)
            5 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_bigeye)
            6 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_cakalang)
            7 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_yellowfin)
            8 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_bluefin)
            9 -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.ln_albacore)
            else -> binding.headerPageLN.imgLogoLN.setImageResource(R.drawable.noimage)
        }

    }

    private fun setTitlePage(type: Int){
        type.let {
            when(it){
                4 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_lemuru)
                5 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_bigeye)
                6 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_cakalang)
                7 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_yellowfin)
                8 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_bluefin)
                9 -> binding.headerPageLN.txvTitleMenu.text = getString(R.string.text_title_LN_albacore)
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
            outState.putParcelable(LN_LBCYBAActivity.KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(LN_LBCYBAActivity.KEY_LOCATION, lastKnownLocation)
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
                LN_LBCYBAActivity.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            LN_LBCYBAActivity.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

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

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()

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

                if(latLongs.size > 1){
                    CalculationByDistance(lateLatlong,latLong)
                } else{
                    lateLatlong = latLong
                }

            }
        })
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
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), LN_LBCYBAActivity.DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, LN_LBCYBAActivity.DEFAULT_ZOOM.toFloat()))
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

    private fun refreshRoutePage(){
        binding.incldLayerRoute2.txvMIleage.text = "0.0 KM"
        binding.incldLayerRoute2.txvTravelingTime.text = ""
        binding.incldLayerRoute2.txvFuel.text = "0.0 LITER"
        binding.incldLayerRoute2.txvBearing.text = "0"
        binding.incldLayerRoute2.txvHeading.text = "0"
    }

    private fun removePolyline(){
        for (i in polyLines.indices){
            polyLines.get(i).remove()
        }
        latLongs = ArrayList<LatLng>()
        distMileage = 0.0
        lateLatlong = defaultLocation
    }

    private fun hideExceptRoute(){
        binding.incldBottomSheet.bottomSheet.visibility = View.GONE
        binding.headerPageLN.lnrTextSearchMap.visibility = View.GONE
        binding.rtlvRouteLayer2.visibility = View.GONE
        binding.rltvLayersImg2.visibility = View.GONE

        binding.incldLayerRoute2.lnrRouteContent.visibility = View.VISIBLE
        isRoute = true
    }

    private fun showExceptRoute(){
        binding.incldBottomSheet.bottomSheet.visibility = View.VISIBLE
        binding.headerPageLN.lnrTextSearchMap.visibility = View.VISIBLE
        binding.rtlvRouteLayer2.visibility = View.VISIBLE
        binding.rltvLayersImg2.visibility = View.VISIBLE

        binding.incldLayerRoute2.lnrRouteContent.visibility = View.GONE
        isRoute = false
        removeAllMarker()
        refreshRoutePage()
    }

    private fun searchLocation(){
        var location: String = binding.headerPageLN.edtSearchCity.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext,"provide location", Toast.LENGTH_SHORT).show()
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
        val newFormat = DecimalFormat("#,##")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))

        distMileage = distMileage + km
        val timeTravel : Double = distMileage * 4 //4 km/hour
        val fuelUsed :  Double = distMileage / 8 //8 km/liter
        binding.incldLayerRoute2.txvMIleage.text = "${String.format("%.2f",distMileage)} KM"
        binding.incldLayerRoute2.txvTravelingTime.text = "${convertHour(timeTravel)} Minute"
        binding.incldLayerRoute2.txvUsedBBM.text = "${String.format("%.2f",fuelUsed)} Liter"
        lateLatlong = EndP

        return Radius * c
    }

    private fun convertHour(value : Double) : String{
        var result = ""
        val roundVal = Math.round(value)

        val timeTravelHour: Long = roundVal / 60
        val timeTravelMinute: Long = roundVal % 60
        result = "${if(timeTravelHour < 10) "0${timeTravelHour}" else "${timeTravelHour}"}:" +
                "${if(timeTravelMinute < 10) "0${timeTravelMinute}" else "${timeTravelMinute}"}"

        return result
    }

}