package com.tbawor.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.tbawor.idle.IdleGame

private const val WINDOW_WIDTH = 500
private const val WINDOW_HEIGHT = 500
private const val WINDOW_TITLE = "Idle game"

object DesktopLauncher {

  @JvmStatic
  fun main(args: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration().apply {
      setTitle(WINDOW_TITLE)
      useVsync(true)
      setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
      setForegroundFPS(60)
      setMaximized(true)
    }

    Lwjgl3Application(IdleGame(), config)
  }

}
