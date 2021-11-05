package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComplaintAdapter(private val complaintList : ArrayList<Complaint>) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): ComplaintAdapter.ComplaintViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_resident_view_complaint, parent, false)

        return ComplaintViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComplaintAdapter.ComplaintViewHolder, position: Int) {
        val complaint : Complaint = complaintList[position]
        holder.complaintType.text = complaint.complaint_type
        holder.complainantName.text = complaint.complainant_name
        holder.complaintDate.text = complaint.complant_date
        holder.complaintComment.text = complaint.complaint_comment
    }

    override fun getItemCount(): Int {
        return complaintList.size
    }

    public class ComplaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val complaintType : TextView = itemView.findViewById(R.id.complaint_type)
        val complainantName: TextView = itemView.findViewById(R.id.complainant_name)
        val complaintDate: TextView = itemView.findViewById(R.id.complaint_date)
        val complaintComment: TextView = itemView.findViewById(R.id.complaint_comment)
    }

}