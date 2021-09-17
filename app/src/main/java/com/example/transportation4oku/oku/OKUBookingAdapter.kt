package com.example.transportation4oku.oku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R

data class OKUBookingAdapter(private val acceptedDetailList: ArrayList<OKUBookingModel>)
    :RecyclerView.Adapter<OKUBookingAdapter.OKUViewHolder>() {
    private lateinit var mListener : onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OKUViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_okubooking,
            parent,false)

        return OKUViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: OKUViewHolder, position: Int) {
        val OKUBookDetail : OKUBookingModel = acceptedDetailList[position]
        holder.to.text = OKUBookDetail.to
        holder.from.text = OKUBookDetail.from
        holder.date.text = OKUBookDetail.date
        holder.id.text = OKUBookDetail.id.toString()
        holder.time.text = OKUBookDetail.time
    }

    override fun getItemCount(): Int {
        return acceptedDetailList.size
    }

    public class OKUViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val to : TextView = itemView.findViewById(R.id.ToOKU)
        val from : TextView = itemView.findViewById(R.id.FromOKU)
        val id : TextView = itemView.findViewById(R.id.BookingIDOKU)
        val date : TextView = itemView.findViewById(R.id.DateOKU)
        val time : TextView = itemView.findViewById(R.id.TimeOKU)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
