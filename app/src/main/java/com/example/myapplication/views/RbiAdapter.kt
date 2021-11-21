package com.example.myapplication.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.FirestoreRBI
import kotlinx.android.synthetic.main.rbi_custom_view.view.*
import java.util.*

class RbiAdapter(private val rbiList: ArrayList<FirestoreRBI>) :
    RecyclerView.Adapter<RbiAdapter.RBIViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RBIViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rbi_custom_view, parent, false)

        return RBIViewHolder(view)
    }

    override fun onBindViewHolder(holder: RBIViewHolder, position: Int) {
        holder.bind(rbiList[position])
    }

    override fun getItemCount(): Int {
        return rbiList.size
    }

    class RBIViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(rbi: FirestoreRBI) {
            itemView.house_name.text = "${rbi.family_name}'s Family"
            itemView.address.text = rbi.address
            itemView.house_num.text = rbi.house_num
        }

    }
}