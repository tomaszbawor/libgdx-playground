package com.tbawor.options

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.tbawor.core.GameState
import com.tbawor.core.ScreenStateMachine

class OptionsScreen(private val machine: ScreenStateMachine) : ScreenAdapter() {
  private val stage = Stage(ScreenViewport())
  private val disposables = mutableListOf<Disposable>()

  private val font = BitmapFont().also { disposables += it }
  private val labelStyle = com.tbawor.ui.UiFactory.createLabelStyle(font, Color.WHITE)

  private val backButton = com.tbawor.ui.UiFactory.createButton(
    text = "Back",
    font = font,
    upColor = Color(0.55f, 0.22f, 0.22f, 1f),
    downColor = Color(0.4f, 0.16f, 0.16f, 1f),
    disposables = disposables
  )

  init {
    setupUi()
  }

  private fun setupUi() {
    backButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        machine.change(GameState.MAIN_MENU)
      }
    })

    val table = Table().apply {
      setFillParent(true)
      pad(20f)
      defaults().pad(10f)
      add(Label("Options", labelStyle)).padBottom(30f)
      row()
      add(Label("(Placeholder options)", labelStyle)).padBottom(20f)
      row()
      add(backButton).width(220f).height(60f)
    }

    stage.addActor(table)
  }


  override fun show() {
    Gdx.input.inputProcessor = stage
  }

  override fun render(delta: Float) {
    ScreenUtils.clear(0.1f, 0.08f, 0.08f, 1f)
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
