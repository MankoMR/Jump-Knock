package ch.band.jumpknock.game;

import android.graphics.PointF;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ch.band.jumpknock.R;

public class GameManager {
	private static final String TAG = GameManager.class.getCanonicalName();
	public static final int SEC_TO_NANO_SEC = 1_000_000_000;
	public PointF screenSize;
	public PointF playerSize;
	public PointF platformSize;
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

	public GameManager(UiNotifier uiNotifier, PointF screenSize, PointF playerSize, PointF platformSize){
		this.uiNotifier = uiNotifier;
		this.screenSize = screenSize;
		this.playerSize = playerSize;
		this.platformSize = platformSize;
		r = new Random();
		player = new Player();
		player.drawableId = R.drawable.jumper;
		player.position = new PointF(screenSize.x / 2 - playerSize.x / 2,screenSize.y / 2);
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
	public void ChangeDisplayVariables(PointF screenSize,PointF playerSize, PointF platformSize){
		this.screenSize = screenSize;
		this.playerSize = playerSize;
		this.platformSize = platformSize;
	}
	public int getHeightOffset() {return heightOffset; }
	public int update(){
		int deltaTime = GetDeltaTime();
		if (isPaused)
			return deltaTime;
		int speedPerSecond = 400;
		float adjustedSpeed = speedPerSecond * ((float)deltaTime / SEC_TO_NANO_SEC);
		//Log.d(TAG,"delta: "+deltaTime+" adjustedSpeed: "+adjustedSpeed);
		heightOffset += adjustedSpeed;
		//player.position.y += adjustedSpeed;
		float distance = 0;
		if (platforms.size() !=  0)
			distance = heightOffset + screenSize.y - platforms.get(platforms.size() - 1).position.y;
		//Log.d(TAG,"Condition for Adding: platforms.size()["+platforms.size()+"] == 0 || distance["+distance+"] > platformSize.y["+platformSize.y+"] * 3 ["+platformSize.y * 5+"]");
		if (platforms.size() == 0 || distance > platformSize.y * 3 ){
			Platform p = new Platform();
			p.position = new PointF(r.nextFloat() * (screenSize.x - platformSize.x),heightOffset+ screenSize.y + platformSize.y);
			p.drawableId = Platform.PlatformResIds[r.nextInt(Platform.PlatformResIds.length)];
			p.isOneTimeUse = false;
			if(r.nextBoolean()){
				p.decoration = new Decoration();
				p.decoration.position = r.nextFloat();
				p.decoration.drawableId = Decoration.DecResIds[r.nextInt(Decoration.DecResIds.length)];
			}
			platforms.add(p);
			uiNotifier.addPlatform(p);

		}
		for(int i = 0;i < platforms.size();i++){
			Platform plat = platforms.get(i);
			if(plat.position.y + platformSize.y * 3 - heightOffset <= 0){
				uiNotifier.removePlatform(plat);
				platforms.remove(i);
				i--;
			}
		}
		player.update(screenSize,heightOffset,deltaTime);
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
		float calcSpeed = pixPerSec * (float)deltaTime / SEC_TO_NANO_SEC;
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
