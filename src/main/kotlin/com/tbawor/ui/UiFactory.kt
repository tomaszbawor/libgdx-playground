package com.tbawor.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 * Centralized UI factory to create common UI components and styles.
 * Screens should use these helpers instead of duplicating UI code.
 */
object UiFactory {
  fun createLabelStyle(font: BitmapFont, color: Color = Color.WHITE): Label.LabelStyle =
    Label.LabelStyle(font, color)

  /**
   * Creates a TextButton with a generated style using solid color textures.
   * All created textures are registered in the provided [disposables] list, so
   * the caller can dispose them together with the screen.
   */
  fun createButton(
    text: String,
    font: BitmapFont,
    upColor: Color,
    downColor: Color,
    disposables: MutableList<Disposable>,
    disabledColor: Color = Color(0.25f, 0.25f, 0.25f, 1f),
    fontColor: Color = Color.WHITE,
    disabledFontColor: Color = Color(0.8f, 0.8f, 0.8f, 1f)
  ): TextButton {
    val upTexture = buildTexture(upColor, disposables)
    val downTexture = buildTexture(downColor, disposables)
    val disabledTexture = buildTexture(disabledColor, disposables)

    val style = TextButton.TextButtonStyle().apply {
      this.font = font
      this.fontColor = fontColor
      this.disabledFontColor = disabledFontColor
      up = TextureRegionDrawable(upTexture)
      down = TextureRegionDrawable(downTexture)
      disabled = TextureRegionDrawable(disabledTexture)
    }

    return TextButton(text, style)
  }

  private fun buildTexture(color: Color, disposables: MutableList<Disposable>): Texture {
    val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
    pixmap.setColor(color)
    pixmap.fill()
    val texture = Texture(pixmap)
    pixmap.dispose()
    disposables += texture
    return texture
  }
}
