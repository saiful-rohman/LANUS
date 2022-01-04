package com.javaindo.lautnusantara.view.home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.databinding.FragmentHomeBinding
import com.javaindo.lautnusantara.utility.DataState
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.view.LN_LBCYBA.LN_LBCYBAActivity
import com.javaindo.lautnusantara.view.LN_laut_pesisir_khusus.LN_Laut_Pesisir_KhususActivity
import com.javaindo.lautnusantara.view.calculate_bbm.CalculateFuelActivity
import com.javaindo.lautnusantara.view.home.ui.home.adapter.ImageSliderAdapter
import com.javaindo.lautnusantara.view.result_catch.ResultCatchActivity
import com.javaindo.lautnusantara.view.selling_price.SellingPriceActivity
import com.javaindo.lautnusantara.viewmodel.HomeViewModel2
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    lateinit var viewModel: HomeViewModel2
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var sliderView : SliderView

    @Inject
    lateinit var prefHelp : PrefHelper

    val images = arrayListOf<Int>(R.drawable.two,R.drawable.three,R.drawable.one)

    companion object{
        fun newInstance() : HomeFragment{
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel2::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSlideAdapter()
        onClick()
        subscribeObserve()
//        viewModel.setHomeStateEvent2(HomeStateEvent2.getUserOnline)
    }

    private fun subscribeObserve(){

//        viewModel.onlineuser.observe( viewLifecycleOwner, Observer { dataONline ->
//            when(dataONline){
//                is DataState.Success -> {
//                    binding.pgrBrLoading.visibility = View.GONE
//                    val amountOnline : String = dataONline.data.amountOnline.toString() ?: "0"
////                    binding.headerPage.txvUserOnline.text = amountOnline
//                }
//                is DataState.Error -> {
//                    binding.pgrBrLoading.visibility = View.GONE
//                }
//                is DataState.Loading -> {
//                    binding.pgrBrLoading.visibility = View.VISIBLE
//                }
//            }
//        })
    }

    private fun setSlideAdapter(){
        val slideAdapter = activity?.let { ImageSliderAdapter(images, it) }
        sliderView = binding.sldrVImage
        binding.sldrVImage.setSliderAdapter(slideAdapter!!)
        binding.sldrVImage.setIndicatorAnimation(IndicatorAnimationType.WORM)
    }

    private fun onClick(){
        binding.inclContent.imgViewLNLaut.setOnClickListener {
            goToLNLautPesisirKhusus(1)
        }
        binding.inclContent.lnrLNCoast.setOnClickListener {
            goToLNLautPesisirKhusus(2)
        }
        binding.inclContent.imgViewLNSpecial.setOnClickListener {
            goToLNLautPesisirKhusus(3)
        }

        binding.inclContent.imgViewLNLemuru.setOnClickListener {
            goToLNLBCYBA(4)
        }
        binding.inclContent.lnrLNBigeye.setOnClickListener {
            goToLNLBCYBA(5)
        }
        binding.inclContent.imgViewLNCakalang.setOnClickListener {
            goToLNLBCYBA(6)
        }
        binding.inclContent.imgViewLNYellowfin.setOnClickListener {
            goToLNLBCYBA(7)
        }
        binding.inclContent.lnrLNBLuefin.setOnClickListener {
            goToLNLBCYBA(8)
        }
        binding.inclContent.imgViewLNAlbacore.setOnClickListener {
            goToLNLBCYBA(9)
        }

        binding.inclContent.imgViewLNSellprice.setOnClickListener {
            val intent = Intent(context,SellingPriceActivity::class.java)
            startActivity(intent)
        }
        binding.inclContent.imgViewLNCatch.setOnClickListener {
            val intent = Intent(context,ResultCatchActivity::class.java)
            startActivity(intent)
        }
        binding.inclContent.imgViewLNCalculatefuel.setOnClickListener {
            val intent = Intent(context,CalculateFuelActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToLNLautPesisirKhusus(typeP: Int){
        val intent = Intent(context,LN_Laut_Pesisir_KhususActivity::class.java)
        intent.putExtra("typePage",typeP.toString())
//        val intent = Intent(context,LN_Laut_Pesisir_khusus_Activity_new::class.java)
        startActivity(intent)
    }

    private fun goToLNLBCYBA(typeP: Int){
        val intent = Intent(context,LN_LBCYBAActivity::class.java)
        intent.putExtra("typePage",typeP.toString())
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}