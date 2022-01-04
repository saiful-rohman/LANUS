package com.javaindo.lautnusantara.view.selling_price

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.databinding.ActivitySellingPriceBinding
import com.javaindo.lautnusantara.model.*
import com.javaindo.lautnusantara.utility.DataState
import com.javaindo.lautnusantara.viewmodel.SellingPriceStateEvent
import com.javaindo.lautnusantara.viewmodel.SellingPriceViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.text.Editable




@AndroidEntryPoint
class SellingPriceActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySellingPriceBinding
    private val viewModel : SellingPriceViewModel by viewModels()

    private var cards : ArrayList<CardView> = ArrayList<CardView>()
    private var linears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
//    private lateinit var sellPriceContent : LinearLayout

    private var headerlinears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var headerImageArrows : ArrayList<ImageView> = ArrayList<ImageView>()
    private var headerTxvTitles : ArrayList<TextView> = ArrayList<TextView>()

    private var contentLinears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var contentTxvAnimals : ArrayList<TextView> = ArrayList<TextView>()
    private var contentLinearMerchantPrices : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var contentTxvMerchantPrices : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvMerchantPriceVals : ArrayList<TextView> = ArrayList<TextView>()
    private var contentLinearAmounts : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var contentTxvAmounts : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvAmountVals : ArrayList<TextView> = ArrayList<TextView>()
    private var contentLinearNumberProduces : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var contentTxvNumberProduces : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvNumberProduceVals : ArrayList<TextView> = ArrayList<TextView>()

    private var otherPortLinears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var otherPortLinearSeeOtherPorts : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var otherPortTxvSeeOtherPorts : ArrayList<TextView> = ArrayList<TextView>()
    private var otherPortImageArrows : ArrayList<ImageView> = ArrayList<ImageView>()
    private var otherPortLinearContentOther : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var otherPortTxvPortNames : ArrayList<TextView> = ArrayList<TextView>()
    private var otherPortLinearMerchantPrices : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var otherPortTxvMerchantPrices : ArrayList<TextView> = ArrayList<TextView>()
    private var otherPortTxvMerchantPriceVals : ArrayList<TextView> = ArrayList<TextView>()
    private var otherPortLinearAmounts : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var otherPortTxvAmounts : ArrayList<TextView> = ArrayList<TextView>()
    private var otherPortTxvAmountVals : ArrayList<TextView> = ArrayList<TextView>()
    private var otherPortLinearNumberProduces : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var otherPortTxvNumberProduces : ArrayList<TextView> = ArrayList<TextView>()
    private var otherPortTxvNumberProduceVals : ArrayList<TextView> = ArrayList<TextView>()

    private var checkExpandeds : ArrayList<TrackClickCursor> = ArrayList<TrackClickCursor>()
    private var checkExpandedOthers : ArrayList<TrackClickCursor> = ArrayList<TrackClickCursor>()

    private var searchParm :SearchSellingPriceParmModel = SearchSellingPriceParmModel()
    private var isFirstSearch = false
