package com.javaindo.lautnusantara.view.home.ui.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.javaindo.lautnusantara.databinding.FragmentInformationBinding
import com.javaindo.lautnusantara.viewmodel.NotificationsViewModel

class InformationFragment : Fragment() {

    private val TAG = "InformationFragment"
    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() : InformationFragment{
            return InformationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}