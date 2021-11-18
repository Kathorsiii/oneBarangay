package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.android.synthetic.main.activity_resident_upload_complaint_proof.*
import java.io.IOException

class ResidentUploadComplaintProofActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var image: InputImage

    override fun onCreate(savedInstanceState: Bundle?) {

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Upload Proof"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_upload_complaint_proof)

    }

    companion object {
        private val TAG= MainActivity::class.simpleName
    }


}