//    private var portNameArray : ArrayList<String> = ArrayList<String>()
//    private var fishNameArray : ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellingPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPartData()
        subscribeObserve()
        processData()
        onCLick()
    }

    private fun processData(){
        viewModel.getSellPriceData(SellingPriceStateEvent.GetSellPrice,searchParm)
    }

    private fun setPartData(){
        binding.headerPage.imgLogoLN.setImageResource(R.drawable.ln_hargajual)
        binding.headerPage.txvTitleMenu.text = getString(R.string.text_title_LN_hargajual)

//        val adapterPortName = ArrayAdapter(this,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.portNames))
        binding.inlcdSellingPriceSearch.edtPortName.setAdapter(ArrayAdapter(this,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.portNames)))
        binding.inlcdSellingPriceSearch.edtFishName.setAdapter(ArrayAdapter(this,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.fishNames)))

        val datas = listOf("2021-12-28","2021-12-27","2021-12-26")
        val adapter = ArrayAdapter(this,R.layout.spinner_item,datas)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.inlcdSellingPriceSearch.spnrActivitySearch.adapter = adapter
        binding.inlcdSellingPriceSearch.spnrActivitySearch.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    private fun subscribeObserve(){
        viewModel.portMapList.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<PortMapModel>> -> {
                    generateContent(dataState.data)
                    binding.prgrsBarLoading.visibility = View.GONE
                    binding.contentSellPrice.visibility = View.VISIBLE
                }
                is DataState.Error ->{
                    binding.prgrsBarLoading.visibility = View.GONE
                    binding.contentSellPrice.visibility = View.VISIBLE
                    Toast.makeText(this,"${dataState.exception.message}",Toast.LENGTH_LONG).show()
                }
                is DataState.Loading ->{
                    binding.prgrsBarLoading.visibility = View.VISIBLE
                    binding.contentSellPrice.visibility = View.GONE
                }
            }
        })
    }

    private fun onCLick(){
        binding.inlcdSellingPriceSearch.edtFishName.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if(!binding.inlcdSellingPriceSearch.edtFishName.text.isNullOrEmpty()){
                    searchParm.fishName = binding.inlcdSellingPriceSearch.edtFishName.text.toString()
                    processData()
                }
            }
        })

        binding.inlcdSellingPriceSearch.edtPortName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if(!binding.inlcdSellingPriceSearch.edtPortName.text.isNullOrEmpty()){
                    searchParm.portName = binding.inlcdSellingPriceSearch.edtPortName.text.toString()
                    processData()
                }
            }
        })

        binding.inlcdSellingPriceSearch.spnrActivitySearch.onItemSelectedListener = object
            : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                searchParm.dateActivity = parent?.getItemAtPosition(position).toString()
                if(isFirstSearch){
                    processData()
                } else {
                    isFirstSearch = true
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("onNothingSelected","onNothingSelected")
            }

        }

        binding.headerPage.imgVBack.setOnClickListener {
            this.finish()
        }
    }

    private fun clearGenContent(){
        if(cards != null){
            cards = ArrayList<CardView>()
        }
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
        if(contentTxvAnimals != null){
            contentTxvAnimals = ArrayList<TextView>()
        }
        if(contentLinearMerchantPrices != null){
            contentLinearMerchantPrices = ArrayList<LinearLayout>()
        }
        if(contentTxvMerchantPrices != null){
            contentTxvMerchantPrices = ArrayList<TextView>()
        }
        if(contentTxvMerchantPriceVals != null){
            contentTxvMerchantPriceVals = ArrayList<TextView>()
        }
        if(contentLinearAmounts != null){
            contentLinearAmounts = ArrayList<LinearLayout>()
        }
        if(contentTxvAmounts != null){
            contentTxvAmounts = ArrayList<TextView>()
        }
        if(contentTxvAmountVals != null){
            contentTxvAmountVals = ArrayList<TextView>()
        }
        if(contentLinearNumberProduces != null){
            contentLinearNumberProduces = ArrayList<LinearLayout>()
        }
        if(contentTxvNumberProduces != null){
            contentTxvNumberProduces = ArrayList<TextView>()
        }
        if(contentTxvNumberProduceVals != null){
            contentTxvNumberProduceVals = ArrayList<TextView>()
        }
        if(otherPortLinears != null){
            otherPortLinears = ArrayList<LinearLayout>()
        }
        if(otherPortLinearSeeOtherPorts != null){
            otherPortLinearSeeOtherPorts = ArrayList<LinearLayout>()
        }
        if(otherPortTxvSeeOtherPorts != null){
            otherPortTxvSeeOtherPorts = ArrayList<TextView>()
        }
        if(otherPortImageArrows != null){
            otherPortImageArrows = ArrayList<ImageView>()
        }
        if(otherPortLinearContentOther != null){
            otherPortLinearContentOther = ArrayList<LinearLayout>()
        }
        if(otherPortTxvPortNames != null){
            otherPortTxvPortNames = ArrayList<TextView>()
        }
        if(otherPortLinearMerchantPrices != null){
            otherPortLinearMerchantPrices = ArrayList<LinearLayout>()
        }
        if(otherPortTxvMerchantPrices != null){
            otherPortTxvMerchantPrices = ArrayList<TextView>()
        }
        if(otherPortTxvMerchantPriceVals != null){
            otherPortTxvMerchantPriceVals = ArrayList<TextView>()
        }
        if(otherPortLinearAmounts != null){
            otherPortLinearAmounts = ArrayList<LinearLayout>()
        }
        if(otherPortTxvAmounts != null){
            otherPortTxvAmounts = ArrayList<TextView>()
        }
        if(otherPortTxvAmountVals != null){
            otherPortTxvAmountVals = ArrayList<TextView>()
        }
        if(otherPortLinearNumberProduces != null){
            otherPortLinearNumberProduces = ArrayList<LinearLayout>()
        }
        if(otherPortTxvNumberProduces != null){
            otherPortTxvNumberProduces = ArrayList<TextView>()
        }
        if(otherPortTxvNumberProduceVals != null){
            otherPortTxvNumberProduceVals = ArrayList<TextView>()
        }
        if(checkExpandeds != null){
            checkExpandeds = ArrayList<TrackClickCursor>()
        }
        if(checkExpandedOthers != null){
            checkExpandedOthers = ArrayList<TrackClickCursor>()
        }
//        if (binding.contentSellPrice.getParent() != null) {
//            (binding.contentSellPrice.getParent() as ViewGroup).removeView(binding.contentSellPrice) // <- fix
//        }
        binding.contentSellPrice.removeAllViews()
    }

    private fun generateContent(dataList : List<PortMapModel>){
        if(dataList != null && dataList.size > 0){
            clearGenContent()

            for(i in dataList.indices){
                var expandParam = TrackClickCursor()
                val linear = LinearLayout(this)
                linears.add(linear)
                linears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                linears.get(i).orientation = LinearLayout.VERTICAL
                linears.get(i).weightSum = 10F
                linears.get(i).setBackgroundResource(R.color.white)
                val paramsLin = linears.get(i).layoutParams as LinearLayout.LayoutParams
                paramsLin.setMargins(0, 20, 0, 0)

                val linearHdr = LinearLayout(this)
                headerlinears.add(linearHdr)
                headerlinears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150)
                headerlinears.get(i).orientation = LinearLayout.HORIZONTAL
                headerlinears.get(i).weightSum = 10F
                headerlinears.get(i).setBackgroundResource(R.color.white)
                val paramsLinCatSpar = headerlinears.get(i).layoutParams as LinearLayout.LayoutParams
                paramsLinCatSpar.setMargins(30, 0, 30, 20)

                val cardHdr = CardView(this)
                cards.add(cardHdr)
                cards.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                cards.get(i).setBackgroundResource(R.color.white)
                cards.get(i).cardElevation = 10F
                cards.get(i).radius = 10F
                cards.get(i).setCardBackgroundColor(Color.MAGENTA)
                val paramsCardCatSpar = cards.get(i).layoutParams as LinearLayout.LayoutParams
                paramsCardCatSpar.setMargins(50, 60, 50, 10)

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
                            contentLinears.get(trackIsExpand.indxCursor).visibility = View.GONE
                            checkExpandeds.get(i).isExpand = false
                        } else {
                            headerImageArrows.get(i).setImageResource(R.drawable.ic_up_arrow)
                            contentLinears.get(trackIsExpand.indxCursor).visibility = View.VISIBLE
                            checkExpandeds.get(i).isExpand = true
                        }
                    }
                }

