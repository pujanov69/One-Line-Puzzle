package com.ishani.onelinepuzzle;

import com.badlogic.gdx.Game;
import com.ishani.onelinepuzzle.screen.BaseScreen;

public abstract class BaseGame extends Game {

    private static BaseGame game;
    public BaseGame()    {
        game = this;
    }

    public static void setActiveScreen(BaseScreen s)    {
        game.setScreen(s);
    }
}
