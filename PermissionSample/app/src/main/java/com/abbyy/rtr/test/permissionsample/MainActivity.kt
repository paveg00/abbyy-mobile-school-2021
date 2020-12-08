package com.abbyy.rtr.test.permissionsample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val preferences = this.getPreferences(Context.MODE_PRIVATE)
		val fileName = preferences.getString(FILENAME_PREFERENCES_KEY, null)
		val image: Bitmap
		if (fileName != null && File(filesDir, "$fileName.jpg").exists()) {
			image = BitmapFactory.decodeStream(openFileInput("$fileName.jpg"))
			val imageView = findViewById<ImageView>(R.id.imageView)
			imageView.setImageBitmap(image)
		} else {
			image = generateBitmap()
			val fileName = UUID.randomUUID().toString()
			preferences.edit {
				putString(FILENAME_PREFERENCES_KEY, fileName)
			}

			openFileOutput("$fileName.jpg", Context.MODE_PRIVATE).use {
				image.compress(Bitmap.CompressFormat.JPEG, 100, it)
			}
		}

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

		} else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
			Toast.makeText(this, R.string.storage_permission_rationale, Toast.LENGTH_LONG).show()
		} else {
			requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE)
		}
	}

	private fun generateBitmap(): Bitmap
	{
		val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)
		val paint = Paint().apply {
			color = getColor(android.R.color.holo_red_dark)
		}
		canvas.drawCircle(250f, 250f, 50f, paint)
		return bitmap
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		when (requestCode) {
			STORAGE_REQUEST_CODE -> {
				if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				} else {
					Toast.makeText(this, R.string.storage_permission_rationale, Toast.LENGTH_LONG).show()
				}
			}
			else -> return
		}
	}

	companion object {
		const val CAMERA_REQUEST_CODE = 1
		const val STORAGE_REQUEST_CODE = 2

		const val FILENAME_PREFERENCES_KEY = "fileName"
	}
}