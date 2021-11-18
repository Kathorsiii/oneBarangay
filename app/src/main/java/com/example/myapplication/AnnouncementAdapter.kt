package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.announcement_custom_view.view.*

//class AnnouncementAdapter(private val announcementList: ArrayList<AnnouncementDataClass>) :
//    RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder>() {
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int,
//    ): AnnouncementAdapter.MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.announcement_custom_view, parent, false)
//
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: AnnouncementAdapter.MyViewHolder, position: Int) {
//        val announcement: AnnouncementDataClass = announcementList[position]
//        holder.announcementTitle.text = announcement.title
//        holder.announcementDesc.text = announcement.body?.take(120)
//
//        Picasso.get()
//            .load(announcement.thumbnail)
//            .into(holder.announcementImg)
//    }
//
//    override fun getItemCount(): Int {
//        return announcementList.size
//    }
//
//    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val announcementTitle: TextView = itemView.findViewById(R.id.announcement_title)
//        val announcementDesc: TextView = itemView.findViewById(R.id.announcement_desc)
//        val announcementImg: ImageView = itemView.findViewById(R.id.announcement_img)
//
//        init {
//            itemView.setOnClickListener {
//                val position: Int = adapterPosition
//                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
//
////                val intent = Intent(this, AnnouncementDisplayActivity::class.java)
////                startActivity(intent)
//            }
//        }
//    }
//}

//    private fun announcementDialog() {
//        val viewDialog = View.inflate(context, R.layout.announcement_dialog, null)
//
//        val builderDialog = AlertDialog.Builder(context)
//        builderDialog.setView(viewDialog)
//
//        val dialog = builderDialog.create()
//        dialog.show()
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//    }

//}

// New
class AnnouncementAdapter(private val announcementList:MutableList<AnnouncementData>, private val clickListener: (AnnouncementData) -> Unit) : RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>() {

    class AnnouncementViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(announcement: AnnouncementData, clickListener: (AnnouncementData) -> Unit) {
            view.announcement_title.text = announcement.title
            view.announcement_desc.text = announcement.desc

            Picasso.get()
                .load(announcement.thumbnail)
                .into(view.announcement_img)

            view.setOnClickListener {
                clickListener(announcement)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val announcementDisplay = inflater.inflate(R.layout.announcement_custom_view, parent, false)

        return AnnouncementViewHolder(announcementDisplay)
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(announcementList[position], clickListener)
    }

    override fun getItemCount(): Int {
//        println(announcementList)
        return announcementList.size
    }


}