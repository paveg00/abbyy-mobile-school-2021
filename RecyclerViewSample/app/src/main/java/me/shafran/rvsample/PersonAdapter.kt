package me.shafran.rvsample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter : RecyclerView.Adapter<PersonViewHolder>() {
	companion object {
		private const val LEFT_VIEW_HOLDER_TYPE = 0
		private const val RIGHT_VIEW_HOLDER_TYPE = 1
	}

	var personList: List<Person> = emptyList()

	var listener: Listener? = null

	interface Listener {
		fun onPersonClick(id: Long)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
		val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val itemView = inflater.inflate(R.layout.person_list_item, parent, false)
		return when(viewType) {
			LEFT_VIEW_HOLDER_TYPE -> LeftPersonViewHolder(itemView, listener)
			RIGHT_VIEW_HOLDER_TYPE -> RightPersonViewHolder(itemView, listener)
			else -> throw IllegalStateException()
		}
	}

	override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
		holder.bind(personList[position])
	}

	override fun getItemCount(): Int {
		return personList.size
	}

	override fun getItemViewType(position: Int): Int {
		return position % 2
	}
}