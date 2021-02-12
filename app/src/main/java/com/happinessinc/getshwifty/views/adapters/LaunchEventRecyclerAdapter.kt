package com.happinessinc.getshwifty.views.adapters

import android.location.Location
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.happinessinc.getshwifty.R
import com.happinessinc.getshwifty.databinding.ItemLaunchEventBinding
import com.happinessinc.getshwifty.repo.models.LaunchEvent

/**
 * Created by Ak on 18/07/20.
 */

/**
 * Created by Ak on 2020-02-10.
 */



class LaunchEventRecyclerAdapter: RecyclerView.Adapter<LaunchEventRecyclerAdapter.ViewHolder>() {
    interface eventListner{
        fun onItemClick( event: LaunchEvent)
    }
    val displayData= mutableListOf<LaunchEvent>()

    var  listner:eventListner?=null




    fun clearData(){
        this.displayData.clear()
    }

    fun addList(newData:MutableList<LaunchEvent>){
        clearData()
        if(newData.size>0) {
            this.displayData.addAll(newData)
        }
        notifyDataSetChanged()
    }

    fun setOrderClickListner(lis:eventListner){
        this.listner=lis
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.getContext())
        val itemBinding: ItemLaunchEventBinding = DataBindingUtil.inflate(inflater!!,
            R.layout.item_launch_event,parent,false)

        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return  displayData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event= displayData.get(position)
        holder.bind(event)
    }




    inner class ViewHolder(val binding: ItemLaunchEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LaunchEvent) {
            binding.launchEvent=item!!
            binding.executePendingBindings()
        }
    }
}