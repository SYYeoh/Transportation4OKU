package com.example.transportation4oku.caregiver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R

data class CGBookingAcceptedAdapter(private val acceptedDetailList : ArrayList<BookingDetail>)
    : RecyclerView.Adapter<CGBookingAcceptedAdapter.MyViewHolder>(){
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_cgacceptedbooking,
            parent,false)

        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val acceptedDetail : BookingDetail = acceptedDetailList[position]

        holder.from.text = acceptedDetail.from
        holder.id.text = acceptedDetail.id.toString()
        holder.to.text = acceptedDetail.to
        holder.date.text = acceptedDetail.date
        holder.time.text = acceptedDetail.time

    }

    override fun getItemCount(): Int {
        return acceptedDetailList.size
    }

    public class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val to : TextView = itemView.findViewById(R.id.tvTo)
        val from : TextView = itemView.findViewById(R.id.tvFrom)
        val id : TextView = itemView.findViewById(R.id.tvBookingID)
        val date : TextView = itemView.findViewById(R.id.tvDate)
        val time : TextView = itemView.findViewById(R.id.tvTime)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

