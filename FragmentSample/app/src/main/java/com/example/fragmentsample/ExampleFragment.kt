package com.example.fragmentsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ExampleFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_example, container, false)
	}

	companion object {
		fun newInstance(): Fragment {
			return ExampleFragment()
		}
	}
}