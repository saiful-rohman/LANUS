package com.javaindo.lautnusantara.view.calculate_bbm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.databinding.ActivityCalculateFuelBinding
import com.javaindo.lautnusantara.databinding.DialogChooseSettingBinding
import com.javaindo.lautnusantara.model.CalculateFuelModel
import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.utility.DataState
import com.javaindo.lautnusantara.viewmodel.CalculateFuelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculateFuelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCalculateFuelBinding
    private val viewModel : CalculateFuelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateFuelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerPage.txvTitleMenu.text = getString(R.string.text_icon_LN_hitung_BBM)

        subscribeObserve()
        viewModel.getSettingUser()
        onCLick()
    }

    private fun subscribeObserve(){
        viewModel.settingUserValues.observe(this, Observer { dataSet ->
            when(dataSet){
                is DataState.Success<SettingUserModel> -> {
                    binding.prgsLoading.visibility = View.GONE
                    binding.scrlContent.visibility = View.VISIBLE
                    setData(dataSet.data)
                }
                is DataState.Error ->{
                    binding.prgsLoading.visibility = View.GONE
                    binding.scrlContent.visibility = View.VISIBLE
                    Toast.makeText(this, "${dataSet.exception.message}", Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    binding.prgsLoading.visibility = View.VISIBLE
                    binding.scrlContent.visibility = View.GONE
                }
            }
        })
    }

    private fun setData(data : SettingUserModel){
        if(data != null){
            binding.incldCalculation.txvBrandEngine.text = data.brandEngine
            binding.incldCalculation.txvEngine.text = data.engine
        }
    }

    private fun onCLick(){
        val bbmBrandEngine = listOf(
            "EVINRUDE",
            "MERCURY",
            "SUZUKI",
            "YAMAHA",
            "HONDA",
            "YANMAR",
            "DONGFENG"
        )
        binding.incldCalculation.imgBrandEngine.setOnClickListener {
            dialogChoice(0,bbmBrandEngine)
        }

        val bbmEngine = listOf(
            "4 STROKE - 3.5 HP",
            "4 STROKE - 4.0 HP",
            "4 STROKE - 6.0 HP",
            "4 STROKE - 8.0 HP",
            "4 STROKE - 9.9 HP",
            "4 STROKE - 15.0 HP"
        )
        binding.incldCalculation.imgEngine.setOnClickListener {
            dialogChoice(1,bbmEngine)
        }

        binding.headerPage.imgVBack.setOnClickListener {
            this.finish()
        }
    }

    private fun setImageLogo(){
        binding.headerPage.imgLogoLN.setImageResource(R.drawable.ln_hitungbbm)
    }

    private fun setTitlePage(){
        binding.headerPage.txvTitleMenu.text = getString(R.string.text_title_LN_hitung_BBM)
    }

    private fun dialogChoice(indx : Int, chooseList : List<String>){
        var titleDialog = ""
        if(indx == 0){
            titleDialog = "Pilih Merk"
        } else if(indx == 1){
            titleDialog = "Pilih Mesin"
        }

        var _binding : DialogChooseSettingBinding

        val dialog = BottomSheetDialog(this)
        _binding = DialogChooseSettingBinding.inflate(layoutInflater)
        dialog.setContentView(_binding.root)

        var txvChoices : ArrayList<TextView> = ArrayList<TextView>()
        var imgChoices : ArrayList<ImageView> = ArrayList<ImageView>()
        var lnrChoices : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
//        val txvTitle = view.findViewById<TextView>(R.id.txvTitleDialogS)
//        val lnrDialogContent = findViewById<LinearLayout>(R.id.lnrDialogContent)
        _binding.txvTitleDialogS.text = titleDialog

        if(chooseList != null && chooseList.size > 0){
            for(i in chooseList.indices){
                val lnrChoice = LinearLayout(this)
                lnrChoices.add(lnrChoice)
                lnrChoices.get(i).layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
                lnrChoices.get(i).orientation = LinearLayout.HORIZONTAL
                val parmLnrChoices = lnrChoices.get(i).layoutParams as LinearLayout.LayoutParams
                parmLnrChoices.setMargins(0,15,0,20)

                val imgChoice = ImageView(this)
                imgChoices.add(imgChoice)
                imgChoices.get(i).layoutParams = LinearLayout.LayoutParams(50,50)
                imgChoices.get(i).setImageResource(R.drawable.ic_right_arrow)
                imgChoices.get(i).scaleType = ImageView.ScaleType.CENTER_CROP
                imgChoices.get(i).setPadding(0,0,0,0)
                val parmImgChoices = imgChoices.get(i).layoutParams as LinearLayout.LayoutParams
                parmImgChoices.setMargins(0,0,0,0)

                val txvChoice = TextView(this)
                txvChoices.add(txvChoice)
                txvChoices.get(i).layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
                txvChoices.get(i).textSize = 14F
                txvChoices.get(i).setText(chooseList.get(i))
                var valChoosen = ""
                if(indx == 0){
                    valChoosen = binding.incldCalculation.txvBrandEngine.text.toString()
                } else if(indx == 1){
                    valChoosen = binding.incldCalculation.txvEngine.text.toString()
                }
//                var strChoice = chooseList.get(i)
                if(chooseList.get(i).equals(valChoosen,false)){
                    imgChoices.get(i).setImageResource(R.drawable.ic_right_arrow_blue)
                    txvChoices.get(i).setTextColor(ContextCompat.getColor(applicationContext,R.color.primary_color))
                } else{
                    imgChoices.get(i).setImageResource(R.drawable.ic_right_arrow)
                    txvChoices.get(i).setTextColor(ContextCompat.getColor(applicationContext,R.color.title_result_catch_color))
                }
                val parmTxvChoices = txvChoices.get(i).layoutParams as LinearLayout.LayoutParams
                parmTxvChoices.setMargins(15,0,0,0)

                lnrChoices.get(i).addView(imgChoices.get(i))
                lnrChoices.get(i).addView(txvChoices.get(i))

                lnrChoices.get(i).setOnClickListener {
                    if(indx == 0){
                        binding.incldCalculation.txvBrandEngine.text = chooseList.get(i)
                    } else if(indx == 1){
                        binding.incldCalculation.txvEngine.text = chooseList.get(i)
                    }

                    dialog.dismiss()
                }

                _binding.lnrDialogContent.addView(lnrChoices.get(i))
            }
        }

        dialog.show()
    }

}