package com.tbawor.idle

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
import java.util.Locale

class IdleGameScreen : ScreenAdapter() {
  private val stage = Stage(ScreenViewport())
  private val disposables = mutableListOf<Disposable>()

  private val font = BitmapFont().also { disposables += it }
  private val labelStyle = Label.LabelStyle(font, Color.WHITE)
  private val buttonStyle = createButtonStyle(font)

  private val moneyLabel = Label("Money: $0.00", labelStyle)
  private val passiveLabel = Label("Passive income: $0.00 /s", labelStyle)
  private val buildingLabel = Label("Buildings: 0", labelStyle)

  private val clickButton = TextButton("Work (+$1)", buttonStyle)
  private val buyBuildingButton = TextButton("Buy Building", buttonStyle)

  private var money = 0.0
  private var passiveIncome = 0.0
  private var buildingCount = 0
  private var buildingCost = 20.0
  private val passivePerBuilding = 5.0

  init {
    setupUi()
    updateLabels()
  }

  private fun setupUi() {
    clickButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        money += 1.0
        updateLabels()
      }
    })

    buyBuildingButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (money >= buildingCost) {
          money -= buildingCost
          buildingCount += 1
          passiveIncome = buildingCount * passivePerBuilding
          buildingCost *= 1.2
          updateLabels()
        }
      }
    })

    val table = Table().apply {
      setFillParent(true)
      pad(20f)
      defaults().pad(10f)
      add(Label("Idle Builder", labelStyle)).padBottom(30f)
      row()
      add(moneyLabel)
      row()
      add(passiveLabel)
      row()
      add(buildingLabel).padBottom(20f)
      row()
      add(clickButton).width(220f).height(60f)
      row()
      add(buyBuildingButton).width(220f).height(60f)
    }

    stage.addActor(table)
  }

  private fun updateLabels() {
    moneyLabel.setText(String.format(Locale.US, "Money: $%.2f", money))
    passiveLabel.setText(String.format(Locale.US, "Passive income: $%.2f /s", passiveIncome))
    buildingLabel.setText("Buildings: $buildingCount")
    buyBuildingButton.setText(String.format(Locale.US, "Buy Building ($%.0f)", buildingCost))
    buyBuildingButton.isDisabled = money < buildingCost
  }

  private fun createButtonStyle(font: BitmapFont): TextButton.TextButtonStyle {
    val upTexture = buildTexture(Color(0.22f, 0.55f, 0.22f, 1f))
    val downTexture = buildTexture(Color(0.16f, 0.4f, 0.16f, 1f))
    val disabledTexture = buildTexture(Color(0.25f, 0.25f, 0.25f, 1f))

    return TextButton.TextButtonStyle().apply {
      this.font = font
      fontColor = Color.WHITE
      disabledFontColor = Color(0.8f, 0.8f, 0.8f, 1f)
      up = TextureRegionDrawable(upTexture)
      down = TextureRegionDrawable(downTexture)
      disabled = TextureRegionDrawable(disabledTexture)
    }
  }

  private fun buildTexture(color: Color): Texture {
    val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
    pixmap.setColor(color)
    pixmap.fill()
    val texture = Texture(pixmap)
    pixmap.dispose()
    disposables += texture
    return texture
  }

  override fun show() {
    Gdx.input.inputProcessor = stage
  }

  override fun render(delta: Float) {
    money += passiveIncome * delta
    updateLabels()
    ScreenUtils.clear(0.1f, 0.1f, 0.12f, 1f)
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
