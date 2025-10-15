package com.tbawor.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.tbawor.game.EmptyGame

private const val WINDOW_WIDTH = 500
private const val WINDOW_HEIGHT = 500
private const val WINDOW_TITLE = "Game"

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration().apply {
            setTitle(WINDOW_TITLE)
            setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
            useVsync(true)
            setForegroundFPS(60)
        }

        Lwjgl3Application(EmptyGame(), config)
    }
}
