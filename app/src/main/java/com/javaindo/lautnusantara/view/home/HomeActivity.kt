package com.javaindo.lautnusantara.view.home

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.databinding.ActivityHomeBinding
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.utility.SharedPrefKeys
import com.javaindo.lautnusantara.view.home.adapter.HomeViewPagerAdapter
import com.javaindo.lautnusantara.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"
    private val viewModel : HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var pagerAdapter : HomeViewPagerAdapter
    private lateinit var viewPager : ViewPager

    private var onlineSum = 1

    @Inject
    lateinit var prefHelp : PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPagerAdapter()
        setBottomNavigation()
        setViewPagerListener()
        setHeaderPageBar(0)
        onClick()

    }

    private fun onClick(){
        binding.headerPage.imgVBack.setOnClickListener {
            setToHomePage()
        }
        binding.headerPage.imgCircleSos.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:Your Phone_number")
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
        }
    }

    fun setBottomNavigation(){
        binding.navView.setOnItemSelectedListener (NavigationBarView.OnItemSelectedListener{ menu->
            when(menu.itemId){
                R.id.navigation_home -> {
                    viewPager.currentItem = 0
                    setHeaderPageBar(0)
                }
                R.id.navigation_helpcenter -> {
                    viewPager.currentItem = 1
                    setHeaderPageBar(1)
                }
                R.id.navigation_information -> {
                    viewPager.currentItem = 2
                    setHeaderPageBar(2)
                }
                R.id.navigation_account -> {
                    viewPager.currentItem = 3
                    setHeaderPageBar(3)
                }
            }
            return@OnItemSelectedListener true
        })
    }

    fun setViewPagerAdapter(){
        viewPager = binding.vPager
        pagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
    }

    fun setViewPagerListener(){
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d("","onPageScrolled")
            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> binding.navView.selectedItemId = R.id.navigation_home
                    1 -> binding.navView.selectedItemId = R.id.navigation_helpcenter
                    2 -> binding.navView.selectedItemId = R.id.navigation_information
                    3 -> binding.navView.selectedItemId = R.id.navigation_account
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.d("","onPageScrollStateChanged")
            }

        })
    }

    fun setToHomePage(){
        binding.navView.selectedItemId = R.id.navigation_home
    }

    fun setHeaderPageBar(indexHeader : Int){
        if(indexHeader == 0){
            binding.headerPage.rltvHome.visibility = View.VISIBLE
            binding.headerPage.rltvNonHome.visibility = View.GONE
            binding.headerPage.txvWelcome.text = prefHelp.getStringFromShared(SharedPrefKeys.userName())
            binding.headerPage.txvUserOnline.text = "${onlineSum} ${getString(R.string.amount_online)}"
        } else {
            binding.headerPage.rltvHome.visibility = View.GONE
            binding.headerPage.rltvNonHome.visibility = View.VISIBLE

            if(indexHeader == 1){
                binding.headerPage.txvTitleMenu.setText(getString(R.string.title_help_center))
            } else if(indexHeader == 2){
                binding.headerPage.txvTitleMenu.setText(getString(R.string.title_Information))
            } else if(indexHeader == 3){
                binding.headerPage.txvTitleMenu.setText(getString(R.string.title_Account))
            }
        }
    }

}