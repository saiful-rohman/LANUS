package com.javaindo.lautnusantara.view.live_chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.javaindo.lautnusantara.databinding.ActivityLiveChatBinding

class LiveChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLiveChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick(){
        binding.headerPage.imgVBackHelpCenter.setOnClickListener {
            this.finish()
        }
    }
}