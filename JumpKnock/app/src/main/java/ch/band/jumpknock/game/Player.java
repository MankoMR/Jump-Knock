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
	public void update(PointF screenSize,int heightOffset,int deltaTimeNs){
		if(position.x + getPositionDelta(velocity.y,deltaTimeNs) <= 0)
			position.x = position.x + screenSize.x - getPositionDelta(velocity.y,deltaTimeNs);
		else
			position.x = (position.x + getPositionDelta(velocity.x,deltaTimeNs)) % screenSize.x;
		velocity.y += getPositionDelta(gravity* 1000000,deltaTimeNs);
		position.y = (position.y + getPositionDelta(velocity.y,deltaTimeNs));
		if(position.y< heightOffset - screenSize.y){
			velocity.y *= -1;
		}
	}
}
