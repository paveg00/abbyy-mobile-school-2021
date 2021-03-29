package com.example.datastoring.db

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

class PersonRepository(private val databaseHolder: DatabaseHolder) {
	fun create(person: Person) {
		try {
			val database = databaseHolder.open() ?: throw IllegalStateException()
			val contentValues = ContentValues()
			contentValues.put(PersonContract.Columns.NAME, person.name)
			database.insert(PersonContract.TABLE_NAME, null, contentValues)
		} finally {
			databaseHolder.close()
		}
	}

	fun loadAll(): List<Person> {
		val personList: MutableList<Person> = mutableListOf()
		var cursor: Cursor? = null
		try {
			val database = databaseHolder.open() ?: throw IllegalStateException()
			cursor = database.query(
				PersonContract.TABLE_NAME, arrayOf(BaseColumns._ID, PersonContract.Columns.NAME),
				null,
				null,
				null,
				null,
				null
			)
			while (cursor.moveToNext()) {
				val person = Person()
				person.id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
				person.name = cursor.getString(cursor.getColumnIndex(PersonContract.Columns.NAME))
				personList.add(person)
			}
		} finally {
			cursor?.close()
			databaseHolder.close()
		}
		return personList
	}
}