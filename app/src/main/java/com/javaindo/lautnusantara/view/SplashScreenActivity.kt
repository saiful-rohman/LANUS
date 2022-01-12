package com.javaindo.lautnusantara.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.javaindo.lautnusantara.databinding.ActivitySplashScreenBinding
import com.javaindo.lautnusantara.model.DatakuModel
import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.utility.DataState
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.utility.SharedPrefKeys
import com.javaindo.lautnusantara.view.home.HomeActivity
import com.javaindo.lautnusantara.view.login.LoginActivity
import com.javaindo.lautnusantara.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val TAG = "SplashScreenActivity"
    private lateinit var binding : ActivitySplashScreenBinding
    private val viewModel : SplashScreenViewModel by viewModels()

    @Inject lateinit var prefHelp : PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var isLogin = prefHelp.getBoolFromShared(SharedPrefKeys.isLogin())

        if(isLogin){
            Handler(Looper.getMainLooper()).postDelayed({
                let{
                    val intet = Intent(this,HomeActivity::class.java)
                    startActivity(intet)

                    this.finish()
                }
            },700)
        } else{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

            this.finish()
        }

        //tes data
        subscribeObserve()
//        viewModel.setStateEvent(MainStateEvent.GetDatakuEvent)
        viewModel.getSettingUser()
    }

    private fun subscribeObserve(){
        viewModel.settingValues.observe(this, Observer { dataSet ->
            when(dataSet){
                is DataState.Success<SettingUserModel> -> {
//                    binding.prgsLoading.visibility = View.GONE
//                    binding.scrlContent.visibility = View.VISIBLE
                    prefHelp.setIntToShared(SharedPrefKeys.idUserSetting(),dataSet.data.id)
                    prefHelp.setStringToShared(SharedPrefKeys.bbmConsume(),dataSet.data.bbmConsume)
                    prefHelp.setStringToShared(SharedPrefKeys.mileage(),dataSet.data.mileage)
                    prefHelp.setStringToShared(SharedPrefKeys.speed(),dataSet.data.speed)
                    prefHelp.setStringToShared(SharedPrefKeys.brandEngine(),dataSet.data.brandEngine)
                    prefHelp.setStringToShared(SharedPrefKeys.engine(),dataSet.data.engine)
                }
                is DataState.Error ->{
//                    binding.prgsLoading.visibility = View.GONE
//                    binding.scrlContent.visibility = View.VISIBLE
                    Toast.makeText(this, "${dataSet.exception.message}",Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
//                    binding.prgsLoading.visibility = View.VISIBLE
//                    binding.scrlContent.visibility = View.GONE
                }
            }
        })
    }

    private fun displayError(message : String){
        if(message != null){
            Toast.makeText(this,"Error : ${message}",Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(this,"Error : tidak tahu",Toast.LENGTH_LONG).show()
        }
    }

    private fun displayLoading(isDisplayed : Boolean){
        if(isDisplayed){
            Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(this,"UnLoading",Toast.LENGTH_LONG).show()
        }
    }

    private fun appendStringData(datakus : List<DatakuModel>){
        val sb = StringBuilder()
        for(data in datakus){
            sb.append(data.title+"\n")
        }

        Toast.makeText(this,"hasilnya : \n ${sb}",Toast.LENGTH_LONG).show()
    }

}