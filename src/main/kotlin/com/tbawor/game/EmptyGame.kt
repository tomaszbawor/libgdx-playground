package com.tbawor.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

private const val CLEAR_RED = 0.07f
private const val CLEAR_GREEN = 0.07f
private const val CLEAR_BLUE = 0.1f
private const val CLEAR_ALPHA = 1f

class EmptyGame : ApplicationAdapter() {
    override fun render() {
        Gdx.gl.glClearColor(CLEAR_RED, CLEAR_GREEN, CLEAR_BLUE, CLEAR_ALPHA)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }
}
