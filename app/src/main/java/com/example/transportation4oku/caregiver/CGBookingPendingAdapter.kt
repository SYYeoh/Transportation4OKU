package com.example.transportation4oku.caregiver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R

class CGBookingPendingAdapter (private val bookingDetailList : ArrayList<PendingDetail>)
    : RecyclerView.Adapter<CGBookingPendingAdapter.MyViewHolder>(){
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_cgbookingpending,
            parent,false)

        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pendingDetail : PendingDetail = bookingDetailList[position]

        holder.from.text = pendingDetail.from
        holder.id.text = pendingDetail.id.toString()
        holder.to.text = pendingDetail.to
        holder.date.text = pendingDetail.date
        holder.time.text = pendingDetail.time

    }

    override fun getItemCount(): Int {
        return bookingDetailList.size
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