package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.*
import kotlinx.android.synthetic.main.fragment_services.view.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ServicesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_services, container, false)

        view.viewAppointmentBtn.setOnClickListener {
            startActivity(Intent(context, ViewAppointmentActivity::class.java))
        }

        view.viewReportsBtn.setOnClickListener {
            startActivity(Intent(context, ResidentViewComplaintActivity::class.java))
        }

        view.bulkSchedBtn.setOnClickListener {
            startActivity(Intent(context, AddBulkScheduleActivity::class.java))
        }

        view.scanDocuBtn.setOnClickListener {
            startActivity(Intent(context, ScanDocumentActivity::class.java))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}