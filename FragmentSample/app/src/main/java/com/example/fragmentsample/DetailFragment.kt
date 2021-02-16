package com.example.fragmentsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_detail, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val textView = view.findViewById<TextView>(R.id.detailTextView)
		val name = requireArguments().getString(NAME_KEY)
		textView.text = name
	}

	companion object {
		const val TAG = "DetailFragment"
		private const val NAME_KEY = "NAME_KEY"
		fun newInstance(name: String): Fragment {
			val fragment: Fragment = DetailFragment()
			val arguments = Bundle()
			arguments.putString(NAME_KEY, name)
			fragment.arguments = arguments
			return fragment
		}
	}
}