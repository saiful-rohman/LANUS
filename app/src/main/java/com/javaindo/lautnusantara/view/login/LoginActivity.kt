package com.javaindo.lautnusantara.view.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.javaindo.lautnusantara.databinding.ActivityLoginBinding
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.utility.SharedPrefKeys
import com.javaindo.lautnusantara.view.home.HomeActivity
import com.javaindo.lautnusantara.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels()
    @Inject lateinit var prefHelp : PrefHelper

//    private var locationPermissionGranted = false

    companion object{
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val PERMISSIONS_REQUEST_CALL_PHONE = 1
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getLocationPermission()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginProcess()
        }
    }

    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE),
                LoginActivity.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
//        locationPermissionGranted = false
        when (requestCode) {
            LoginActivity.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    locationPermissionGranted = true
                } else {
                    finish()
                }
            }
        }
    }

    private fun loginProcess(){
        if(true){
            processData()
            prefHelp.setStringToShared(SharedPrefKeys.userName(), binding.edtName.text.toString())
            prefHelp.setStringToShared(SharedPrefKeys.userPhone(), binding.edtPhoneNunmber.text.toString())
            prefHelp.setBooleanToShared(SharedPrefKeys.isLogin(),true)

            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)

            this.finish()
        }

    }

    private fun processData(){
        val isInsert = prefHelp.getBoolFromShared(SharedPrefKeys.isFirstInsertDataSettingUser())
        if(!isInsert){
            viewModel.processData()
            prefHelp.setBooleanToShared(SharedPrefKeys.isFirstInsertDataSettingUser(),true)
        }

    }
}