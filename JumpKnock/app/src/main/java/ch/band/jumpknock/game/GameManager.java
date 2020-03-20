package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ch.band.jumpknock.R;
/*
 *Copyright (c) 2020 Manuel Koloska, All rights reserved.
 */
public class GameManager {
	private static final String TAG = GameManager.class.getCanonicalName();
	GameVariables gameVariables;
	private long playTimeNs;
	//should always be in the middle of the screen.
	private float currentHeight;
	private float reachedHeight;
	private Player player;
	private ArrayList<Platform> platforms = new ArrayList<>();
	private Random r;
	public boolean isPaused;
	public boolean isStopped;
	private boolean isGameOver = false;

	private UiNotifier uiNotifier;
	private Handler gameRunner;
	private static int FPS = 60;
	Platform top;

	public GameManager(UiNotifier uiNotifier,GameVariables gameVariables){
		this.uiNotifier = uiNotifier;
		this.gameVariables = gameVariables;
		r = new Random();
		player = new Player(gameVariables,R.drawable.jumper);
		isPaused = isStopped = false;
		gameRunner = new Handler();
		gameRunner.postDelayed(createGameLoop(),1000);

	}
	private Runnable createGameLoop(){
		final GameManager gameManager = this;
		return new Runnable() {
			@Override
			public void run() {
				int deltaTime = update();
				if(!isStopped){
					long toWait = 1000 / FPS - deltaTime / 1_000_000;
					toWait = toWait < 0 ? 0 : toWait;
					//Log.d(getClass().getSimpleName(),"Run loop in "+ toWait + "ms");
					gameRunner.postDelayed(this,toWait);
				}
			}
		};
	}
	public void ChangeDisplayVariables(GameVariables gameVariables){
		this.gameVariables = gameVariables;
	}
	public float getCurrentHeight() {return currentHeight; }

	public int update(){
		int deltaTime = GetDeltaTime();
		if (isPaused)
			return deltaTime;
		int speedPerSecond = 400;
		float adjustedSpeed = speedPerSecond * ((float)deltaTime / GameVariables.SEC_TO_NANO_SEC);
		//Log.d(TAG,"delta: "+deltaTime+" adjustedSpeed: "+adjustedSpeed);
		//currentHeight += adjustedSpeed;

		float delta = player.update(gameVariables, currentHeight,deltaTime);
		float playerheight = player.position.y + gameVariables.playerSize.y;
		currentHeight += delta;
		if(currentHeight >= reachedHeight)
			reachedHeight = currentHeight;

		if(delta <= 0){
			testForCollision();
			if(player.position.y + gameVariables.gameFieldSize.y < reachedHeight){
				boolean cheatMode = false;
				if(!isGameOver && !cheatMode){
					uiNotifier.gameOver(reachedHeight);
					isGameOver = true;
					Log.d(TAG,"Lost Game: Player Velocity: "+player.velocity.toString()+" Position:"+ player.position.toString());
				}
				if(cheatMode){
					player.velocity.y = 1.2500f;
					uiNotifier.playerCollidedWith(null);
				}
				//currentHeight +=delta;
			}
		}
		//player.position.y += adjustedSpeed;
		float distance = 0;
		if (platforms.size() !=  0)
			distance = currentHeight + gameVariables.gameFieldSize.y - platforms.get(platforms.size() - 1).position.y;
		//Log.d(TAG,"Condition for Adding: platforms.size()["+platforms.size()+"] == 0 || distance["+distance+"] > platformSize.y["+platformSize.y+"] * 3 ["+platformSize.y * 5+"]");

		//Add platform if distance is sufficiently big enough
		if (platforms.size() == 0 || distance > gameVariables.platformSize.y * 5 ){
			Platform p = new Platform();
			p.position = new PointF(
					r.nextFloat() * (gameVariables.gameFieldSize.x - gameVariables.platformSize.x),
					currentHeight + gameVariables.gameFieldSize.y + gameVariables.platformSize.y);
			p.drawableId = gameVariables.platformDrawIds[r.nextInt(gameVariables.platformDrawIds.length)];

			//Clouds are one time use Platforms
			if(p.drawableId == R.drawable.cloud1 || p.drawableId == R.drawable.cloud2 || p.drawableId == R.drawable.cloudwithlightning)
				p.isOneTimeUse = true;

			//Blocks with
			if(r.nextBoolean() && (
					p.drawableId == R.drawable.grasblock1
					|| p.drawableId == R.drawable.grasblock2
					|| p.drawableId == R.drawable.stoneblockwithgrass1
					|| p.drawableId == R.drawable.stoneblockwithgrass2 )){
				p.decoration = new Decoration();
				p.decoration.position = r.nextFloat();
				p.decoration.drawableId = gameVariables.decorationDrawIds[r.nextInt(gameVariables.decorationDrawIds.length)];
			}
			platforms.add(p);
			uiNotifier.addPlatform(p);
		}
		// Remove platforms if they are out of the screen.
		for(int i = 0;i < platforms.size();i++){
			Platform plat = platforms.get(i);
			if(plat.position.y + gameVariables.platformSize.y * 3 - currentHeight <= 0){
				uiNotifier.removePlatform(plat);
				platforms.remove(i);
				i--;
			}
		}
		uiNotifier.updateUi(reachedHeight);
		uiNotifier.UpdateGame(platforms,player, currentHeight,gameVariables);
		return deltaTime;
	}
	private int GetDeltaTime(){
		long now = System.nanoTime();
		long diff = now - playTimeNs;
		playTimeNs = now;
		return (int)diff;
	}
	public void getAcceleration(float acceleration, long deltaTime){
		float pixPerSec = player.maxSpeedPerSec;
		float calcSpeed = pixPerSec * (float)deltaTime / gameVariables.SEC_TO_NANO_SEC;
		//multiplied with constant to dampen the "feedback" of the sensor
		float multiplier = calcSpeed / 1.5f;
		//Log.d(TAG," multiplier: "+ multiplier+" acceleration: "+ acceleration + " result: "+ acceleration * multiplier);
		acceleration *= multiplier ;

		//Cap velocity at the max speed allowed for the player.
		this.player.velocity.x += acceleration;
		if(player.velocity.x < -calcSpeed)
			player.velocity.x = -calcSpeed;
		if(player.velocity.x > calcSpeed)
			player.velocity.x = calcSpeed;
		//Log.d(TAG,"Velocity: "+ player.velocity.toString()+ " Position: "+ player.position.toString());
	}
	private void testForCollision(){
		for (int i = 0; i < platforms.size();i++){
			Platform plat = platforms.get(i);
			//test if overlays with the platform horizontally.
			if(plat.position.x <= player.position.x + gameVariables.playerSize.x && plat.position.x+ gameVariables.platformSize.x >= player.position.x)
				//checking if the player overlays with the platform vertically
				if (plat.position.y + gameVariables.platformSize.y >= player.position.y && plat.position.y <= player.position.y){
					player.velocity.y = 1.2500f;
					uiNotifier.playerCollidedWith(plat);
					if(plat.isOneTimeUse) {
						gameRunner.postDelayed(()->{
							uiNotifier.removePlatform(plat);
							this.platforms.remove(plat);
						},100);
					}
				}
		}
	}
	public boolean removePlatform(Platform platform){
		return platforms.remove(platform);
	}
}
