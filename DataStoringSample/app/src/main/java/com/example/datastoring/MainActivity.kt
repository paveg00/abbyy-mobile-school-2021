package com.example.datastoring

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.datastoring.App.Companion.databaseHolder
import com.example.datastoring.db.Person
import com.example.datastoring.db.PersonRepository
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
	private lateinit var textView: TextView
	private lateinit var personRepository: PersonRepository
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		textView = findViewById(R.id.textView)
		findViewById<View>(R.id.createButton).setOnClickListener(this)
		findViewById<View>(R.id.loadButton).setOnClickListener(this)
		personRepository = PersonRepository(databaseHolder ?: return)
	}

	override fun onClick(v: View) {
		val id = v.id
		if (id == R.id.createButton) {
			onCreateButtonClick()
		} else if (id == R.id.loadButton) {
			onLoadButtonClick()
		}
	}

	private fun onCreateButtonClick() {
		object : AsyncTask<Void?, Void?, Void?>() {
			override fun doInBackground(vararg voids: Void?): Void? {
				val person = Person()
				person.name = UUID.randomUUID().toString()
				personRepository.create(person)
				return null
			}
		}.execute()
	}

	private fun onLoadButtonClick() {
		object : AsyncTask<Void?, Void?, String>() {
			override fun doInBackground(vararg voids: Void?): String {
				return personRepository.loadAll().toString()
			}

			override fun onPostExecute(s: String) {
				super.onPostExecute(s)
				textView.text = s
			}
		}.execute()
	}
}