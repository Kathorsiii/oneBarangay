package com.example.myapplication


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.appointment_custom_view.view.*
import java.util.*
import kotlin.collections.ArrayList

class AppointmentAdapter(
    private val appointmentList: ArrayList<AppointmentItem>,
    private val clickListener: (AppointmentItem) -> Unit,
) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_custom_view, parent, false)

        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(appointmentList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(appointment: AppointmentItem, clickListener: (AppointmentItem) -> Unit) {
            itemView.appointment_fullName.text =
                "${appointment.first_name} ${appointment.last_name}"
            itemView.user_email.text = appointment.email
            itemView.contact_number.text = appointment.contact_number
            itemView.status.text = appointment.status?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }

            val documents = appointment.document!!
            val requestedDocuments: MutableList<String> = ArrayList()
            for (document in documents) {
                requestedDocuments.add(document["document_name"] as String)
            }
            itemView.requestDocuments.text = requestedDocuments.joinToString(",\n")

            Picasso.get()
                .load(appointment.appointment_image)
                .into(itemView.appointment_img)

            itemView.setOnClickListener {
                clickListener(appointment)
            }

        }

    }
}