package com.example.datastoring

import android.content.Context
import android.content.SharedPreferences

class SomePreferences(context: Context) {
	private val sharedPreferences: SharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
	var variable: Int
		get() = sharedPreferences.getInt(VARIABLE_KEY, 0)
		set(variable) {
			sharedPreferences.edit().putInt(VARIABLE_KEY, variable).apply()
		}

	companion object {
		private const val FILENAME = "SomePreferences"
		private const val VARIABLE_KEY = "VARIABLE_KEY"
	}

}