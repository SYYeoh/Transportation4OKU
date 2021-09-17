package com.example.transportation4oku.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R

/*class AdminBookingAdapter(options: FirestoreRecyclerOptions<AdminBooking>) :
    FirestoreRecyclerAdapter<AdminBooking, AdminBookingAdapter.AdminBookingViewHolder>(options){*/
class AdminBookingAdapter(private val bookingList : ArrayList<AdminBooking>): RecyclerView.Adapter
<AdminBookingAdapter.AdminBookingViewHolder>() {

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
    ): AdminBookingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_adminbooking,
            parent,false)

        return AdminBookingViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(
        holder: AdminBookingViewHolder,
        position: Int
    ) {
        val booking : AdminBooking = bookingList[position]

        holder.name.text = booking.name
        holder.from.text = booking.from
        holder.to.text = booking.to
        holder.status.text = booking.status
        holder.id.text = booking.id.toString()
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    public class AdminBookingViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.nameAB)
        val from: TextView = itemView.findViewById(R.id.fromAB)
        val to: TextView = itemView.findViewById(R.id.toAB)
        val status: TextView = itemView.findViewById(R.id.statusAB)
        val id: TextView = itemView.findViewById(R.id.bookIDAB)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

    //delete booking function
   /*fun deleteBooking(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }*/

    /*//viewholder onclick
    interface OnItemClickListener {
        fun onItemClick(documentSnapshot: DocumentSnapshot, position : Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface ClickListener {
        fun onPositionClicked(position: Int)
        fun onLongClicked(position : Int)
    }

    //test
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookingHolder {
        return  BookingHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_adminbooking,
        parent, false))
    }

    inner class BookingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var from: TextView
        var to: TextView
        var status: TextView

        init {
            name = itemView.findViewById(R.id.nameAB)
            from = itemView.findViewById(R.id.fromAB)
            to = itemView.findViewById(R.id.toAB)
            status = itemView.findViewById(R.id.statusAB)

            //when  on click in recycler view
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener!!.onItemClick(snapshots.getSnapshot(position), position)
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: BookingHolder,
        position: Int,
        model: AdminBooking
    ) {

        holder.name.text = model.name
        holder.from.text = model.from
        holder.to.text = model.to
        holder.status.text = model.status
        //holder.dateAB.text = model.date

    }*/

    /*override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminBookingAdapter.AdminBookingViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_adminbooking,
        parent, false)
        return AdminBookingViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(
        holder: AdminBookingAdapter.AdminBookingViewHolder,
        position: Int
    ) {

        val ab : AdminBooking = AdminBookingList[position]
        holder.nameAB.text = ab.name
        holder.fromAB.text = ab.from
        holder.toAB.text = ab.to
        holder.statusAB.text = ab.status

    }

    override fun getItemCount(): Int {

        return AdminBookingList.size

    }

    public class AdminBookingViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val nameAB : TextView = itemView.findViewById(R.id.nameAB)
        val fromAB : TextView = itemView.findViewById(R.id.fromAB)
        val toAB : TextView = itemView.findViewById(R.id.toAB)
        val statusAB : TextView = itemView.findViewById(R.id.statusAB)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }*/