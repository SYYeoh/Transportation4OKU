package com.example.transportation4oku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CGPendingBookingAdapter(private val bookingDetailList : ArrayList<BookingDetail>)
    : RecyclerView.Adapter<CGPendingBookingAdapter.CGPendingBookingViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CGPendingBookingAdapter.CGPendingBookingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_cgpendingbooking,
            parent, false
        )

        return CGPendingBookingViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(
        holder: CGPendingBookingViewHolder,
        position: Int
    ) {
        val bookingDetail: BookingDetail = bookingDetailList[position]

        holder.from.text = bookingDetail.from
        holder.id.text = bookingDetail.id.toString()
        holder.to.text = bookingDetail.to
        holder.date.text = bookingDetail.date
        holder.time.text = bookingDetail.time
    }

    override fun getItemCount(): Int {
        return bookingDetailList.size
    }

    class CGPendingBookingViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val to: TextView = itemView.findViewById(R.id.tvTo)
        val from: TextView = itemView.findViewById(R.id.tvFrom)
        val id: TextView = itemView.findViewById(R.id.tvBookingID)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val time: TextView = itemView.findViewById(R.id.tvTime)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}