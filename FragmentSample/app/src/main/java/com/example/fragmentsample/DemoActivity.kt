package com.example.fragmentsample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentsample.DetailFragment.Companion.newInstance

class DemoActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_demo)
		if (savedInstanceState == null) {
			supportFragmentManager
				.beginTransaction()
				.replace(R.id.demoMainContainer, ListFragment.newInstance(), ListFragment.TAG)
				.addToBackStack(null)
				.commit()
		}
	}

	fun showDetailFragment(name: String) {
		if (supportFragmentManager.findFragmentByTag(DetailFragment.TAG) != null) {
			// Если на экране уже есть фрагмент с деталями, то надо его убрать перед показом нового
			supportFragmentManager.popBackStack()
		}
		supportFragmentManager
			.beginTransaction()
			.replace(R.id.demoDetailContainer, newInstance(name), DetailFragment.TAG)
			.addToBackStack(null)
			.commit()
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
			return Intent(context, DemoActivity::class.java)
		}
	}
}