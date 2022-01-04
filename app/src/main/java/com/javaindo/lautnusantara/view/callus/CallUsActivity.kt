package com.javaindo.lautnusantara.view.callus

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.javaindo.lautnusantara.databinding.ActivityCallUsBinding

class CallUsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCallUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick(){

        binding.headerPage.imgVBackHelpCenter.setOnClickListener {
            this.finish()
        }

        binding.txvBpolLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bpol.litbang.kkp.go.id"))
            startActivity(intent)
        }
    }
}