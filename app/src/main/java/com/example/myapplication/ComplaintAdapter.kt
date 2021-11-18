package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.complaint_custom_view.view.*

class ComplaintAdapter(
    private val complaintList: ArrayList<ComplaintData>,
    private val clickListener: (ComplaintData) -> Unit
) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ComplaintAdapter.ComplaintViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.complaint_custom_view, parent, false)

        return ComplaintViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComplaintAdapter.ComplaintViewHolder, position: Int) {
//        val complaint : ComplaintData = complaintList[position]
//        holder.complaintType.text = complaint.complaint_type
//        holder.complainantName.text = complaint.complainant_name
        holder.bindComplaint(complaintList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return complaintList.size
    }

    public class ComplaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindComplaint(complaint: ComplaintData, clickListener: (ComplaintData) -> Unit) {
            itemView.complaint_fullName.text = complaint.complainant_name
            itemView.complaint_type.text = complaint.complaint_type
            itemView.complaint_status.text = complaint.complaint_status

            Picasso.get()
                .load(complaint.image_url)
                .into(itemView.complaint_img)
        }

//        val complaintType: TextView = itemView.findViewById(R.id.complaint_type)
//        val complainantName: TextView = itemView.findViewById(R.id.complaint_fullName)
    }

}