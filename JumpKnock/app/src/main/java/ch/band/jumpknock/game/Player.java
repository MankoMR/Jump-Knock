package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.annotation.DrawableRes;

public class Player extends Placeable {
	private static final String TAG = Player.class.getCanonicalName();
	public PointF velocity;
	public float gravity;
	private float maxSpeedPerSec;
	public Player(GameVariables gameVariables, @DrawableRes int picRessource){
		super();
		this.position = new PointF(
				gameVariables.gameFieldSize.x / 2 - gameVariables.playerSize.x / 2,
				gameVariables.getTopOrBottomMargin());
		this.drawableId = picRessource;
		this.velocity = new PointF();
		this.velocity.y = 1.400f;
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

		if(velocity.x < -calcMaxVelocity)
			velocity.x = -calcMaxVelocity;
		if(velocity.x > calcMaxVelocity)
			velocity.x = calcMaxVelocity;
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
			position.x = gameVariables.gameFieldSize.x - (futurePosition.x);
		//Case player is out of screen on the right side
		else if(futurePosition.x >= gameVariables.gameFieldSize.x - gameVariables.getLeftOrRightMargin()) {
			position.x = futurePosition.x - (gameVariables.gameFieldSize.x - gameVariables.getLeftOrRightMargin());
			if(position.x <= 0)
				position.x = 1;
		}
		//Case player is visible
		else {
			position = futurePosition;
		}
		//Simulate Gravity
		velocity.y -= getPositionDelta(calcMaxVelocity / 70000,deltaTimeNs);
		//Log.d(TAG,"Velocity: "+velocity.toString()+" FuturePosition: "+futurePosition.toString()+" currentPosition:"+position);
		return deltaHeight;
	}
}
