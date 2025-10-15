package com.tbawor.idle

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.tbawor.core.GameState
import com.tbawor.core.ScreenStateMachine
import com.tbawor.menu.MainMenuScreen
import com.tbawor.options.OptionsScreen

class IdleGame : Game() {
  private lateinit var machine: ScreenStateMachine

  override fun create() {
    val gameplayState = com.tbawor.core.domain.GameplayState()
    machine = ScreenStateMachine(
      this,
      mapOf(
        GameState.MAIN_MENU to { MainMenuScreen(machine) },
        GameState.PLAYING to { IdleGameScreen(machine, gameplayState) },
        GameState.OPTIONS to { OptionsScreen(machine) }
      )
    )
    machine.change(GameState.MAIN_MENU)
  }

  override fun dispose() {
    super.dispose()
    machine.disposeAll()
    com.tbawor.ui.UiFactory.disposeShared()
  }
}
