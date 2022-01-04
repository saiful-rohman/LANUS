package com.javaindo.lautnusantara.view.LN_laut_pesisir_khusus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javaindo.lautnusantara.databinding.ContentLnpesisirkhususAdapterBinding
import com.javaindo.lautnusantara.model.LatLongModel
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class LNLautPesisirKhususAdapter_backup (diffCallback : DiffUtil.ItemCallback<LatLongModel>):
    PagingDataAdapter<LatLongModel,LNLautPesisirKhususAdapter_backup.LNViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LNViewHolder {
        val _binding = ContentLnpesisirkhususAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return LNViewHolder(_binding)
    }

    object latLongComparator : DiffUtil.ItemCallback<LatLongModel>(){
        override fun areItemsTheSame(oldItem: LatLongModel, newItem: LatLongModel) = oldItem.indx == newItem.indx

        override fun areContentsTheSame(oldItem: LatLongModel, newItem: LatLongModel) = oldItem == newItem

    }

    override fun onBindViewHolder(holder: LNViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class LNViewHolder(val binding_: ContentLnpesisirkhususAdapterBinding)
        : RecyclerView.ViewHolder(binding_.root){

        fun bind(data : LatLongModel){
            binding_.let {
                it.txvLongitude.text = data.longitude
                it.txvLongitudeD.text = data.longitudeD
                it.txvLongitudeM.text = data.longitudeM
                it.txvLongitudeS.text = data.longitudeS
                it.txvLatitude.text = data.latitude
                it.txvLatitudeD.text = data.latitudeD
                it.txvLatitudeM.text = data.latitudeM
                it.txvLatitudeS.text = data.latitudeS
            }
        }

    }


}