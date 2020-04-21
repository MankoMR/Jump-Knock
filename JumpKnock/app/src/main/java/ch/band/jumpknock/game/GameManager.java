package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ch.band.jumpknock.R;


/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */

/**
 * The type Game manager.
 * It handles the the overall Logic for the Game.
 * Specifically updating gamestate, interacting with UI, handling win / lose conditions and managing platforms
 */
public class GameManager {
	private static final String TAG = GameManager.class.getCanonicalName();
	/**
	 * The Game variables.
	 */
	GameVariables gameVariables;
	private long playTimeNs;
	//should always be in the middle of the screen.
	private float currentHeight;
	private float reachedHeight;
	private Player player;
	private ArrayList<Platform> platforms = new ArrayList<>();
	private Random r;
	private boolean isPaused;
	private boolean isStopped;
	private boolean isGameOver = false;

	private UiNotifier uiNotifier;
	private Handler gameRunner;
	private static int FPS = 60;

	/**
	 * Instantiates a new Game manager.
	 *
	 * @param uiNotifier    the ui notifier
	 * @param gameVariables the game variables
	 */
	public GameManager(UiNotifier uiNotifier,GameVariables gameVariables){
		this.uiNotifier = uiNotifier;
		this.gameVariables = gameVariables;
		r = new Random();
		player = new Player(gameVariables,R.drawable.jumper);
		isPaused = isStopped = false;
		gameRunner = new Handler();
		gameRunner.postDelayed(createGameLoop(gameRunner),1000);

	}

	/**
	 * gibt den wert aus isPaused zurück
	 *
	 * @return boolean
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * speichert den mitgebenen wert in paused
	 *
	 * @param paused the paused
	 */
	public void setPaused(boolean paused) {
		isPaused = paused;
	}

	/**
	 * gibt den wert von isStopped zurück
	 *
	 * @return boolean
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * speichert den mitgebenen wert in isStopped
	 *
	 * @param stopped the stopped
	 */
	public void setStopped(boolean stopped) {
		isStopped = stopped;
	}

	/**
	 * It creates the Game loop
	 * the Runnable posts itself to the runner to run again.
	 * This results in a "while" loop which only can be broken out by setting isStopped to true.
	 * @return the game loop as a runnable
	 */
	private Runnable createGameLoop(Handler runner){
		return new Runnable() {
			@Override
			public void run() {
				int deltaTime = update();
				if(!isStopped){
					long toWait = 1000 / FPS - deltaTime / 1_000_000;
					toWait = toWait < 0 ? 0 : toWait;
					//Log.d(getClass().getSimpleName(),"Run loop in "+ toWait + "ms");
					runner.postDelayed(this,toWait);
				}
			}
		};
	}

	/**
	 *
	 * @return current height
	 */
	public float getCurrentHeight() {return currentHeight; }

