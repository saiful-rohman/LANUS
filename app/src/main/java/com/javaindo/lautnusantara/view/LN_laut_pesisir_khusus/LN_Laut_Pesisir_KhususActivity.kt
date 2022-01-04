package com.javaindo.lautnusantara.view.LN_laut_pesisir_khusus

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
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
import com.javaindo.lautnusantara.model.LatLongModel
import com.javaindo.lautnusantara.utility.DataState
import com.javaindo.lautnusantara.view.LN_laut_pesisir_khusus.adapter.LNLautPesisirKhususAdapter
import com.javaindo.lautnusantara.viewmodel.LN_Laut_Pesisir_KhususViewModel
import com.javaindo.lautnusantara.viewmodel.LatLongStateEvent
import dagger.hilt.android.AndroidEntryPoint


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
        viewModel.setStateEvent(LatLongStateEvent.GetLatLong)

        setPartData()
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

    private fun setPartData(){
        setImageLogo(typePage)
        setTitlePage(typePage)
    }

    private fun onClick(){
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
//                    when (newState) {
//                        BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(this@LN_Laut_Pesisir_KhususActivity, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
//                        BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(this@LN_Laut_Pesisir_KhususActivity, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
//                        BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(this@LN_Laut_Pesisir_KhususActivity, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
//                        BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(this@LN_Laut_Pesisir_KhususActivity, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
//                        BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(this@LN_Laut_Pesisir_KhususActivity, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
//                        else -> Toast.makeText(this@LN_Laut_Pesisir_KhususActivity, "OTHER_STATE", Toast.LENGTH_SHORT).show()
//                    }
            }
        })

//            binding.btnBottomSheetPersistent.setOnClickListener {
//                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                else
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            }
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

//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


//        this.map!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
        this.map!!.setMinZoomPreference(1.0f)
        this.map!!.setMaxZoomPreference(20.0f)

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        this.map?.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            // Return null here, so that getInfoContents() is called next.
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                // Inflate the layouts for the info window, title and snippet.
                val infoWindow = layoutInflater.inflate(R.layout.custom_info_contents,
                    findViewById<FrameLayout>(R.id.map), false)
                val title = infoWindow.findViewById<TextView>(R.id.title)
                title.text = marker.title
                val snippet = infoWindow.findViewById<TextView>(R.id.snippet)
                snippet.text = marker.snippet
                return infoWindow
            }
        })

        // Prompt the user for permission.
        getLocationPermission()

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
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
                map?.uiSettings?.isMyLocationButtonEnabled = true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.option_get_place) {
//            showCurrentPlace()
//        }
        return true
    }

    @SuppressLint("MissingPermission")
    private fun showCurrentPlace() {
        if (map == null) {
            return
        }
        if (locationPermissionGranted) {
            // Use fields to define the data types to return.
            val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

            // Use the builder to create a FindCurrentPlaceRequest.
            val request = FindCurrentPlaceRequest.newInstance(placeFields)

            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            val placeResult = placesClient.findCurrentPlace(request)
            placeResult.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val likelyPlaces = task.result

                    // Set the count, handling cases where less than 5 entries are returned.
                    val count = if (likelyPlaces != null && likelyPlaces.placeLikelihoods.size < M_MAX_ENTRIES) {
                        likelyPlaces.placeLikelihoods.size
                    } else {
                        M_MAX_ENTRIES
                    }
                    var i = 0
                    likelyPlaceNames = arrayOfNulls(count)
                    likelyPlaceAddresses = arrayOfNulls(count)
                    likelyPlaceAttributions = arrayOfNulls<List<*>?>(count)
                    likelyPlaceLatLngs = arrayOfNulls(count)
                    for (placeLikelihood in likelyPlaces?.placeLikelihoods ?: emptyList()) {
                        // Build a list of likely places to show the user.
                        likelyPlaceNames[i] = placeLikelihood.place.name
                        likelyPlaceAddresses[i] = placeLikelihood.place.address
                        likelyPlaceAttributions[i] = placeLikelihood.place.attributions
                        likelyPlaceLatLngs[i] = placeLikelihood.place.latLng
                        i++
                        if (i > count - 1) {
                            break
                        }
                    }

                    // Show a dialog offering the user the list of likely places, and add a
                    // marker at the selected place.
                    openPlacesDialog()
                } else {
                    Log.e(TAG, "Exception: %s", task.exception)
                }
            }
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.")

            // Add a default marker, because the user hasn't selected a place.
            map?.addMarker(MarkerOptions()
                .title(getString(R.string.default_info_title))
                .position(defaultLocation)
                .snippet(getString(R.string.default_info_snippet)))

            // Prompt the user for permission.
            getLocationPermission()
        }
    }

    private fun openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        val listener = DialogInterface.OnClickListener { dialog, which -> // The "which" argument contains the position of the selected item.
            val markerLatLng = likelyPlaceLatLngs[which]
            var markerSnippet = likelyPlaceAddresses[which]
            if (likelyPlaceAttributions[which] != null) {
                markerSnippet = """
                $markerSnippet
                ${likelyPlaceAttributions[which]}
                """.trimIndent()
            }

            if (markerLatLng == null) {
                return@OnClickListener
            }

            // Add a marker for the selected place, with an info window
            // showing information about that place.
            map?.addMarker(MarkerOptions()
                .title(likelyPlaceNames[which])
                .position(markerLatLng)
                .snippet(markerSnippet))

            // Position the map's camera at the location of the marker.
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                DEFAULT_ZOOM.toFloat()))
        }

        // Display the dialog.
        AlertDialog.Builder(this)
            .setTitle(R.string.pick_place)
            .setItems(likelyPlaceNames, listener)
            .show()
    }

}