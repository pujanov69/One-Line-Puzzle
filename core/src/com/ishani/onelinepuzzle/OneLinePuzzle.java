package com.ishani.onelinepuzzle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ishani.onelinepuzzle.actors.BaseActor;
import com.ishani.onelinepuzzle.actors.Starfish;
import com.ishani.onelinepuzzle.actors.Turtle;
import com.ishani.onelinepuzzle.actors.Whirlpool;

public class OneLinePuzzle extends GameBeta {

	private Turtle turtle;
	private Starfish starfish;
	private BaseActor ocean;


	@Override
	public void initialize() {
		ocean = new BaseActor(0,0, mainStage);
		ocean.loadTexture("water.jpg");
		ocean.setSize(800, 600);

		starfish = new Starfish(380,380, mainStage);

		turtle = new Turtle(20,20,mainStage);
	}

	@Override
	public void update(float dt) {
		if (turtle.overlaps(starfish) && !starfish.isCollected() ) {
			starfish.collect();
			Whirlpool whirl = new Whirlpool(0,0, mainStage);
			whirl.centerAtActor( starfish );
			whirl.setOpacity(0.25f);
			BaseActor youWinMessage = new BaseActor(0,0,mainStage);
			youWinMessage.loadTexture("you-win.png");
			youWinMessage.centerAtPosition(400,300);
			youWinMessage.setOpacity(0);
			youWinMessage.addAction( Actions.delay(1) );
			youWinMessage.addAction( Actions.after( Actions.fadeIn(1) ) );
		}

	}

}
