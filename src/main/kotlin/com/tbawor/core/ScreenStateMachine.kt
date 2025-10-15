package com.tbawor.core

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen

/**
 * A minimal state machine that maps GameState to LibGDX Screens and
 * uses Game.setScreen() to perform transitions.
 */
class ScreenStateMachine(
  private val game: Game,
  private val screenProviders: Map<GameState, () -> Screen>
) {
  var current: GameState? = null
    private set

  private val screens: MutableMap<GameState, Screen> = mutableMapOf()

  fun change(state: GameState) {
    if (state == GameState.QUIT) {
      // Let caller decide to actually exit, but we expose QUIT for clarity
      current = state
      return
    }
    val screen = screens.getOrPut(state) { screenProviders[state]!!.invoke() }
    current = state
    game.setScreen(screen)
  }

  fun disposeAll() {
    screens.values.forEach { it.dispose() }
    screens.clear()
  }
}
