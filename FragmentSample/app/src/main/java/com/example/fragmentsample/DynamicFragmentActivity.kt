package com.example.fragmentsample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DynamicFragmentActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_dynamic_fragment)
		if (savedInstanceState == null) {
			supportFragmentManager
				.beginTransaction()
				.replace(R.id.dynamicFragmentActivityContainer, ExampleFragment.newInstance())
				.addToBackStack(null)
				.commit()
		}
	}

	override fun onBackPressed() {
		if (supportFragmentManager.backStackEntryCount == 1) {
			finish()
		} else {
			super.onBackPressed()
		}
	}

	companion object {
		fun getIntent(context: Context): Intent {
			return Intent(context, DynamicFragmentActivity::class.java)
		}
	}
}