package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.hardware.SensorManager;

import androidx.annotation.DrawableRes;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class Player extends Placeable {
	private static final String TAG = Player.class.getCanonicalName();
	private PointF velocity;
	private float gravity;
	private float maxSpeedPerSec;

	public Player(GameVariables gameVariables, @DrawableRes int picRessource){
		super();
		this.position = new PointF(
				gameVariables.getGameFieldSize().x / 2 - gameVariables.getPlayerSize().x / 2,
				gameVariables.getGameFieldSize().y / 2);
		this.drawableId = picRessource;
		this.velocity = new PointF();
		this.velocity.y = 1.800f;
		gravity = SensorManager.GRAVITY_EARTH;
		maxSpeedPerSec = 30;
		//example commit to show how to push to origin / GitHub.
	}

	/**
	 * gibt den wert aus velocity zurück
	 * @return
	 */
	public PointF getVelocity() {
		return velocity;
	}

	/**
	 * speichert den wert in velocity
	 * @param velocity
	 */
	public void setVelocity(PointF velocity) {
		this.velocity = velocity;
	}

	/**
	 * gibt den wert aus gravity zurück
	 * @return
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * speichert den wert in gravity
	 * @param gravity
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	/**
	 * gibt den wert aus maxSpeedPerSec zurück
	 * @return
	 */
	public float getMaxSpeedPerSec() {
		return maxSpeedPerSec;
	}

	/**
	 * speichert den wert in maxSpeedPerSec
	 * @param maxSpeedPerSec
	 */
	public void setMaxSpeedPerSec(float maxSpeedPerSec) {
		this.maxSpeedPerSec = maxSpeedPerSec;
	}

	/**
	 * updatet deltaTime
	 * @param deltaTimeNs
	 */
	@Override
	public void update(int deltaTimeNs) {
		super.update(deltaTimeNs);
	}

	/**
	 * updatet die höhe des spielers
	 * @param gameVariables
	 * @param heightOffset
	 * @param deltaTimeNs
	 * @return
	 */
	public float update(GameVariables gameVariables, float heightOffset, int deltaTimeNs){
		float calcMaxVelocity = calcDistance(maxSpeedPerSec,GameVariables.getSecToNanoSec());

		/*
		//Unecessary since it is already capped by GameManager.setHorizontalPlayerAcceleration
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
				position.x + calcDistance(velocity.x,deltaTimeNs),
				position.y + calcDistance(velocity.y,deltaTimeNs));
		float deltaHeight = futurePosition.y - position.y;
		//Case player is out of screen on the left side;
		if(futurePosition.x <= 0)
			position.x = gameVariables.getGameFieldSize().x - (futurePosition.x) - gameVariables.getPlayerSize().x;
		//Case player is out of screen on the right side
		else if(futurePosition.x >= gameVariables.getGameFieldSize().x - gameVariables.getLeftOrRightMargin()) {
			position.x = futurePosition.x - (gameVariables.getGameFieldSize().x - gameVariables.getLeftOrRightMargin());
			if(position.x <= 0)
				position.x = 1;
		}
		//Case player is visible
		else {
			position.x = futurePosition.x;
		}
		position.y = futurePosition.y;
		//Simulate Gravity
		velocity.y -= calcDistance(calcMaxVelocity / 70000,deltaTimeNs);
		//Log.d(TAG,"Velocity: "+velocity.toString()+" FuturePosition: "+futurePosition.toString()+" currentPosition:"+position);
		return deltaHeight;
	}
}
