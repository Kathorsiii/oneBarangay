package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.announcement_custom_view.view.*

class AnnouncementAdapter(private val announcementList: ArrayList<AnnouncementData>, private val clickListener: (AnnouncementData) -> Unit) :
    RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AnnouncementAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.announcement_custom_view, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnnouncementAdapter.MyViewHolder, position: Int) {
        holder.bind(announcementList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(announcement: AnnouncementData, clickListener: (AnnouncementData) -> Unit) {
            itemView.announcement_title.text = announcement.title
            itemView.announcement_desc.text = announcement.body?.take(120)

            Picasso.get()
                .load(announcement.thumbnail)
                .into(itemView.announcement_img)

            itemView.setOnClickListener {
                clickListener(announcement)
            }

        }

    }
}