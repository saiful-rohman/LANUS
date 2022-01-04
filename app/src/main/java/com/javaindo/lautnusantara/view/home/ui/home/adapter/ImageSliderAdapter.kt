package com.javaindo.lautnusantara.view.home.ui.home.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javaindo.lautnusantara.databinding.ContentSliderItemBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class ImageSliderAdapter (val dataList : ArrayList<Int>,val context: Context)
    : SliderViewAdapter<ImageSliderAdapter.ImageSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): ImageSlideViewHolder {
        val _binding = ContentSliderItemBinding.inflate(LayoutInflater.from(parent!!.context))
        return ImageSlideViewHolder(_binding)
    }

    override fun onBindViewHolder(holder: ImageSlideViewHolder, position: Int) {
        val strImg = dataList.get(position)
        with(holder){
            binding.imgViewSlide.setImageResource(strImg)

            binding.imgViewSlide.setOnClickListener {
                if(position == 0){
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.co.id"))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else if(position == 1){
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://klik.digital/"))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else if(position == 2){
                    val appPackageName : String = "com.klik.lautnusantara"
                    try {
//                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${appPackageName}")))
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${appPackageName}"))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
//                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${appPackageName}")))
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${appPackageName}"))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }

            }
        }
    }

    class ImageSlideViewHolder(val binding : ContentSliderItemBinding)
        : SliderViewAdapter.ViewHolder(binding.root){
    }

    override fun getCount(): Int {
        return dataList.size
    }


}