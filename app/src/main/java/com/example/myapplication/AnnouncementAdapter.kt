package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.announcement_custom_view.view.*

class AnnouncementAdapter(val arrayList: ArrayList<Model>, val context: Context) :
    RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: Model) {
            itemView.announcement_title.text = model.title
            itemView.announcement_date.text = model.date
            itemView.announcement_time.text = model.time
            itemView.announcement_desc.text = model.desc
            itemView.announcement_img.setImageResource(model.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.announcement_custom_view, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

//        holder.itemView.setOnClickListener {
//            if (position == 0) {
//                Toast.makeText(context, "You clicked Announcement 1", Toast.LENGTH_LONG).show()
//                announcementDialog()
//            } else if (position == 1) {
//                Toast.makeText(context, "You clicked Announcement 2", Toast.LENGTH_LONG).show()
//                announcementDialog()
//            } else if (position == 2) {
//                Toast.makeText(context, "You clicked Announcement 3", Toast.LENGTH_LONG).show()
//                announcementDialog()
//            } else if (position == 3) {
//                Toast.makeText(context, "You clicked Announcement 4", Toast.LENGTH_LONG).show()
//                announcementDialog()
//            } else if (position == 4) {
//                Toast.makeText(context, "You clicked Announcement 5", Toast.LENGTH_LONG).show()
//                announcementDialog()
//            }
//        }
    }

    private fun announcementDialog() {
        val viewDialog = View.inflate(context, R.layout.announcement_dialog, null)

        val builderDialog = AlertDialog.Builder(context)
        builderDialog.setView(viewDialog)

        val dialog = builderDialog.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}