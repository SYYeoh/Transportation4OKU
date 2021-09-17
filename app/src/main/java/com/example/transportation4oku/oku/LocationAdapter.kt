package com.example.transportation4oku.oku

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.transportation4oku.R

class LocationAdapter(private val locationClassList : ArrayList<LocationClass>):RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.location_list_item,
            parent,false)
        return MyViewHolder(itemView,mListener)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val locationClass : LocationClass = locationClassList[position]
        holder.name.text = locationClass.Name
    }
    override fun getItemCount(): Int {
        return locationClassList.size
    }
    public class MyViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.txtType)
        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}