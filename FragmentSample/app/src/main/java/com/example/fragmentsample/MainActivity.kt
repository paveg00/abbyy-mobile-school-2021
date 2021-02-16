package com.example.fragmentsample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		findViewById<View>(R.id.staticFragmentButton).setOnClickListener {
			startActivity(StaticFragmentActivity.getIntent(this@MainActivity))
		}
		findViewById<View>(R.id.dynamicFragmentButton).setOnClickListener {
			startActivity(DynamicFragmentActivity.getIntent(this@MainActivity))
		}
		findViewById<View>(R.id.demoButton).setOnClickListener {
			startActivity(DemoActivity.getIntent(this@MainActivity))
		}
	}
}