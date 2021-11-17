package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class AddBulkScheduleActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bulk_schedule)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Bulk Schedule"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val typeofEvent = listOf("SAP", "Ayuda", "Vaccination")
//        val adapter = ArrayAdapter(requireContext(), R.layout.event_list_item, typeofEvent)
//
//    private fun requireContext(): Context {
//
//    }
//        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
}

// To check a checkbox
// checkbox.isChecked = true

// To listen for a checkbox's checked/unchecked state changes
// checkbox.setOnCheckedChangeListener { buttonView, isChecked
// Responds to checkbox being checked/unchecked
// }
