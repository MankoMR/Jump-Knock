package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ch.band.jumpknock.R;

public class GameManager {
	private static final String TAG = GameManager.class.getCanonicalName();
	GameVariables gameVariables;
	private long playTimeNs;
	private int heightOffset;
	private Player player;
	private ArrayList<Platform> platforms = new ArrayList<>();
	private Random r;
	public boolean isPaused;
	public boolean isStopped;

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
	public int getHeightOffset() {return heightOffset; }
	public int update(){
		int deltaTime = GetDeltaTime();
		if (isPaused)
			return deltaTime;
		int speedPerSecond = 400;
		float adjustedSpeed = speedPerSecond * ((float)deltaTime / GameVariables.SEC_TO_NANO_SEC);
		//Log.d(TAG,"delta: "+deltaTime+" adjustedSpeed: "+adjustedSpeed);
		heightOffset += adjustedSpeed;
		//player.position.y += adjustedSpeed;
		float distance = 0;
		if (platforms.size() !=  0)
			distance = heightOffset + gameVariables.screenSize.y - platforms.get(platforms.size() - 1).position.y;
		//Log.d(TAG,"Condition for Adding: platforms.size()["+platforms.size()+"] == 0 || distance["+distance+"] > platformSize.y["+platformSize.y+"] * 3 ["+platformSize.y * 5+"]");
		if (platforms.size() == 0 || distance > gameVariables.platformSize.y * 3 ){
			Platform p = new Platform();
			p.position = new PointF(r.nextFloat() * (gameVariables.screenSize.x - gameVariables.platformSize.x),heightOffset+ gameVariables.screenSize.y + gameVariables.platformSize.y);
			p.drawableId = gameVariables.platformDrawIds[r.nextInt(gameVariables.platformDrawIds.length)];
			p.isOneTimeUse = false;
			if(r.nextBoolean()){
				p.decoration = new Decoration();
				p.decoration.position = r.nextFloat();
				p.decoration.drawableId = gameVariables.decorationDrawIds[r.nextInt(gameVariables.decorationDrawIds.length)];
			}
			platforms.add(p);
			uiNotifier.addPlatform(p);

		}
		for(int i = 0;i < platforms.size();i++){
			Platform plat = platforms.get(i);
			if(plat.position.y + gameVariables.platformSize.y * 3 - heightOffset <= 0){
				uiNotifier.removePlatform(plat);
				platforms.remove(i);
				i--;
			}
		}
		player.update(gameVariables,heightOffset,deltaTime);
		uiNotifier.updateUi(heightOffset);
		uiNotifier.UpdateGame(platforms,player,heightOffset);
		return deltaTime;
	}
	private int GetDeltaTime(){
		long now = System.nanoTime();
		long diff = now - playTimeNs;
		playTimeNs = now;
		return (int)diff;
	}
	public void getAcceleration(float acceleration, long deltaTime){
		float pixPerSec = 30;
		float calcSpeed = pixPerSec * (float)deltaTime / gameVariables.SEC_TO_NANO_SEC;
		float multiplier = calcSpeed / 1.5f;

		Log.d(TAG," multiplier: "+ multiplier+" acceleration: "+ acceleration + " result: "+ acceleration * multiplier);
		acceleration *= multiplier ;

		this.player.velocity.x += acceleration;
		if(player.velocity.x < -calcSpeed)
			player.velocity.x = -calcSpeed;
		if(player.velocity.x > calcSpeed)
			player.velocity.x = calcSpeed;
		//Log.d(TAG,"Velocity: "+ player.velocity.toString()+ " Position: "+ player.position.toString());
	}
}
