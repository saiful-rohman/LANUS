package com.javaindo.lautnusantara.view.home.ui.helpcenter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.javaindo.lautnusantara.databinding.FragmentHelpCenterBinding
import com.javaindo.lautnusantara.view.callus.CallUsActivity
import com.javaindo.lautnusantara.view.faq.FAQActivity
import com.javaindo.lautnusantara.view.live_chat.LiveChatActivity
import com.javaindo.lautnusantara.view.setting_user.SettingUserActivity
import com.javaindo.lautnusantara.viewmodel.HelpCenterViewModel

class HelpCenterFragment : Fragment() {

    private val TAG = "HelpCenterFragment"
    private lateinit var helpCenterViewModel: HelpCenterViewModel
    private var _binding: FragmentHelpCenterBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() : HelpCenterFragment{
            return HelpCenterFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        helpCenterViewModel = ViewModelProvider(this).get(HelpCenterViewModel::class.java)
        _binding = FragmentHelpCenterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        binding.shpImgFAQ.setOnClickListener {
            val intent = Intent(context,FAQActivity::class.java)
            startActivity(intent)
        }
        binding.shpImgCallCenter.setOnClickListener {
            val intent = Intent(context,CallUsActivity::class.java)
            startActivity(intent)
        }
        binding.shpImgLiveChat.setOnClickListener {
            val intent = Intent(context,LiveChatActivity::class.java)
            startActivity(intent)
        }
        binding.shpImgSetting.setOnClickListener {
            val intent = Intent(context,SettingUserActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}