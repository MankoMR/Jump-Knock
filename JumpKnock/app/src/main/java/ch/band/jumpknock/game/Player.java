package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.hardware.SensorManager;

public class Player extends Placeable {
	public PointF velocity;
	public float gravity;
	public Player(){
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
