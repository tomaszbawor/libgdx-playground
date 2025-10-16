package com.tbawor.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.tbawor.core.GameState
import com.tbawor.core.ScreenStateMachine
import com.tbawor.ui.UiFactory.createGreenButton

class MainMenuScreen(private val machine: ScreenStateMachine) : ScreenAdapter() {
  private val stage = Stage(ScreenViewport())
  private val disposables = mutableListOf<Disposable>()

  private val font = com.tbawor.ui.UiFactory.pixelFont()
  private val labelStyle = com.tbawor.ui.UiFactory.createLabelStyle(font, Color.WHITE)

  private val newGameButton = createGreenButton(
    text = "New Game",
    disposables = disposables,
    // Placeholder sprite regions from Main_menu.png; adjust later
  )
  private val optionsButton = createGreenButton(
    text = "Options",
    disposables = disposables
  )
  private val quitButton = createGreenButton(
    text = "Quit",
    disposables = disposables
  )

  init {
    setupUi()
  }

  private fun setupUi() {
    newGameButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        machine.change(GameState.PLAYING)
      }
    })

    optionsButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        machine.change(GameState.OPTIONS)
      }
    })

    quitButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        Gdx.app.exit()
      }
    })

    val table = Table().apply {
      setFillParent(true)
      pad(20f)
      defaults().pad(10f)
      add(Label("Idle Builder", labelStyle)).padBottom(30f)
      row()
      add(newGameButton).width(300f).height(60f)
      row()
      add(optionsButton).width(300f).height(60f)
      row()
      add(quitButton).width(300f).height(60f)
    }

    stage.addActor(table)
  }


  override fun show() {
    Gdx.input.inputProcessor = stage
  }

  override fun render(delta: Float) {
    ScreenUtils.clear(0.08f, 0.08f, 0.1f, 1f)
    stage.act(delta)
    stage.draw()
  }

  override fun resize(width: Int, height: Int) {
    stage.viewport.update(width, height, true)
  }

  override fun hide() {
    if (Gdx.input.inputProcessor === stage) {
      Gdx.input.inputProcessor = null
    }
  }

  override fun dispose() {
    stage.dispose()
    disposables.forEach { it.dispose() }
  }
}
