package com.example.transportation4oku

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class AdminBookingAdapter(options: FirestoreRecyclerOptions<AdminBooking>) :
    FirestoreRecyclerAdapter<AdminBooking, AdminBookingAdapter.AdminBookingViewHolder>(options){

    //val AdminBookingAdapter.onItemClickListener listener;
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef: CollectionReference = fStore.collection("Booking Detail");

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminBookingViewHolder {
        return  AdminBookingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_adminbooking,
        parent, false))
    }

    override fun onBindViewHolder(
        holder: AdminBookingViewHolder,
        position: Int,
        model: AdminBooking
    ) {

        holder.nameAB.text = model.name
        holder.fromAB.text = model.from
        holder.toAB.text = model.to
        holder.statusAB.text = model.status
        holder.dateAB.text = model.date

        holder.itemView.setOnClickListener {

            val date = holder.dateAB.text
            val name = holder.nameAB.text
            val from = holder.fromAB.text
            val to = holder.toAB.text
            val status = holder.statusAB.text

            val context = holder.nameAB.context
            val intent = Intent(context, AdminBookingDetail::class.java)
            intent.putExtra("date", date)
            intent.putExtra("name", name)
            intent.putExtra("from", from)
            intent.putExtra("to", to)
            intent.putExtra("status", status)
            context.startActivity(intent)
        }

    }

    class AdminBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameAB : TextView = itemView.findViewById(R.id.nameAB)
        val fromAB : TextView = itemView.findViewById(R.id.fromAB)
        val toAB : TextView = itemView.findViewById(R.id.toAB)
        val statusAB : TextView = itemView.findViewById(R.id.statusAB)
        val dateAB : TextView = itemView.findViewById(R.id.dateAB)

        /*override fun AdminBookingViewHolder(itemView: View) {
            super.itemView
            itemView.setOnClickListener {
                val position : Int = adapterPosition
                if (position != RecyclerView.NO_POSITION && ) {
                    .onItemClick(get().)
                }

            }

        }*/
    }

    //test
    /*interface onItemClickListener {
        fun onItemClick(getSnapshot: DocumentSnapshot, position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        listener
    }

    interface ClickListener {
        fun onPositionClicked(position: Int)
        fun onLongClicked(position : Int)
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


}