package com.javaindo.lautnusantara.view.result_catch

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.databinding.ActivityResultCatchBinding
import com.javaindo.lautnusantara.databinding.DialogCalendarBinding
import com.javaindo.lautnusantara.model.*
import com.javaindo.lautnusantara.view.result_catch.adapter.DialogCalendarAdapter
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.javaindo.lautnusantara.utility.DataState
import com.javaindo.lautnusantara.viewmodel.ResultCatchViewModel
import com.javaindo.lautnusantara.viewmodel.ResultStateValue
import dagger.hilt.android.AndroidEntryPoint
import android.view.ViewGroup

import android.view.LayoutInflater
import androidx.annotation.Nullable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.content.DialogInterface





@AndroidEntryPoint
class ResultCatchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityResultCatchBinding
    private val viewModel : ResultCatchViewModel by viewModels()

    private var linears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var headerlinears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var headerImageArrows : ArrayList<ImageView> = ArrayList<ImageView>()
    private var headerTxvTitles : ArrayList<TextView> = ArrayList<TextView>()

    private var contentLinears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var contentTxvPorts : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvLabelCatch : ArrayList<TextView> = ArrayList<TextView>()
    private var contentLinearAnimalCatchs : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var contentTxvAmounts : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvWeights : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvPrices : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvPricesTot : ArrayList<TextView> = ArrayList<TextView>()
    private var contentViewSeparators : ArrayList<View> = ArrayList<View>()

    private var checkExpandeds : ArrayList<TrackClickCursor> = ArrayList<TrackClickCursor>()
    private var searchParm :SearchResultCatchParmModel = SearchResultCatchParmModel()
    private var isFirstSearch = false
    private var isOkayClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPartInit()
        subscribeObserve()
        processData()
        onClick()
    }

    private fun subscribeObserve(){
        viewModel.resultCatchData.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    binding.pgrsLoading.visibility = View.GONE
                    generateContent(dataState.data)
                }
                is DataState.Error -> {
                    binding.pgrsLoading.visibility = View.GONE
                    Toast.makeText(this,"${dataState.exception}",Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    binding.pgrsLoading.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun processData(){
        viewModel.getResultCacth(ResultStateValue.getResultCatch,searchParm)
    }

    private fun setPartInit(){
        binding.headerPage.imgLogoLN.setImageResource(R.drawable.ln_hasiltangkap)
        binding.headerPage.txvTitleMenu.text = getString(R.string.text_title_LN_hasiltangkap)
    }

    private fun onClick(){
        binding.inlcdResultCatchSearch.edtDateTo.setOnClickListener {
            dialogDateSearch()
        }

        binding.inlcdResultCatchSearch.edtDateFrom.setOnClickListener {
            dialogDateSearch()
        }

        binding.headerPage.imgVBack.setOnClickListener {
            this.finish()
        }
    }

    private fun generateContent(dataList : ArrayList<ResultCatchModel>){
        refreshGenContent()

        if(dataList != null && dataList.size > 0){
            for(i in dataList.indices){
                var expandParam = TrackClickCursor()

                val linear = LinearLayout(this)
                linears.add(linear)
                linears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                linears.get(i).orientation = LinearLayout.VERTICAL
                linears.get(i).weightSum = 10F
                linears.get(i).setBackgroundResource(R.color.white)
                val paramsLin = linears.get(i).layoutParams as LinearLayout.LayoutParams
                paramsLin.setMargins(30, 0, 30, 0)

                val linearHdr = LinearLayout(this)
                headerlinears.add(linearHdr)
                headerlinears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100)
                headerlinears.get(i).orientation = LinearLayout.HORIZONTAL
                headerlinears.get(i).weightSum = 10F
                headerlinears.get(i).setBackgroundResource(R.color.white)
                val paramsLinCatSpar = headerlinears.get(i).layoutParams as LinearLayout.LayoutParams
                paramsLinCatSpar.setMargins(30, 0, 30, 15)

                val arrowImgHdr = ImageView(this)
                headerImageArrows.add(arrowImgHdr)
                headerImageArrows.get(i).layoutParams = LinearLayout.LayoutParams(70,70)
                headerImageArrows.get(i).scaleType = ImageView.ScaleType.CENTER_CROP
                headerImageArrows.get(i).setPadding(10,10,10,10)
                headerImageArrows.get(i).setImageResource(R.drawable.ic_arrow_down)
                val layoutParmImgSubCat = headerImageArrows.get(i).layoutParams as LinearLayout.LayoutParams
//            layoutParmImgSubCat.setMargins(0, 0, 0, 0)
                layoutParmImgSubCat.weight = 4F
                layoutParmImgSubCat.gravity = Gravity.CENTER_VERTICAL

                headerImageArrows.get(i).setOnClickListener {
                    var trackIsExpand = TrackClickCursor()
                    var check = false
                    for(c in checkExpandeds.indices){
                        if(i == checkExpandeds.get(c).indxCursor){
                            trackIsExpand = checkExpandeds.get(c)
                            check = true
                            break
                        }
                    }

                    if(check){
                        if(trackIsExpand.isExpand){
                            headerImageArrows.get(i).setImageResource(R.drawable.ic_arrow_down)
                            headerTxvTitles.get(i).setTextColor(ContextCompat.getColor(applicationContext!!, R.color.title_result_catch_color))
                            contentLinears.get(trackIsExpand.indxCursor).visibility = View.GONE
                            checkExpandeds.get(i).isExpand = false
                        } else {
                            headerImageArrows.get(i).setImageResource(R.drawable.ic_up_arrow_blue)
                            headerTxvTitles.get(i).setTextColor(ContextCompat.getColor(applicationContext!!, R.color.primary_color))
                            contentLinears.get(trackIsExpand.indxCursor).visibility = View.VISIBLE
                            checkExpandeds.get(i).isExpand = true
                        }
                    }
                }

                val txvTitleHdr = TextView(this)
                headerTxvTitles.add(txvTitleHdr)
                headerTxvTitles.get(i).setText(dataList.get(i).dateProc)
                headerTxvTitles.get(i).typeface = Typeface.DEFAULT_BOLD
                headerTxvTitles.get(i).setTextColor(ContextCompat.getColor(applicationContext!!, R.color.title_result_catch_color))
//            headerTxvTitles.get(i).setPadding(20, 0, 0, 0)
//            headerTxvTitles.get(i).setTextSize(16f)
                //txvSubCategoryNameList.get(i).setTextAppearance(activity,R.style.font_roboto_regular)
                headerTxvTitles.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmTitle = headerTxvTitles.get(i).layoutParams as LinearLayout.LayoutParams
                parmTitle.setMargins(0, 0, 100, 0)
                parmTitle.weight = 6F
                parmTitle.gravity = Gravity.CENTER_VERTICAL

                headerlinears.get(i).addView(headerTxvTitles.get(i))
                headerlinears.get(i).addView(headerImageArrows.get(i))

                val linearContnts = LinearLayout(this)
                contentLinears.add(linearContnts)
                contentLinears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                contentLinears.get(i).orientation = LinearLayout.VERTICAL
                contentLinears.get(i).visibility = View.GONE
                val parmLinearHrstl = contentLinears.get(i).layoutParams as LinearLayout.LayoutParams
//                parmLinearHrstl.setMargins(30, 0, 30, 20)
                expandParam.indxCursor = i
                checkExpandeds.add(expandParam)

                val lblTxvPort = TextView(this)
                contentTxvPorts.add(lblTxvPort)
                contentTxvPorts.get(i).setText("Pelabuhan : ${dataList.get(i).portMapName}")
                contentTxvPorts.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmPort = contentTxvPorts.get(i).layoutParams as LinearLayout.LayoutParams
                parmPort.setMargins(30, 0, 30, 25)

                val txvLableCatch = TextView(this)
                contentTxvLabelCatch.add(txvLableCatch)
                contentTxvLabelCatch.get(i).setText("Data Tangkapan :")
                contentTxvLabelCatch.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                contentTxvLabelCatch.get(i).typeface = Typeface.DEFAULT_BOLD
                contentTxvLabelCatch.get(i).setTextColor(ContextCompat.getColor(this, R.color.title_result_catch_color_light))
                contentTxvLabelCatch.get(i).textSize = 16F
                val parmLableCatch = contentTxvLabelCatch.get(i).layoutParams as LinearLayout.LayoutParams
                parmLableCatch.setMargins(30, 0, 30, 10)

                val linearContntAnimalCatch = LinearLayout(this)
                contentLinearAnimalCatchs.add(linearContntAnimalCatch)
                contentLinearAnimalCatchs.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                contentLinearAnimalCatchs.get(i).orientation = LinearLayout.HORIZONTAL
                contentLinearAnimalCatchs.get(i).weightSum = 10F
                val parmLinearHrstl2 = contentLinearAnimalCatchs.get(i).layoutParams as LinearLayout.LayoutParams
                parmLinearHrstl2.setMargins(30, 0, 30, 0)

                val lblTxvAnimal = TextView(this)
                contentTxvAmounts.add(lblTxvAnimal)
                contentTxvAmounts.get(i).setText(dataList.get(i).animalName)
                contentTxvAmounts.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmlbl2 = contentTxvAmounts.get(i).layoutParams as LinearLayout.LayoutParams
//                    parmlbl2.setMargins(10, 0, 0, 0)
                parmlbl2.weight = 4F

                val txvWeight = TextView(this)
                contentTxvWeights.add(txvWeight)
                contentTxvWeights.get(i).setText("Berat : ${dataList.get(i).weightCapture} KG")
                contentTxvWeights.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                contentTxvWeights.get(i).gravity = Gravity.RIGHT
                val parmAmountVal = contentTxvWeights.get(i).layoutParams as LinearLayout.LayoutParams
//                    parmAmountVal.setMargins(10, 0, 0, 0)
                parmAmountVal.weight = 6F

                contentLinearAnimalCatchs.get(i).addView(contentTxvAmounts.get(i))
                contentLinearAnimalCatchs.get(i).addView(contentTxvWeights.get(i))

                val lblTxvPrice = TextView(this)
                contentTxvPrices.add(lblTxvPrice)
                contentTxvPrices.get(i).setText("Harga : Rp. ${dataList.get(i).price}")
                contentTxvPrices.get(i).gravity = Gravity.RIGHT
                contentTxvPrices.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmlbl3 = contentTxvPrices.get(i).layoutParams as LinearLayout.LayoutParams
                parmlbl3.setMargins(30, 0, 30, 10)

                val lblTxvPriceTot = TextView(this)
                contentTxvPricesTot.add(lblTxvPriceTot)
                contentTxvPricesTot.get(i).setText("Total : ${dataList.get(i).price}")
                contentTxvPricesTot.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmlbl4 = contentTxvPricesTot.get(i).layoutParams as LinearLayout.LayoutParams
                parmlbl4.setMargins(30, 0, 30, 10)

                val viewSeparator = View(this)
                contentViewSeparators.add(viewSeparator)
                contentViewSeparators.get(i).setBackgroundResource(R.color.gray2)
                contentViewSeparators.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,5)
                val parmView = contentViewSeparators.get(i).layoutParams as LinearLayout.LayoutParams
                parmView.setMargins(0,0,0,10)

                contentLinears.get(i).addView(contentTxvPorts.get(i))
                contentLinears.get(i).addView(contentTxvLabelCatch.get(i))
                contentLinears.get(i).addView(contentLinearAnimalCatchs.get(i))
                contentLinears.get(i).addView(contentTxvPrices.get(i))
                contentLinears.get(i).addView(contentTxvPricesTot.get(i))

                linears.get(i).addView(headerlinears.get(i))
                linears.get(i).addView(contentLinears.get(i))
                linears.get(i).addView(contentViewSeparators.get(i))

                binding.contentResultCatch.addView(linears.get(i))
            }
        }

    }

    private fun refreshGenContent(){
        if(linears != null){
            linears = ArrayList<LinearLayout>()
        }
        if(headerlinears != null){
            headerlinears = ArrayList<LinearLayout>()
        }
        if(headerImageArrows != null){
            headerImageArrows = ArrayList<ImageView>()
        }
        if(headerTxvTitles != null){
            headerTxvTitles = ArrayList<TextView>()
        }
        if(contentLinears != null){
            contentLinears = ArrayList<LinearLayout>()
        }
        if(contentTxvPorts != null){
            contentTxvPorts = ArrayList<TextView>()
        }
        if(contentTxvLabelCatch != null){
            contentTxvLabelCatch = ArrayList<TextView>()
        }
        if(contentLinearAnimalCatchs != null){
            contentLinearAnimalCatchs = ArrayList<LinearLayout>()
        }
        if(contentTxvAmounts != null){
            contentTxvAmounts = ArrayList<TextView>()
        }
        if(contentTxvWeights != null){
            contentTxvWeights = ArrayList<TextView>()
        }
        if(contentTxvPrices != null){
            contentTxvPrices = ArrayList<TextView>()
        }
        if(contentTxvPricesTot != null){
            contentTxvPricesTot = ArrayList<TextView>()
        }
        if(contentViewSeparators != null){
            contentViewSeparators = ArrayList<View>()
        }
        if(checkExpandeds != null){
            checkExpandeds = ArrayList<TrackClickCursor>()
        }

    }


    private fun dialogDateSearch(){
        val calendar = Calendar.getInstance()
        var yearNow = calendar.get(Calendar.YEAR)
        var monthNow = calendar.get(Calendar.MONTH)
        var dateNow = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val id : Locale = Locale ("in","ID")
//        val sdf : SimpleDateFormat = SimpleDateFormat("yyyy-mm-dd",id)
//        val datePickerDialog = DatePickerDialog(
//            this,
//            object : DatePickerDialog.OnDateSetListener {
//                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
//                    Log.d("asdas","asdasd")
//                }
//            },
//            yearNow,
//            monthNow,
//            dateNow
//        )
////        datePickerDialog.setTitle("select date")
//        datePickerDialog.show()


        val datePickerListener = DatePickerDialog.OnDateSetListener {
                view, selectedYear, selectedMonth, selectedDay ->

                // when dialog box is closed, below method will be called.
                if (isOkayClicked) {
//                    birthday.setText(
//                        selectedYear + (selectedMonth + 1)
//                                + selectedDay
//                    )
                    Toast.makeText(this,"aaaaaaaaaaaaa",Toast.LENGTH_LONG).show()
                    yearNow = selectedYear
                    monthNow = selectedMonth
                    dateNow = selectedDay
                }
                isOkayClicked = false
            }

        val datePickerDialog = DatePickerDialog(
            this, datePickerListener,
            yearNow, monthNow, dateNow
        )

        datePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            "cancel"
        ) { dialog, which ->
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel()
                isOkayClicked = false
            }
        }

        datePickerDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            "OK"
        ) { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                isOkayClicked = true
                val datePicker = datePickerDialog
                    .datePicker
                datePickerListener.onDateSet(
                    datePicker,
                    datePicker.year,
                    datePicker.month,
                    datePicker.dayOfMonth
                )
            }
        }
        datePickerDialog.setCancelable(false)
        datePickerDialog.show()

    }

