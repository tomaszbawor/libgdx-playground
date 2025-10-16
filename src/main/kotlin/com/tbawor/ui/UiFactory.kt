package com.tbawor.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 * Centralized UI factory to create common UI components and styles.
 * Screens should use these helpers instead of duplicating UI code.
 */
object UiFactory {
  // Shared game font loaded from resources (TTF). Ensures consistent visuals.
  private var sharedPixelFont: BitmapFont? = null

  fun pixelFont(): BitmapFont {
    if (sharedPixelFont == null) {
      // Load TTF font from classpath resources using FreeType
      val handle = com.badlogic.gdx.Gdx.files.classpath("fonts/boldpixels/BoldPixels.ttf")
      val generator = com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator(handle)
      val params = com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter().apply {
        size = 24
        color = Color.WHITE
        // Keep crisp/pixelated appearance
        magFilter = Texture.TextureFilter.Nearest
        minFilter = Texture.TextureFilter.Nearest
      }
      sharedPixelFont = generator.generateFont(params).apply {
        setUseIntegerPositions(true)
        region.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
      }
      generator.dispose()
    }
    return sharedPixelFont as BitmapFont
  }

  fun disposeShared() {
    sharedPixelFont?.dispose()
    sharedPixelFont = null
  }

  fun createLabelStyle(font: BitmapFont, color: Color = Color.WHITE): Label.LabelStyle =
    Label.LabelStyle(font, color)

  /**
   * Creates a TextButton with a generated style using solid color textures.
   * All created textures are registered in the provided [disposables] list, so
   * the caller can dispose them together with the screen.
   */
  // Default colors used across the app for buttons
  private val DEFAULT_UP_COLOR = Color(0.22f, 0.22f, 0.55f, 1f)
  private val DEFAULT_DOWN_COLOR = Color(0.16f, 0.16f, 0.4f, 1f)
  private val DEFAULT_DISABLED_COLOR = Color(0.25f, 0.25f, 0.25f, 1f)
  private val DEFAULT_FONT_COLOR = Color.WHITE
  private val DEFAULT_DISABLED_FONT_COLOR = Color(0.8f, 0.8f, 0.8f, 1f)

  fun createButton(
    text: String,
    disposables: MutableList<Disposable>
  ): TextButton {
    val upTexture = buildTexture(DEFAULT_UP_COLOR, disposables)
    val downTexture = buildTexture(DEFAULT_DOWN_COLOR, disposables)
    val disabledTexture = buildTexture(DEFAULT_DISABLED_COLOR, disposables)

    val style = TextButton.TextButtonStyle().apply {
      this.font = pixelFont()
      this.fontColor = DEFAULT_FONT_COLOR
      this.disabledFontColor = DEFAULT_DISABLED_FONT_COLOR
      up = TextureRegionDrawable(upTexture)
      down = TextureRegionDrawable(downTexture)
      disabled = TextureRegionDrawable(disabledTexture)
    }

    return TextButton(text, style)
  }

  // Building texture form the simple color
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
