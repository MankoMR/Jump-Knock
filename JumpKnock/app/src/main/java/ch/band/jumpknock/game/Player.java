package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.hardware.SensorManager;
import androidx.annotation.DrawableRes;

public class Player extends Placeable {
	public PointF velocity;
	public float gravity;
	private float maxSpeedPerSec;
	public Player(GameVariables gameVariables, @DrawableRes int picRessource){
		super();
		this.position = new PointF(
				gameVariables.gameFieldSize.x / 2 - gameVariables.playerSize.x / 2,
				gameVariables.gameFieldSize.y * 2f / 3f);
		this.drawableId = picRessource;
		this.velocity = new PointF();
		velocity = new PointF();
		gravity = SensorManager.GRAVITY_EARTH;
	}

	@Override
	public void update(int deltaTimeNs) {
		super.update(deltaTimeNs);
	}
	public void update(GameVariables gameVariables,int heightOffset,int deltaTimeNs){


		if(position.x + getPositionDelta(velocity.y,deltaTimeNs) <= gameVariables.playerSize.y / 2)
			position.x = position.x + gameVariables.screenSize.x - getPositionDelta(velocity.y,deltaTimeNs);
		else
			position.x = (position.x + getPositionDelta(velocity.x,deltaTimeNs)) % gameVariables.screenSize.x;
		velocity.y += getPositionDelta(gravity* 1000000,deltaTimeNs);
		position.y = (position.y + getPositionDelta(velocity.y,deltaTimeNs));
		if(position.y< heightOffset - gameVariables.screenSize.y){
			velocity.y *= -1;
		}
	}
}
