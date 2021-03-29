package com.example.datastoring.db

class Person {
	var id: Long = 0
	var name: String? = null
	override fun toString(): String {
		return "Person{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}'
	}
}