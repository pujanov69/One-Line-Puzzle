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
import com.ishani.onelinepuzzle.actors.Rock;
import com.ishani.onelinepuzzle.actors.Starfish;
import com.ishani.onelinepuzzle.actors.Turtle;
import com.ishani.onelinepuzzle.actors.Whirlpool;

public class OneLinePuzzle extends GameBeta {

	private Turtle turtle;

	private boolean win;


	@Override
	public void initialize() {
		BaseActor ocean = new BaseActor(0,0, mainStage);
		ocean.loadTexture("water.jpg");
		ocean.setSize(800, 600);

		new Starfish(400,400, mainStage);
		new Starfish(500,100, mainStage);
		new Starfish(100,450, mainStage);
		new Starfish(200,250, mainStage);

		new Rock(200,150, mainStage);
		new Rock(100,300, mainStage);
		new Rock(300,350, mainStage);
		new Rock(450,200, mainStage);


		turtle = new Turtle(20,20,mainStage);
		win = false;

	}


	@Override
	public void update(float dt) {
		for (BaseActor rockActor : BaseActor.getList(mainStage, "com.ishani.onelinepuzzle.actors.Rock"))
			turtle.preventOverlap(rockActor);
		//rock.preventOverlap(turtle);

		for (BaseActor starfishActor : BaseActor.getList(mainStage, "com.ishani.onelinepuzzle.actors.Starfish")) {
			Starfish starfish = (Starfish) starfishActor;
			if (turtle.overlaps(starfish) && !starfish.isCollected()) {
				starfish.collect();

				Whirlpool whirl = new Whirlpool(0, 0, mainStage);
				whirl.centerAtActor(starfish);
				whirl.setOpacity(0.25f);

			}

		}
		if (BaseActor.count(mainStage, "com.ishani.onelinepuzzle.actors.Starfish") == 0 && !win) {
			win = true;
			BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
			youWinMessage.loadTexture("you-win.png");
			youWinMessage.centerAtPosition(400, 300);
			youWinMessage.setOpacity(0);
			youWinMessage.addAction(Actions.delay(1));
			youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
		}
	}

}
