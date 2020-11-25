package me.shafran.rvsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PersonListActivity : AppCompatActivity(), PersonAdapter.Listener {
	override fun onPersonClick(id: Long) {
		startActivity(PersonDetailActivity.getIntent(this, id))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_person_list)

		val recyclerView: RecyclerView = findViewById(R.id.personRecyclerView)
		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.setHasFixedSize(true)
		recyclerView.recycledViewPool.setMaxRecycledViews(0, 5)

		val adapter = PersonAdapter()
		recyclerView.adapter = adapter

		adapter.personList = PersonRepository.getPersonList()
		adapter.listener = this
	}
}