package com.example.myapplication

import android.content.Intent
import android.net.Uri.fromFile
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.model.ocr.FamilyItem
import com.example.myapplication.databinding.ActivityScanDocumentBinding
import com.example.myapplication.views.OcrResultActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File


class ScanDocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanDocumentBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    private var storage: FirebaseStorage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanDocumentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "OCR"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding.rlSelectImg.setOnClickListener {
            openImagePicker()
        }
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }

    // Open Image Picker
    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080,
                1080)    //  Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                setViewVisibility()
                val storageRef = storage.reference
                val fileUri = data!!.data!!
                val file = fromFile(File(fileUri.path!!))
                val imagesRef = storageRef.child("images/${file.lastPathSegment}")
                val uploadTask = imagesRef.putFile(file)

                uploadTask.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }.addOnSuccessListener { taskSnapshot ->
                    val filename = taskSnapshot.metadata!!.name
                    val intent = Intent(this, OcrResultActivity::class.java)
                    intent.putExtra("filename", filename)
                    startActivity(intent)
                }
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this,
                    resources.getString(R.string.task_cancelled), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setViewVisibility() {
        binding.ivSelectedImg.visibility = View.VISIBLE
        binding.tvImgDetails.visibility = View.VISIBLE
        binding.scrollView.visibility = View.VISIBLE
        binding.llNoselectMessage.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}