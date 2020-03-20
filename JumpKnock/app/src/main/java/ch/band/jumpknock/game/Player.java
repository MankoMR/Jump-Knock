package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.annotation.DrawableRes;
/*
 *Copyright (c) 2020 Manuel Koloska, All rights reserved.
 */
public class Player extends Placeable {
	private static final String TAG = Player.class.getCanonicalName();
	public PointF velocity;
	public float gravity;
	public float maxSpeedPerSec;

	public Player(GameVariables gameVariables, @DrawableRes int picRessource){
		super();
		this.position = new PointF(
				gameVariables.gameFieldSize.x / 2 - gameVariables.playerSize.x / 2,
				gameVariables.gameFieldSize.y / 2);
		this.drawableId = picRessource;
		this.velocity = new PointF();
		this.velocity.y = 1.800f;
		gravity = SensorManager.GRAVITY_EARTH;
		maxSpeedPerSec = 30;
		//example commit to show how to push to origin / GitHub.
	}

	@Override
	public void update(int deltaTimeNs) {
		super.update(deltaTimeNs);
	}

	public float update(GameVariables gameVariables, float heightOffset, int deltaTimeNs){
		float calcMaxVelocity = getPositionDelta(maxSpeedPerSec,GameVariables.SEC_TO_NANO_SEC);

		/*
		//Unecessary since it is already capped by GameManager.getAcceleration
		if(velocity.x < -calcMaxVelocity)
			velocity.x = -calcMaxVelocity;
		if(velocity.x > calcMaxVelocity)
			velocity.x = calcMaxVelocity;
		 */

		//Cap falldown speed at max velocity;
		if(velocity.y < -calcMaxVelocity)
			velocity.y = -calcMaxVelocity;
		if(velocity.y > calcMaxVelocity)
			velocity.y = calcMaxVelocity;

		PointF futurePosition = new PointF(
				position.x + getPositionDelta(velocity.x,deltaTimeNs),
				position.y + getPositionDelta(velocity.y,deltaTimeNs));
		float deltaHeight = futurePosition.y - position.y;
		//Case player is out of screen on the left side;
		if(futurePosition.x <= 0)
			position.x = gameVariables.gameFieldSize.x - (futurePosition.x) - gameVariables.playerSize.x;
		//Case player is out of screen on the right side
		else if(futurePosition.x >= gameVariables.gameFieldSize.x - gameVariables.getLeftOrRightMargin()) {
			position.x = futurePosition.x - (gameVariables.gameFieldSize.x - gameVariables.getLeftOrRightMargin());
			if(position.x <= 0)
				position.x = 1;
		}
		//Case player is visible
		else {
			position.x = futurePosition.x;
		}
		position.y = futurePosition.y;
		//Simulate Gravity
		velocity.y -= getPositionDelta(calcMaxVelocity / 70000,deltaTimeNs);
		//Log.d(TAG,"Velocity: "+velocity.toString()+" FuturePosition: "+futurePosition.toString()+" currentPosition:"+position);
		return deltaHeight;
	}
}
