package com.example.datastoring.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object PersonContract {
	const val TABLE_NAME = "person_table"
	fun createTable(db: SQLiteDatabase) {
		db.execSQL(
			"CREATE TABLE " + TABLE_NAME
				+ " ( "
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Columns.NAME + " TEXT NOT NULL"
				+ " );"
		)
	}

	interface Columns : BaseColumns {
		companion object {
			const val NAME = "name"
		}
	}
}