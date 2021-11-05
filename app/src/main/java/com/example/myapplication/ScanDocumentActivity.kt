package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class ScanDocumentActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_document)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Scan Document"

    }
}