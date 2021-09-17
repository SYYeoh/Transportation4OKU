package com.example.transportation4oku.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R

class AdminManageCGAdapter(private val CGList : ArrayList<AdminManageCGModel>):
    RecyclerView.Adapter<AdminManageCGAdapter.AdminManageCGViewHolder>() {
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminManageCGViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_adminmanagecg,
            parent,false)

        return AdminManageCGViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(
        holder: AdminManageCGViewHolder,
        position: Int
    ) {
        val CG : AdminManageCGModel = CGList[position]
        holder.name.text = CG.name
        holder.email.text = CG.email
    }

    override fun getItemCount(): Int {
        return CGList.size
    }

    class AdminManageCGViewHolder(itemView: View, listener: onItemClickListener)
        : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameAMCG)
        val email: TextView = itemView.findViewById(R.id.emailAMCG)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}