//                portNameArray.add(dataList.get(i).portMapName)
                val txvTitleHdr = TextView(this)
                headerTxvTitles.add(txvTitleHdr)
                headerTxvTitles.get(i).setText(dataList.get(i).portMapName)
                headerTxvTitles.get(i).typeface = Typeface.DEFAULT_BOLD
//            headerTxvTitles.get(i).setTextColor(ContextCompat.getColor(applicationContext!!, R.color.txt_category))
//            headerTxvTitles.get(i).setPadding(20, 0, 0, 0)
//            headerTxvTitles.get(i).setTextSize(16f)
                //txvSubCategoryNameList.get(indexItem).setTextAppearance(activity,R.style.font_roboto_regular)
                headerTxvTitles.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmTitle = headerTxvTitles.get(i).layoutParams as LinearLayout.LayoutParams
                parmTitle.setMargins(0, 0, 100, 0)
                parmTitle.weight = 6F
                parmTitle.gravity = Gravity.CENTER_VERTICAL

                headerlinears.get(i).addView(headerTxvTitles.get(i))
                headerlinears.get(i).addView(headerImageArrows.get(i))

                linears.get(i).addView(headerlinears.get(i))

                val subDatas = dataList.get(i).sellPriceList
                if(subDatas != null && subDatas.size > 0){
                    headerImageArrows.get(i).visibility = View.VISIBLE

                    val linearContnts = LinearLayout(this)
                    contentLinears.add(linearContnts)
                    contentLinears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    contentLinears.get(i).orientation = LinearLayout.VERTICAL
                    contentLinears.get(i).visibility = View.GONE
                    val parmLinearHrstl = contentLinears.get(i).layoutParams as LinearLayout.LayoutParams
//                parmLinearHrstl.setMargins(30, 0, 30, 20)
                    expandParam.indxCursor = i
                    checkExpandeds.add(expandParam)

                    for(k in subDatas.indices){
                        val subData = subDatas.get(k)
                        val indexItem = subData.positionIndex

//                        fishNameArray.add(subData.animalName)
                        val lblTxvAnimal = TextView(this)
                        contentTxvAnimals.add(lblTxvAnimal)
                        contentTxvAnimals.get(indexItem).setText(subData.animalName)
                        contentTxvAnimals.get(indexItem).typeface = Typeface.DEFAULT_BOLD
                        contentTxvAnimals.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        val parmAnimal = contentTxvAnimals.get(indexItem).layoutParams as LinearLayout.LayoutParams
                        parmAnimal.setMargins(30, 0, 30, 25)


                        val linearContntMerchantPrice = LinearLayout(this)
                        contentLinearMerchantPrices.add(linearContntMerchantPrice)
                        contentLinearMerchantPrices.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        contentLinearMerchantPrices.get(indexItem).orientation = LinearLayout.HORIZONTAL
                        contentLinearMerchantPrices.get(indexItem).weightSum = 10F
                        val parmLinearMerchant = contentLinearMerchantPrices.get(indexItem).layoutParams as LinearLayout.LayoutParams
                        parmLinearMerchant.setMargins(30, 0, 30, 0)

                        val lblTxv1 = TextView(this)
                        contentTxvMerchantPrices.add(lblTxv1)
                        contentTxvMerchantPrices.get(indexItem).setText("Harga Pedagang(Rp)")
                        contentTxvMerchantPrices.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        val parmlbl1 = contentTxvMerchantPrices.get(indexItem).layoutParams as LinearLayout.LayoutParams
//                    parmlbl1.setMargins(10, 0, 0, 0)
                        parmlbl1.weight = 4F

                        val txvMerchantPrice = TextView(this)
                        contentTxvMerchantPriceVals.add(txvMerchantPrice)
                        contentTxvMerchantPriceVals.get(indexItem).setText(subData.traderPrice)
                        contentTxvMerchantPriceVals.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        contentTxvMerchantPriceVals.get(indexItem).gravity = Gravity.RIGHT
                        val parmMerchPriceVal = contentTxvMerchantPriceVals.get(indexItem).layoutParams as LinearLayout.LayoutParams
//                    parmMerchPriceVal.setMargins(10, 0, 0, 0)
                        parmMerchPriceVal.weight = 6F

                        contentLinearMerchantPrices.get(indexItem).addView(contentTxvMerchantPrices.get(indexItem))
                        contentLinearMerchantPrices.get(indexItem).addView(contentTxvMerchantPriceVals.get(indexItem))

                        val linearContntHrstl2 = LinearLayout(this)
                        contentLinearAmounts.add(linearContntHrstl2)
                        contentLinearAmounts.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        contentLinearAmounts.get(indexItem).orientation = LinearLayout.HORIZONTAL
                        contentLinearAmounts.get(indexItem).weightSum = 10F
                        val parmLinearHrstl2 = contentLinearAmounts.get(indexItem).layoutParams as LinearLayout.LayoutParams
                        parmLinearHrstl2.setMargins(30, 0, 30, 0)

                        val lblTxv2 = TextView(this)
                        contentTxvAmounts.add(lblTxv2)
                        contentTxvAmounts.get(indexItem).setText("Jumlag(Kg)")
                        contentTxvAmounts.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        val parmlbl2 = contentTxvAmounts.get(indexItem).layoutParams as LinearLayout.LayoutParams
//                    parmlbl2.setMargins(10, 0, 0, 0)
                        parmlbl2.weight = 4F

                        val txvAmount = TextView(this)
                        contentTxvAmountVals.add(txvAmount)
                        contentTxvAmountVals.get(indexItem).setText(subData.amount)
                        contentTxvAmountVals.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        contentTxvAmountVals.get(indexItem).gravity = Gravity.RIGHT
                        val parmAmountVal = contentTxvAmountVals.get(indexItem).layoutParams as LinearLayout.LayoutParams
//                    parmAmountVal.setMargins(10, 0, 0, 0)
                        parmAmountVal.weight = 6F

                        contentLinearAmounts.get(indexItem).addView(contentTxvAmounts.get(indexItem))
                        contentLinearAmounts.get(indexItem).addView(contentTxvAmountVals.get(indexItem))

                        val linearContntHrstl3 = LinearLayout(this)
                        contentLinearNumberProduces.add(linearContntHrstl3)
                        contentLinearNumberProduces.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        contentLinearNumberProduces.get(indexItem).orientation = LinearLayout.HORIZONTAL
                        contentLinearNumberProduces.get(indexItem).weightSum = 10F
                        val parmLinearHrstl3 = contentLinearNumberProduces.get(indexItem).layoutParams as LinearLayout.LayoutParams
                        parmLinearHrstl3.setMargins(30, 0, 30, 30)

                        val lblTxv3 = TextView(this)
                        contentTxvNumberProduces.add(lblTxv3)
                        contentTxvNumberProduces.get(indexItem).setText("Jumlah Produsen")
                        contentTxvNumberProduces.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        val parmlbl3 = contentTxvNumberProduces.get(indexItem).layoutParams as LinearLayout.LayoutParams
//                    parmlbl3.setMargins(10, 0, 0, 0)
                        parmlbl3.weight = 4F

                        val txvNumberProduce = TextView(this)
                        contentTxvNumberProduceVals.add(txvNumberProduce)
                        contentTxvNumberProduceVals.get(indexItem).setText(subData.amountProduce)
                        contentTxvNumberProduceVals.get(indexItem).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        contentTxvNumberProduceVals.get(indexItem).gravity = Gravity.RIGHT
                        val parmNumberProduceVal = contentTxvNumberProduceVals.get(indexItem).layoutParams as LinearLayout.LayoutParams
//                    parmNumberProduceVal.setMargins(10, 0, 0, 0)
                        parmNumberProduceVal.weight = 6F

                        contentLinearNumberProduces.get(indexItem).addView(contentTxvNumberProduces.get(indexItem))
                        contentLinearNumberProduces.get(indexItem).addView(contentTxvNumberProduceVals.get(indexItem))

                        contentLinears.get(i).addView(contentTxvAnimals.get(indexItem))
                        contentLinears.get(i).addView(contentLinearMerchantPrices.get(indexItem))
                        contentLinears.get(i).addView(contentLinearAmounts.get(indexItem))
                        contentLinears.get(i).addView(contentLinearNumberProduces.get(indexItem))

                        //See Other
                        val otherData = subDatas.get(k).sellPriceOther
                        if(otherData != null){
                            val indxOthr = otherData.positionIndex
                            var expandParamOther = TrackClickCursor()

                            val linearContntSeeOtherPort = LinearLayout(this)
                            otherPortLinears.add(linearContntSeeOtherPort)
                            otherPortLinears.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortLinears.get(indxOthr).orientation = LinearLayout.VERTICAL
                            otherPortLinears.get(indxOthr).weightSum = 10F
                            otherPortLinears.get(indxOthr).setBackgroundResource(R.color.white)
                            val paramsLinSeeOther = otherPortLinears.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                        paramsLinSeeOther.setMargins(30, 0, 30, 20)

                            val linearHdrOther = LinearLayout(this)
                            otherPortLinearSeeOtherPorts.add(linearHdrOther)
                            otherPortLinearSeeOtherPorts.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150)
                            otherPortLinearSeeOtherPorts.get(indxOthr).orientation = LinearLayout.HORIZONTAL
                            otherPortLinearSeeOtherPorts.get(indxOthr).weightSum = 10F
                            otherPortLinearSeeOtherPorts.get(indxOthr).setBackgroundResource(R.color.gray)
                            val paramsLinOther = otherPortLinearSeeOtherPorts.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                        paramsLinOther.setMargins(30, 0, 30, 20)

                            val arrowImgSeeOtherPort = ImageView(this)
                            otherPortImageArrows.add(arrowImgSeeOtherPort)
                            otherPortImageArrows.get(indxOthr).layoutParams = LinearLayout.LayoutParams(90,80)
                            otherPortImageArrows.get(indxOthr).scaleType = ImageView.ScaleType.CENTER_CROP
                            otherPortImageArrows.get(indxOthr).setPadding(10,10,10,10)
                            otherPortImageArrows.get(indxOthr).setImageResource(R.drawable.ic_arrow_down)
                            val layoutParmSeeOtherPort = otherPortImageArrows.get(indxOthr).layoutParams as LinearLayout.LayoutParams
                            layoutParmSeeOtherPort.setMargins(0, 0, 30, 0)
                            layoutParmSeeOtherPort.weight = 4F
                            layoutParmSeeOtherPort.gravity = Gravity.CENTER_VERTICAL

                            otherPortImageArrows.get(indxOthr).setOnClickListener {
                                var trackIsExpandOther = TrackClickCursor()
                                var checkOther = false
                                for(k in checkExpandedOthers.indices){
                                    if(indxOthr == checkExpandedOthers.get(k).indxCursor){
                                        trackIsExpandOther = checkExpandedOthers.get(k)
                                        checkOther = true
                                        break
                                    }
                                }

                                if(checkOther){
                                    if(trackIsExpandOther.isExpand){
                                        otherPortImageArrows.get(indxOthr).setImageResource(R.drawable.ic_arrow_down)
                                        otherPortLinearContentOther.get(trackIsExpandOther.indxCursor).visibility = View.GONE
                                        checkExpandedOthers.get(indxOthr).isExpand = false
                                    } else {
                                        otherPortImageArrows.get(indxOthr).setImageResource(R.drawable.ic_up_arrow)
                                        otherPortLinearContentOther.get(trackIsExpandOther.indxCursor).visibility = View.VISIBLE
                                        checkExpandedOthers.get(indxOthr).isExpand = true
                                    }
                                }
                            }

                            val lblTxvSeeOtherPort = TextView(this)
                            otherPortTxvSeeOtherPorts.add(lblTxvSeeOtherPort)
                            otherPortTxvSeeOtherPorts.get(indxOthr).setText("Lihat Pelabuhan Lain")
                            otherPortTxvSeeOtherPorts.get(indxOthr).typeface = Typeface.DEFAULT_BOLD
                            otherPortTxvSeeOtherPorts.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            val parmlblSeeOtherPort = otherPortTxvSeeOtherPorts.get(indxOthr).layoutParams as LinearLayout.LayoutParams
                            parmlblSeeOtherPort.setMargins(30, 0, 0, 0)
                            parmlblSeeOtherPort.weight = 6F
                            parmlblSeeOtherPort.gravity = Gravity.CENTER_VERTICAL

                            otherPortLinearSeeOtherPorts.get(indxOthr).addView(otherPortTxvSeeOtherPorts.get(indxOthr))
                            otherPortLinearSeeOtherPorts.get(indxOthr).addView(otherPortImageArrows.get(indxOthr))

                            otherPortLinears.get(indxOthr).addView(otherPortLinearSeeOtherPorts.get(indxOthr))

                            val linearseeOtherContent = LinearLayout(this)
                            otherPortLinearContentOther.add(linearseeOtherContent)
                            otherPortLinearContentOther.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortLinearContentOther.get(indxOthr).orientation = LinearLayout.VERTICAL
                            otherPortLinearContentOther.get(indxOthr).setBackgroundResource(R.color.gray)
                            otherPortLinearContentOther.get(indxOthr).visibility = View.GONE
                            val parmLinearOtherContent = otherPortLinearContentOther.get(indxOthr).layoutParams as LinearLayout.LayoutParams
                            parmLinearOtherContent.setMargins(0, 0, 0, 25)

                            expandParamOther.indxCursor = indxOthr
                            checkExpandedOthers.add(expandParamOther)

                            val othertxvPortName = TextView(this)
                            otherPortTxvPortNames.add(othertxvPortName)
                            otherPortTxvPortNames.get(indxOthr).setText(dataList.get(i).portMapName)
                            otherPortTxvPortNames.get(indxOthr).typeface = Typeface.DEFAULT_BOLD
                            otherPortTxvPortNames.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            val parmOtherPortMap = otherPortTxvPortNames.get(indxOthr).layoutParams as LinearLayout.LayoutParams
                            parmOtherPortMap.setMargins(30, 0, 30, 25)

                            val linearseeOtherMerchantPrice = LinearLayout(this)
                            otherPortLinearMerchantPrices.add(linearseeOtherMerchantPrice)
                            otherPortLinearMerchantPrices.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortLinearMerchantPrices.get(indxOthr).orientation = LinearLayout.HORIZONTAL
                            otherPortLinearMerchantPrices.get(indxOthr).weightSum = 10F
                            val parmLinearOtherMerchant = otherPortLinearMerchantPrices.get(indxOthr).layoutParams as LinearLayout.LayoutParams
                            parmLinearOtherMerchant.setMargins(30, 0, 30, 0)

                            val otherlblTxv1 = TextView(this)
                            otherPortTxvMerchantPrices.add(otherlblTxv1)
                            otherPortTxvMerchantPrices.get(indxOthr).setText("Harga Pedagang(Rp)")
                            otherPortTxvMerchantPrices.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            val parmlbl1Other = otherPortTxvMerchantPrices.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                    parmlbl1Other.setMargins(10, 0, 0, 0)
                            parmlbl1Other.weight = 4F

                            val othertxvMerchantPrice = TextView(this)
                            otherPortTxvMerchantPriceVals.add(othertxvMerchantPrice)
                            otherPortTxvMerchantPriceVals.get(indxOthr).setText(otherData.traderPriceOther)
                            otherPortTxvMerchantPriceVals.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortTxvMerchantPriceVals.get(indxOthr).gravity = Gravity.RIGHT
                            val parmMerchPriceValOther = otherPortTxvMerchantPriceVals.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                    parmMerchPriceValOther.setMargins(10, 0, 0, 0)
                            parmMerchPriceValOther.weight = 6F

                            otherPortLinearMerchantPrices.get(indxOthr).addView(otherPortTxvMerchantPrices.get(indxOthr))
                            otherPortLinearMerchantPrices.get(indxOthr).addView(otherPortTxvMerchantPriceVals.get(indxOthr))
//
                            val linearContntHrstl2Other = LinearLayout(this)
                            otherPortLinearAmounts.add(linearContntHrstl2Other)
                            otherPortLinearAmounts.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortLinearAmounts.get(indxOthr).orientation = LinearLayout.HORIZONTAL
                            otherPortLinearAmounts.get(indxOthr).weightSum = 10F
                            val parmLinearHrstl2Other = otherPortLinearAmounts.get(indxOthr).layoutParams as LinearLayout.LayoutParams
                            parmLinearHrstl2Other.setMargins(30, 0, 30, 0)

                            val lblTxv2Other = TextView(this)
                            otherPortTxvAmounts.add(lblTxv2Other)
                            otherPortTxvAmounts.get(indxOthr).setText("Jumlag(Kg)")
                            otherPortTxvAmounts.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            val parmlbl2Other = otherPortTxvAmounts.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                    parmlbl2Other.setMargins(10, 0, 0, 0)
                            parmlbl2Other.weight = 4F

                            val txvAmountOther = TextView(this)
                            otherPortTxvAmountVals.add(txvAmountOther)
                            otherPortTxvAmountVals.get(indxOthr).setText(otherData.amountOther)
                            otherPortTxvAmountVals.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortTxvAmountVals.get(indxOthr).gravity = Gravity.RIGHT
                            val parmAmountValOther = otherPortTxvAmountVals.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                    parmAmountValOther.setMargins(10, 0, 0, 0)
                            parmAmountValOther.weight = 6F

                            otherPortLinearAmounts.get(indxOthr).addView(otherPortTxvAmounts.get(indxOthr))
                            otherPortLinearAmounts.get(indxOthr).addView(otherPortTxvAmountVals.get(indxOthr))

                            val linearContntHrstl3Other = LinearLayout(this)
                            otherPortLinearNumberProduces.add(linearContntHrstl3Other)
                            otherPortLinearNumberProduces.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortLinearNumberProduces.get(indxOthr).orientation = LinearLayout.HORIZONTAL
                            otherPortLinearNumberProduces.get(indxOthr).weightSum = 10F
                            val parmLinearHrstl3Other = otherPortLinearNumberProduces.get(indxOthr).layoutParams as LinearLayout.LayoutParams
                            parmLinearHrstl3Other.setMargins(30, 0, 30, 30)

                            val lblTxv3Other = TextView(this)
                            otherPortTxvNumberProduces.add(lblTxv3Other)
                            otherPortTxvNumberProduces.get(indxOthr).setText("Jumlah Produsen")
                            otherPortTxvNumberProduces.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            val parmlbl3Other = otherPortTxvNumberProduces.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                    parmlbl3Other.setMargins(10, 0, 0, 0)
                            parmlbl3Other.weight = 4F

                            val txvNumberProduceOther = TextView(this)
                            otherPortTxvNumberProduceVals.add(txvNumberProduceOther)
                            otherPortTxvNumberProduceVals.get(indxOthr).setText(otherData.amountProduceOther)
                            otherPortTxvNumberProduceVals.get(indxOthr).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                            otherPortTxvNumberProduceVals.get(indxOthr).gravity = Gravity.RIGHT
                            val parmNumberProduceValOther = otherPortTxvNumberProduceVals.get(indxOthr).layoutParams as LinearLayout.LayoutParams
//                    parmNumberProduceValOther.setMargins(10, 0, 0, 0)
                            parmNumberProduceValOther.weight = 6F

                            otherPortLinearNumberProduces.get(indxOthr).addView(otherPortTxvNumberProduces.get(indxOthr))
                            otherPortLinearNumberProduces.get(indxOthr).addView(otherPortTxvNumberProduceVals.get(indxOthr))

                            otherPortLinearContentOther.get(indxOthr).addView(otherPortTxvPortNames.get(indxOthr))
                            otherPortLinearContentOther.get(indxOthr).addView(otherPortLinearMerchantPrices.get(indxOthr))
                            otherPortLinearContentOther.get(indxOthr).addView(otherPortLinearAmounts.get(indxOthr))
                            otherPortLinearContentOther.get(indxOthr).addView(otherPortLinearNumberProduces.get(indxOthr))

                            otherPortLinears.get(indxOthr).addView(otherPortLinearContentOther.get(indxOthr))
                            contentLinears.get(i).addView(otherPortLinears.get(indxOthr))
                        }
                    }
                    linears.get(i).addView(contentLinears.get(i))
                } else {
                    headerImageArrows.get(i).visibility = View.INVISIBLE
                }

                cards.get(i).addView(linears.get(i))
                binding.contentSellPrice.addView(cards.get(i))
            }
        } else {
            clearGenContent()
        }

    }
}