//    private fun dialogDateSearch(){
//        var dialog = Dialog(this)
//        var _binding = DialogCalendarBinding.inflate(LayoutInflater.from(this))
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(_binding.root)
//
//        var yearChoose = Calendar.getInstance().get(Calendar.YEAR)
//        var txvYears : ArrayList<TextView> = ArrayList<TextView>()
//
//        for(i in 0..200){
//            var year = 1900 + i
//
//            val txvYear = TextView(dialog.context)
//            txvYears.add(txvYear)
//            txvYears.get(i).setText("${year}")
//            txvYears.get(i).textSize = 24F
//            txvYears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            val txvParm = txvYears.get(i).layoutParams as LinearLayout.LayoutParams
//            txvParm.setMargins(0,10,0,10)
//            txvParm.gravity = Gravity.CENTER_HORIZONTAL
//
//            if(year ==  yearChoose){
//                txvYears.get(i).requestFocus()
//                txvYears.get(i).setTextColor(ContextCompat.getColor(this,R.color.yellow))
//            }
//
//            txvYears.get(i).setOnClickListener {
//                yearChoose = txvYears.get(i).text.toString().toInt()
//                _binding.txvYearSelection.setText("${yearChoose}")
//            }
//
//            _binding.lnrYearCalendar.addView(txvYears.get(i))
//        }
//
//        _binding.tabCalendar.addTab(_binding.tabCalendar.newTab().setText("From"))
//        _binding.tabCalendar.addTab(_binding.tabCalendar.newTab().setText("To"))
//        _binding.tabCalendar.tabGravity = TabLayout.GRAVITY_FILL
//        _binding.tabCalendar.addOnTabSelectedListener(object : OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Toast.makeText(applicationContext,"${tab?.position}",Toast.LENGTH_LONG).show()
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {}
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//
//        })
//
//        _binding.txvMonthSelection.setOnClickListener {
//            _binding.txvMonthSelection.textSize = 28f
//            _binding.txvYearSelection.textSize = 18f
//            _binding.txvMonthSelection.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))
//            _binding.txvYearSelection.setTextColor(ContextCompat.getColor(applicationContext,R.color.transparent))
//            _binding.lnrMonthCalendar.visibility = View.VISIBLE
//            _binding.lnrYearCalendar.visibility = View.GONE
//
////            _binding.lnrMonthCalendar.removeAllViews()
////            if(datePickers != null){
////                datePickers = ArrayList<DatePicker>()
////            }
////
////            for(i in 0..200){
////                var year = 1900 + i
////
////                val datePicker = DatePicker(dialog.context)
////                datePickers.add(datePicker)
////                datePickers.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
////                val datePickerParm = datePickers.get(i).layoutParams as LinearLayout.LayoutParams
////                datePickerParm.setMargins(0,10,0,10)
//////                datePickerParm.gravity = Gravity.CENTER_HORIZONTAL
////
////                _binding.lnrMonthCalendar.addView(datePickers.get(i))
////            }
//
//        }
//
//        _binding.txvYearSelection.setOnClickListener {
//            _binding.txvMonthSelection.textSize = 18f
//            _binding.txvYearSelection.textSize = 28f
//            _binding.txvMonthSelection.setTextColor(ContextCompat.getColor(applicationContext,R.color.transparent))
//            _binding.txvYearSelection.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))
//            _binding.lnrMonthCalendar.visibility = View.GONE
//            _binding.lnrYearCalendar.visibility = View.VISIBLE
//
////            _binding.lnrYearCalendar.removeAllViews()
////            if(txvYears != null){
////                txvYears = ArrayList<TextView>()
////            }
////            if(lnrYears != null){
////                lnrYears = ArrayList<LinearLayout>()
////            }
////
////            for(i in 0..200){
////                var year = 1900 + i
////
////                val txvYear = TextView(dialog.context)
////                txvYears.add(txvYear)
////                txvYears.get(i).setText("${year}")
////                txvYears.get(i).textSize = 24F
////                txvYears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
////                val txvParm = txvYears.get(i).layoutParams as LinearLayout.LayoutParams
////                txvParm.setMargins(0,10,0,10)
////                txvParm.gravity = Gravity.CENTER_HORIZONTAL
////
////                if(year ==  yearChoose){
////                    txvYears.get(i).requestFocus()
////                    txvYears.get(i).setTextColor(ContextCompat.getColor(this,R.color.yellow))
////                }
////
////                txvYears.get(i).setOnClickListener {
////                    yearChoose = txvYears.get(i).text.toString().toInt()
////                    _binding.txvYearSelection.setText("${yearChoose}")
////                }
////
////                _binding.lnrYearCalendar.addView(txvYears.get(i))
////            }
//
//        }
//
//        _binding.btnOkCalendar.setOnClickListener {
//            val day = if(_binding.dpCalendar.dayOfMonth > 9) "0${_binding.dpCalendar.dayOfMonth}" else "${_binding.dpCalendar.dayOfMonth}"
//            val month = if(_binding.dpCalendar.month > 9) "0${_binding.dpCalendar.month}" else "${_binding.dpCalendar.month}"
//            val year = if(_binding.dpCalendar.year > 9) "0${_binding.dpCalendar.year}" else "${_binding.dpCalendar.year}"
//            searchParm.dateFrom = "${year}-${month}-${day}"
//            Toast.makeText(this,"${searchParm.dateFrom}",Toast.LENGTH_LONG).show()
//
////            viewModel.getResultCacth(ResultStateValue.getResultCatch,searchParm)
//        }
//
//        _binding.btnCancleCalendar.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }


}