	/**
	 * This methods gets called in a loop and runs the game-logic.
	 * When appropriate it commands which action needs to be taken through the UiNotifier.
	 * @return the deltatime of @GetDeltaTime.
	 */
	public int update(){
		int deltaTime = GetDeltaTime();
		if (isPaused)
			return deltaTime;
		int speedPerSecond = 400;
		float adjustedSpeed = speedPerSecond * ((float)deltaTime / GameVariables.getSecToNanoSec());
		//Log.d(TAG,"delta: "+deltaTime+" adjustedSpeed: "+adjustedSpeed);
		//currentHeight += adjustedSpeed;

		float delta = player.update(gameVariables, currentHeight,deltaTime);

		float playerheight = player.position.y + gameVariables.getPlayerSize().y;
		currentHeight += delta;
		if(currentHeight >= reachedHeight)
			reachedHeight = currentHeight;

		if(delta <= 0){
			testForCollision();
			if(player.position.y + gameVariables.getGameFieldSize().y < reachedHeight){
				boolean cheatMode = false;
				if(!isGameOver && !cheatMode){
					uiNotifier.gameOver(reachedHeight);
					isGameOver = true;
					Log.d(TAG,"Lost Game: Player Velocity: "+player.getVelocity().toString()+" Position:"+ player.position.toString());
				}
				if(cheatMode){
					player.getVelocity().y = 1.2500f;
					uiNotifier.playerCollidedWith(null);
				}
				//currentHeight +=delta;
			}
		}
		//player.position.y += adjustedSpeed;
		float distance = 0;
		if (platforms.size() !=  0)
			distance = currentHeight + gameVariables.getGameFieldSize().y - platforms.get(platforms.size() - 1).position.y;
		//Log.d(TAG,"Condition for Adding: platforms.size()["+platforms.size()+"] == 0 || distance["+distance+"] > platformSize.y["+platformSize.y+"] * 3 ["+platformSize.y * 5+"]");

		//Add platform if distance is sufficiently big enough
		if (platforms.size() == 0 || distance > gameVariables.getPlatformSize().y * 5 ){
			Platform p = new Platform();
			p.position = new PointF(
					r.nextFloat() * (gameVariables.getGameFieldSize().x - gameVariables.getPlatformSize().x),
					currentHeight + gameVariables.getGameFieldSize().y + gameVariables.getPlatformSize().y);
			p.drawableId = gameVariables.getPlatformDrawIds()[r.nextInt(gameVariables.getPlatformDrawIds().length)];

			//Clouds are one time use Platforms
			if(p.drawableId == R.drawable.cloud1 || p.drawableId == R.drawable.cloud2 || p.drawableId == R.drawable.cloudwithlightning)
				p.setOneTimeUse(true);

			//Blocks with
			if(r.nextBoolean() && (
					p.drawableId == R.drawable.grasblock1
					|| p.drawableId == R.drawable.grasblock2
					|| p.drawableId == R.drawable.stoneblockwithgrass1
					|| p.drawableId == R.drawable.stoneblockwithgrass2 )){
				p.setDecoration(new Decoration(gameVariables.getDecorationDrawIds()[r.nextInt(gameVariables.getDecorationDrawIds().length)],r.nextFloat()));
				//p.getDecoration().setPosition(r.nextFloat());
				//p.getDecoration().setDrawableId(gameVariables.getDecorationDrawIds()[r.nextInt(gameVariables.getDecorationDrawIds().length)]);
			}
			platforms.add(p);
			uiNotifier.addPlatform(p);
		}
		// Remove platforms if they are out of the screen.
		for(int i = 0;i < platforms.size();i++){
			Platform plat = platforms.get(i);
			if(plat.position.y + gameVariables.getPlatformSize().y * 3 - currentHeight <= 0){
				uiNotifier.removePlatform(plat);
				platforms.remove(i);
				i--;
			}
		}
		uiNotifier.updateUi(reachedHeight);
		uiNotifier.UpdateGame(platforms,player, currentHeight,gameVariables);
		return deltaTime;
	}

	/**
	 * It should only be called Once per update or when the Delta is to big
	 * to properly Update the game (eg: after pausing the game, the delta will be way to big,
	 * because the pause counts a one update.)
	 *
	 * @return the delta in time in nanoseconds between the current method- and last method-call.
	 */
	private int GetDeltaTime(){
		long now = System.nanoTime();
		long diff = now - playTimeNs;
		playTimeNs = now;
		return (int)diff;
	}

	/**
	 * berechnet die bewegung
	 *
	 * @param acceleration the acceleration
	 * @param deltaTime    the delta time in Ns
	 */
	public void getAcceleration(float acceleration, long deltaTime){
		float pixPerSec = player.getMaxSpeedPerSec();
		float calcSpeed = pixPerSec * (float)deltaTime / gameVariables.getSecToNanoSec();
		//multiplied with constant to dampen the "feedback" of the sensor
		float multiplier = calcSpeed / 1.5f;
		//Log.d(TAG," multiplier: "+ multiplier+" acceleration: "+ acceleration + " result: "+ acceleration * multiplier);
		acceleration *= multiplier ;

		//Cap velocity at the max speed allowed for the player.
		this.player.getVelocity().x += acceleration;
		if(player.getVelocity().x < -calcSpeed)
			player.getVelocity().x = -calcSpeed;
		if(player.getVelocity().x > calcSpeed)
			player.getVelocity().x = calcSpeed;
		//Log.d(TAG,"Velocity: "+ player.velocity.toString()+ " Position: "+ player.position.toString());
	}
	/**
	 * Tests all platforms for a collision with the player.
	 * If a collision occurs, it executes the apropriate game-logic.
	 */
	private void testForCollision(){
		for (int i = 0; i < platforms.size();i++){
			Platform plat = platforms.get(i);
			//test if overlays with the platform horizontally.
			if(plat.position.x <= player.position.x + gameVariables.getPlayerSize().x && plat.position.x+ gameVariables.getPlatformSize().x >= player.position.x)
				//checking if the player overlays with the platform vertically
				if (plat.position.y + gameVariables.getPlatformSize().y >= player.position.y && plat.position.y <= player.position.y){
					player.getVelocity().y = 1.2500f;
					uiNotifier.playerCollidedWith(plat);
					if(plat.isOneTimeUse()) {
						gameRunner.postDelayed(()->{
							uiNotifier.removePlatform(plat);
							this.platforms.remove(plat);
						},100);
					}
				}
		}
	}
}
