package com.tbawor.core.domain

class Player(
	var name: String,
	val hp: Int,
) {
	var currentHp = hp
	var maxHp = hp
}
