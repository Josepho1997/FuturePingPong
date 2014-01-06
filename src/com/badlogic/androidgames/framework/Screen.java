
package com.badlogic.androidgames.framework;

import android.content.Context;

public abstract class Screen {
    protected final Game game;
    Context context;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
