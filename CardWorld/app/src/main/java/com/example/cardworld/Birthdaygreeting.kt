package com.example.cardworld

import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_birthdaygreeting.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class Birthdaygreeting : AppCompatActivity() {

    companion object {
        const val NAME_EXTRA = "name_extra"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthdaygreeting)

        val name = intent.getStringExtra(NAME_EXTRA)
        birthdayGreeting.text="Happy Birthday\n$name!"



        ib_sharebutton.setOnClickListener {
              BitmapAsyncTask(getBitmapFromView(fl_drawing_view_container)).execute()


        }

    }



    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width,
            view.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if(bgDrawable!=null){
            bgDrawable.draw(canvas)
        }
        else{
            canvas.drawColor(Color.WHITE)
        }

        view.draw(canvas)

        return returnedBitmap
    }



    private inner class BitmapAsyncTask(val mBitmap: Bitmap?) :
        AsyncTask<Any, Void, String>() {

        private var mDialog: ProgressDialog? = null
        override fun onPreExecute() {
            super.onPreExecute()
            showProgressDialog()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg params: Any): String {

            return saveToGallery()
        }
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            cancelProgressDialog()

            if(result!!.isNotEmpty()){
                Toast.makeText(this@Birthdaygreeting,
                    "File saved successfully. : $result",
                    Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(this@Birthdaygreeting,
                    "Error saving file.",
                    Toast.LENGTH_SHORT).show()
            }

            shareImage(Uri.parse(result))
        }

        private fun shareImage(uri: Uri) {
            val intent = Intent(Intent.ACTION_SEND).apply{
                type="image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            startActivity(
                Intent.createChooser(
                    intent, "Share"
                )
            )
        }


        @RequiresApi(Build.VERSION_CODES.N)
        private fun saveToGallery(): String {
            var result = ""

            var resolver = this@Birthdaygreeting.contentResolver

            val foldername = packageManager.getApplicationLabel(applicationInfo).toString()
                .replace(" ", "")
            val filename = createFilename(foldername)
            val saveLocation = Environment.DIRECTORY_PICTURES + File.separator + foldername

            if (android.os.Build.VERSION.SDK_INT >= 29) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)

                // RELATIVE_PATH and IS_PENDING are introduced in API 29.
                values.put(MediaStore.Images.Media.RELATIVE_PATH, saveLocation)
                values.put(MediaStore.Images.Media.IS_PENDING, true)


                val uri: Uri? = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

                if (uri != null) {
                    //val outstream = resolver.openOutputStream(uri)

                    if (mBitmap != null) {
                        saveImageToStream(mBitmap, resolver.openOutputStream(uri))
                    }

                    values.put(MediaStore.Images.Media.IS_PENDING, false)

                    this@Birthdaygreeting.contentResolver.update(uri, values, null, null)

                    result = uri.toString()
                }
            }

            return result
        }

        private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
            if (outputStream != null) {
                try {
                    mBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    Log.e("**Exception", "Could not write to stream" )
                    e.printStackTrace()
                }
            }



        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun createFilename(filename:String) : String{
            val formatter = SimpleDateFormat("YYYYMMdd-HHmm.ssSSS")
            val dateString = formatter.format(Date()) + "_"

            return dateString + filename + ".jpg"
        }


        private fun showProgressDialog() {
            @Suppress("DEPRECATION")
            mDialog = ProgressDialog.show(
                this@Birthdaygreeting,
                "",
                "Saving your image..."
            )
        }


        private fun cancelProgressDialog() {
            if (mDialog != null) {
                mDialog!!.dismiss()
                mDialog = null
            }
        }
    }





}