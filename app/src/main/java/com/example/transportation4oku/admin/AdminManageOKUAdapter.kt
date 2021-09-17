package com.example.transportation4oku.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportation4oku.R

class AdminManageOKUAdapter(private val OKUList : ArrayList<AdminManageOKUModel>): RecyclerView.Adapter
<AdminManageOKUAdapter.AdminManageUserViewHolder>() {

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
    ): AdminManageUserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_adminmanageuser,
            parent,false)

        return AdminManageUserViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(
        holder: AdminManageUserViewHolder,
        position: Int
    ) {
        val OKU : AdminManageOKUModel = OKUList[position]
        holder.name.text = OKU.name
        holder.email.text = OKU.email
    }

    override fun getItemCount(): Int {
        return OKUList.size
    }

    class AdminManageUserViewHolder(itemView: View, listener: onItemClickListener)
        : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameAMU)
        val email: TextView = itemView.findViewById(R.id.emailAMU)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
