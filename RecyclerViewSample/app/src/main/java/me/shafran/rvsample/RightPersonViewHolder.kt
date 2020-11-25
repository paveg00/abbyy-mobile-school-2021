package me.shafran.rvsample

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RightPersonViewHolder(
	itemView: View,
	listener: PersonAdapter.Listener?
) : PersonViewHolder(itemView, listener) {
	init {
		textView.gravity = Gravity.END
	}

	override fun bind(person: Person) {
		data = person
		textView.text = person.name
	}

}