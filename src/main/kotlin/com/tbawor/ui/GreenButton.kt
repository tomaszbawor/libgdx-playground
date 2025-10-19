package com.tbawor.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable

/**
 * A TextButton subclass that uses the green button graphics from the CraftPix UI spritesheet.
 *
 * The constructor accepts a [disposables] list where created textures are registered for disposal
 * alongside the screen that owns them.
 */
class GreenButton(
	text: String,
	disposables: MutableList<Disposable>,
) : TextButton(text, buildStyle(disposables)) {
	companion object {
		// Hardcoded sprite regions for the green button on the CraftPix Buttons.png
		private const val UP_X: Int = 3
		private const val UP_Y: Int = 112
		private const val UP_W: Int = 42
		private const val UP_H: Int = 16

		private const val DOWN_X: Int = 3
		private const val DOWN_Y: Int = 96
		private const val DOWN_W: Int = 42
		private const val DOWN_H: Int = 16

		private fun buildStyle(disposables: MutableList<Disposable>): TextButtonStyle {
			// Load the spritesheet from classpath and ensure pixel-perfect filtering
			val texture =
				Texture(
					com.badlogic.gdx.Gdx.files
						.classpath("assets/ui/craftpix-gui/PNG/Buttons.png"),
				).apply {
					setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
				}
			// Register texture for disposal with the screen
			disposables += texture

			val upRegion = TextureRegion(texture, UP_X, UP_Y, UP_W, UP_H)
			val downRegion = TextureRegion(texture, DOWN_X, DOWN_Y, DOWN_W, DOWN_H)

			val upDrawable = TextureRegionDrawable(upRegion)
			val downDrawable = TextureRegionDrawable(downRegion)
			val disabledDrawable = upDrawable.tint(Color(0.3f, 0.3f, 0.3f, 1f))

			return TextButtonStyle().apply {
				font = UiFactory.pixelFont()
				fontColor = Color.WHITE
				disabledFontColor = Color(0.8f, 0.8f, 0.8f, 1f)
				up = upDrawable
				down = downDrawable
				disabled = disabledDrawable
			}
		}
	}
}
