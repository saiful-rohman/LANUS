package com.javaindo.lautnusantara.view.faq

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.databinding.ActivityFaqactivityBinding
import com.javaindo.lautnusantara.model.FAQModel
import com.javaindo.lautnusantara.model.TrackClickCursor

class FAQActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFaqactivityBinding

    private var linears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()

    private var headerlinears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var headerImageArrows : ArrayList<ImageView> = ArrayList<ImageView>()
    private var headerTxvQuestion : ArrayList<TextView> = ArrayList<TextView>()
    private var contentTxvAnwser : ArrayList<TextView> = ArrayList<TextView>()
    private var contentLinears : ArrayList<LinearLayout> = ArrayList<LinearLayout>()
    private var contentViewSeparators : ArrayList<View> = ArrayList<View>()

    private var checkExpandeds : ArrayList<TrackClickCursor> = ArrayList<TrackClickCursor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateContent(generateDummy())
        onCLick()
    }

    private fun onCLick(){
        binding.headerPage.imgVBackHelpCenter.setOnClickListener {
            this.finish()
        }
    }

    private fun generateContent(dataList : ArrayList<FAQModel>){
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
                headerlinears.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
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
                            contentLinears.get(trackIsExpand.indxCursor).visibility = View.GONE
                            checkExpandeds.get(i).isExpand = false
                        } else {
                            headerImageArrows.get(i).setImageResource(R.drawable.ic_up_arrow)
                            contentLinears.get(trackIsExpand.indxCursor).visibility = View.VISIBLE
                            checkExpandeds.get(i).isExpand = true
                        }
                    }
                }

                val txvQuestion = TextView(this)
                headerTxvQuestion.add(txvQuestion)
                headerTxvQuestion.get(i).setText(dataList.get(i).question)
                headerTxvQuestion.get(i).setTextColor(ContextCompat.getColor(applicationContext!!, R.color.title_result_catch_color))
//            headerTxvTitles.get(i).setPadding(20, 0, 0, 0)
                headerTxvQuestion.get(i).setTextSize(16f)
//            headerTxvQuestion.get(i).typeface = Typeface.DEFAULT_BOLD
                headerTxvQuestion.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmTitle = headerTxvQuestion.get(i).layoutParams as LinearLayout.LayoutParams
                parmTitle.setMargins(0, 0, 100, 0)
                parmTitle.weight = 6F
                parmTitle.gravity = Gravity.CENTER_VERTICAL

                headerlinears.get(i).addView(headerTxvQuestion.get(i))
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

                val lblTxvAnswer = TextView(this)
                contentTxvAnwser.add(lblTxvAnswer)
                contentTxvAnwser.get(i).setText(dataList.get(i).answer)
                contentTxvAnwser.get(i).setTextSize(14f)
                contentTxvAnwser.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                val parmPort = contentTxvAnwser.get(i).layoutParams as LinearLayout.LayoutParams
                parmPort.setMargins(30, 0, 30, 25)

                val viewSeparator = View(this)
                contentViewSeparators.add(viewSeparator)
                contentViewSeparators.get(i).setBackgroundResource(R.color.gray2)
                contentViewSeparators.get(i).layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2)
                val parmView = contentViewSeparators.get(i).layoutParams as LinearLayout.LayoutParams
                parmView.setMargins(0,10,0,20)

                contentLinears.get(i).addView(contentTxvAnwser.get(i))

                linears.get(i).addView(headerlinears.get(i))
                linears.get(i).addView(contentLinears.get(i))
                linears.get(i).addView(contentViewSeparators.get(i))

                binding.lnrFAQList.addView(linears.get(i))
            }
        }

    }

    private fun generateDummy() : ArrayList<FAQModel>{
        var result : ArrayList<FAQModel> = ArrayList<FAQModel>()

        val rc = FAQModel()
        rc.question = "Apakah aplikasi Laut Nusantara?"
        rc.answer = "Aplikasi Laut Nusantara adalah sebuah aplikasi berbasis Android yang dibangun " +
                "oleh PT XL Axiata Tbk (XL Axiata) bagi masyarakat Indonesia, terutama para nelayan" +
                " diseluruh penjuru Nusantara."
        result.add(rc)

        val rc2 = FAQModel()
        rc2.question = "Apakah Tujuan Aplikasi Laut Nusantara ini dibuat?"
        rc2.answer = "Tujuan membangun aplikasi Laut Nusantara ini didasarkan atas pertimbangan " +
                "bahwa indonesia merupakan negara kepualauan terbesar didunia dengan 2/3 wilayahnya " +
                "merupakan lautan. Selain itu, total garis pantai yang di miliki indonesia mencapai " +
                "lebih dari 95.000 km persegi, dan luas hamparan terumbu karang hingga 24,5 juta Ha. " +
                "selain itu, Indonesia juga masih menyimpan potensi kelautan lainnya yang juga melimpah " +
                ", terutama berupa sumberdaya ikan (SDI), mineral dan bahan tambang, serta potensi " +
                "transportasi/perhubungan."
        rc2.positionIndex = 1
        result.add(rc2)

        val rc3 = FAQModel()
        rc3.question = "Apa saja manfaat yang bisa didapatkan pada aplikasi Laut Nusantara?"
        rc3.answer = "Manfaat yang bisa didapatkan pada aplikasi Laut Nusantara ini, pengguna bisa " +
                "mendapatkan informasi mengenai sebaran ikan berdasarkan jenisnya. secara keseluruhan " +
                "Aplikasi Laut Nusantara mempunyai nilai manfaat yang lebih luas dibanding aplikasi-aplikasi " +
                "sejenis."
        rc3.positionIndex = 2
        result.add(rc3)

        val rc4 = FAQModel()
        rc4.question = "Apa perbedaan aplikasi Laut Nusantara dengan aplikasi kelautan lainnya?"
        rc4.answer = "perbedaan aplikasi Laut Nusantara dengan aplikasi kelautan lainnya adalah , fitur-fitur " +
                "yang tersaji pada Aplikasi LAut Nusantara sangat update dan akurat karena data-datanya " +
                "didapat langsung dari Balai Riset dan Observasi Laut pada kementrian kelautan dan perikanan " +
                "Republik Indonesia melalui jalur khusus atau tunneling. Kelahiran aplikasi ini memang " +
                "tidak terlepas dari dukungan yang diberikan oleh instansi Balai Riset dan Observasi Laut dan " +
                "kementrian dan kelautan. Semua informasi kelautan yang terdapat dalam aplikasi Laut Nusantara " +
                "ini didapat secara langsung dari stasiun bumi Balai Risetdan Observasi Laut, sehingga tidak diragukan " +
                "keakuratannya."
        rc4.positionIndex = 3
        result.add(rc4)

        val rc5 = FAQModel()
        rc5.question = "Informasi apa saja yang tersedia diaplikasi Laut Nusantara?"
        rc5.answer = "Informasi yang tersedia di aplikasi Laut Nusantara antara lain daerah penangkapan " +
                "ikan , data cuaca(arah angin, kecepatan angin, cuaca, tinggi gelombang pada koordinat " +
                "tertentu, status laut (aman, waspada dan bahaya) untuk melaut, lapor tangkapan, gelombang " +
                "perairan, harga ikan pelabuhan, nama ikan dan 3 penamaan (Daerah Inggris dan Latin), informasi " +
                "pelabuhan, simulasi penggunaan BBM, Hitung biaya BBM, Chatting)."
        rc5.positionIndex = 4
        result.add(rc5)

        return result
    }


}