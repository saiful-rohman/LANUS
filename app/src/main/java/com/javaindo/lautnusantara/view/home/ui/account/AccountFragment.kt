package com.javaindo.lautnusantara.view.home.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.javaindo.lautnusantara.databinding.FragmentAccountBinding
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.utility.SharedPrefKeys
import com.javaindo.lautnusantara.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val TAG = "AccountFragment"
    private lateinit var accountViewModel: AccountViewModel
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var prefHelp: PrefHelper

    companion object{
        fun newInstance() :AccountFragment{
            return AccountFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            logoutProcess()
        }
        setData()

    }

    private fun setData(){
        val userName : String? = prefHelp.getStringFromShared(SharedPrefKeys.userName())
        val phone : String? = prefHelp.getStringFromShared(SharedPrefKeys.userPhone())
        binding.txvUserName.text = userName
        binding.txvPhoneNumber.text = phone
    }

    private fun logoutProcess(){
        prefHelp.setBooleanToShared(SharedPrefKeys.isLogin(), false)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}