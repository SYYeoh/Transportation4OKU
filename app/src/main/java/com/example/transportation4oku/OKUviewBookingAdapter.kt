package com.example.transportation4oku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.admin.AdminBookingAdapter

class OKUviewBookingAdapter(private val okubook : ArrayList<OKUviewBookingModel>): RecyclerView.Adapter
<OKUviewBookingAdapter.OKUviewBookingViewHolder>() {
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
    ): OKUviewBookingAdapter.OKUviewBookingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_okubooking,
            parent,false)

        return OKUviewBookingViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(
        holder: OKUviewBookingAdapter.OKUviewBookingViewHolder,
        position: Int
    ) {
        val booking : OKUviewBookingModel = okubook[position]

        holder.name.text = booking.name
        holder.from.text = booking.from
        holder.to.text = booking.to
        holder.status.text = booking.status
        holder.id.text = booking.id.toString()
    }

    override fun getItemCount(): Int {
        return okubook.size
    }
    public class OKUviewBookingViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameOKU)
        val from: TextView = itemView.findViewById(R.id.fromOKU)
        val to: TextView = itemView.findViewById(R.id.toOKU)
        val status: TextView = itemView.findViewById(R.id.statusOKU)
        val id: TextView = itemView.findViewById(R.id.bookIDOKU)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}