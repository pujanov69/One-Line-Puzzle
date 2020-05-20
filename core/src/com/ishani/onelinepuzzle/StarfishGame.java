package com.ishani.onelinepuzzle;

import com.ishani.onelinepuzzle.screen.MenuScreen;

public class StarfishGame extends BaseGame {
    @Override
    public void create() {
        setActiveScreen(new MenuScreen());
    }
}
