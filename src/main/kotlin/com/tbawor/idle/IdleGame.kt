package com.tbawor.idle

import com.badlogic.gdx.Game

class IdleGame : Game() {
    override fun create() {
        setScreen(IdleGameScreen())
    }
}
