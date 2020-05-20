package com.ishani.onelinepuzzle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ishani.onelinepuzzle.OneLinePuzzle;
import com.ishani.onelinepuzzle.StarfishGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new StarfishGame(), config);
	}
}
