package com.tbawor.core.engine

/**
 * Domain model that encapsulates gameplay state and logic.
 * This separates game logic from UI so Screens can render and trigger actions
 * without owning the core game rules.
 */
class GameplayState(
	var money: Double = 0.0,
	var buildingCount: Int = 0,
	var buildingCost: Double = 20.0,
	val passivePerBuilding: Double = 5.0,
) {
	val passiveIncome: Double
		get() = buildingCount * passivePerBuilding

	/** Progress the simulation forward by delta seconds. */
	fun tick(delta: Float) {
		money += passiveIncome * delta
	}

	/** Manual work click action. */
	fun work(amount: Double = 1.0) {
		money += amount
	}

	fun canBuyBuilding(): Boolean = money >= buildingCost

	/**
	 * Attempts to buy a building.
	 * @return true if purchased, false if not enough money
	 */
	fun buyBuilding(): Boolean {
		if (!canBuyBuilding()) return false
		money -= buildingCost
		buildingCount += 1
		buildingCost *= 1.2 // simple price growth
		return true
	}

	/** Reset to initial starting values. */
	fun reset() {
		money = 0.0
		buildingCount = 0
		buildingCost = 20.0
	}
}
