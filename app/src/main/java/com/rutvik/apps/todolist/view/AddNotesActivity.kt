package com.rutvik.apps.todolist.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.rutvik.apps.todolist.BuildConfig
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.utils.AppConstant
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {

    val TAG = "Add Notes Activity"

    lateinit var editTextTitle : EditText
    lateinit var editTextDescription : EditText
    lateinit var imageViewImage: ImageView
    lateinit var buttonSubmit : Button

    var picturePath : String = ""

    val RC_GALLERY = 1101
    val RC_CAMERA = 1102
    val MY_PERMISSION_CODE = 1103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        bindViews()

        imageViewImage.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (checkAndRequestPermissions()) {
                    setUpAddImageDialog()
                    Log.d(TAG, "Add Image Clicked")
                }
            }
        })
        buttonSubmit.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra(AppConstant.NOTES_TITLE, editTextTitle.text.toString())
                intent.putExtra(AppConstant.NOTES_DESCRIPTION, editTextDescription.text.toString())
                intent.putExtra(AppConstant.NOTES_IMAGE_PATH, picturePath)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })
    }

    private fun checkAndRequestPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        var listPermissionNeeded = ArrayList<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray<String>(), MY_PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         when (requestCode) {
             MY_PERMISSION_CODE -> {
                 setUpAddImageDialog()
             }
         }
    }

    private fun setUpAddImageDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_selector_notes_image,null)
        val textViewCamera : TextView = view.findViewById(R.id.textViewCamera)
        val textViewGallery : TextView = view.findViewById(R.id.textViewGallery)
        val dialog = AlertDialog.Builder(this).setView(view).setCancelable(true).create()

        textViewCamera.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d(TAG, "Camera Option selected")
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                var photoFile : File? = null
                photoFile = createImage()
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(this@AddNotesActivity,
                            BuildConfig.APPLICATION_ID + ".provider", photoFile)
                    picturePath = photoFile.absolutePath
                    Log.d(TAG, "Photo tooked : $picturePath")
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, RC_CAMERA)
                }

//                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//                    // Ensure that there's a camera activity to handle the intent
//                    takePictureIntent.resolveActivity(packageManager)?.also {
//                        // Create the File where the photo should go
//                        val photoFile: File? = try {
//                            createImageFile()
//                        } catch (ex: IOException) {
//                            // Error occurred while creating the File
//                            null
//                        }
//                        // Continue only if the File was successfully created
//                        photoFile?.also {
//                            val photoURI: Uri = FileProvider.getUriForFile(
//                                    this@AddNotesActivity,
//                                    "com.rutvik.apps.todolist.provider",
//                                    it
//                            )
//                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                            startActivityForResult(takePictureIntent, RC_CAMERA)
//                        }
//                    }
//                }

                dialog.dismiss()
            }
        })

        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d(TAG, "Gallery Option selected")
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, RC_GALLERY)
                dialog.dismiss()
            }
        })

        dialog.show()
    }
    private fun createImage(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG_" + timeStamp + '_'
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

//    @Throws(IOException::class)
//    private fun createImageFile(): File {
//        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(
//                "JPEG_${timeStamp}_", /* prefix */
//                ".jpg", /* suffix */
//                storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            picturePath = absolutePath
//        }
//    }

    private fun bindViews() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        imageViewImage = findViewById(R.id.imageViewNotes)
        buttonSubmit = findViewById(R.id.buttonSubmit)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // If result is OK
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                RC_CAMERA -> {
                    Glide.with(this).load(picturePath).into(imageViewImage)
//                    val imageBitmap = data?.data as Bitmap
//                    imageViewImage.setImageBitmap(imageBitmap)
                }
                RC_GALLERY -> {
                    Log.d(TAG, "Returned from Gallery Succesfully")
                    val selectedImage = data?.data
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    var c = contentResolver.query(selectedImage!!, filePath, null, null, null)
                    c?.moveToFirst()
                    val columnIndex = c!!.getColumnIndex(filePath[0])
                    picturePath = c.getString(columnIndex)
                    c.close()
                    Log.d(TAG, picturePath)
                    //Glide.with(this).load(selectedImage!! ).into(imageViewImage)
                    picturePath = selectedImage.toString()
                    //var file = File(selectedImage.path)
                    Glide.with(this).load(selectedImage).into(imageViewImage)
                }
            }
        }
    }
}
