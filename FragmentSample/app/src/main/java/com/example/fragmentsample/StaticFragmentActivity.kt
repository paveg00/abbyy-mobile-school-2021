package com.example.fragmentsample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StaticFragmentActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_static_fragment)
	}

	companion object {
		fun getIntent(context: Context): Intent {
			return Intent(context, StaticFragmentActivity::class.java)
		}
	}
}