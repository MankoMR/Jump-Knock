package ch.band.jumpknock;


import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ch.band.jumpknock.CalibrateActivity;
import ch.band.jumpknock.R;
import ch.band.jumpknock.RecordActivity;
import ch.band.jumpknock.game.GameManager;
import ch.band.jumpknock.game.GameVariables;
import ch.band.jumpknock.game.Platform;
import ch.band.jumpknock.game.Player;
import ch.band.jumpknock.game.sound.SoundEngine;
import ch.band.jumpknock.game.UiNotifier;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class GameActivity extends AppCompatActivity implements UiNotifier, SensorEventListener {

    private static final String TAG = ch.band.jumpknock.GameActivity.class.getCanonicalName();
    public static final  String REACHED_HEIGHT = "reached_height";
    GameManager gameManager;
    TextView tvReachedHeight;
    //All moving things except the player are in the flContainer
    FrameLayout flContainer;
    //The the player needs to be always in the foreground. That makes a separate framelayout necessary.
    FrameLayout flPlayerContainer;

    //To manage the platforms groupview a mapping is necessesary to associate a platform to its visual oponent.
    HashMap<Platform, ConstraintLayout> platforms = new HashMap<>();
    //managing the player is easier
    ImageView player;
    TextView heightoffset;
    int platforWidthScale = 5;
    private SensorManager sensorManager;
    private Sensor movementSensor;
    private float[] ofSetValues = new float[4];
    private Random random;
    private Handler handler;
    //to stop the record activity from appearing when pressing back button
    private boolean cancelGameOverRunnable = false;
    private SoundEngine soundEngine = new SoundEngine();

    /**
     * creirt die Game activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        //Initialising variables.
        tvReachedHeight = findViewById(R.id.TvHeight);
        flContainer = findViewById(R.id.FlPlatformContainer);
        flPlayerContainer = findViewById(R.id.FlPlayerContainer);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        random = new Random();
        ofSetValues = getIntent().getFloatArrayExtra(CalibrateActivity.SENSOR_OFFSETS);
        handler = new Handler();
        //InitDebugStuff();
        InitSounds();
        ExecuteGameInitialisation();
    }

    /**
     * beenden das game beim zurück button click
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.cancelGameOverRunnable = true;
    }

    /**
     * fügt den Sound hinzu
     */
    private void InitSounds(){
        int[] bounceSounds = new int[3];
        int[] bloobSounds = new int[7];
        int[] fallSound = new int[1];
        bounceSounds[0] = R.raw.bounce1;
        bounceSounds[1] = R.raw.bounce2;
        bounceSounds[2] = R.raw.bounce3;

        bloobSounds[0] = R.raw.bloop01;
        bloobSounds[1] = R.raw.bloop02;
        bloobSounds[2] = R.raw.bloop03;
        bloobSounds[3] = R.raw.bloop04;
        bloobSounds[4] = R.raw.bloop05;
        bloobSounds[5] = R.raw.bloop06;
        bloobSounds[6] = R.raw.bloop07;
        fallSound[0] = R.raw.fall;
        //fallSound.setVolume(0.3f,0.3f);
        soundEngine.add("bounce",bounceSounds,1f,getApplicationContext());
        soundEngine.add("bloop",bloobSounds,1f,getApplicationContext());
        soundEngine.add("fall",fallSound,0.3f,getApplicationContext());
    }

    /**
     * startet das spiel
     */
    private void ExecuteGameInitialisation(){
        final UiNotifier notifier = this;
        final SensorEventListener sensorEventListener = this;

        //The game can only be initialised once the size of flContainer is known.
        //Therefore this listener is required. It gets called as soon as the size changes / get initialised.
        flContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(flContainer.getWidth() == 0 || flContainer.getHeight() == 0)
                    return;
                //The width of the platforms we want is 1/platforWidthScale of the width of the screen.
                //To calculate the sizes we need to initalize one Platform and one Imageview for the player
                //By default the platform (Constraintlayout with images) fills the width of the Screen.

                int width = flContainer.getWidth();
                Drawable dplat = getResources().getDrawable(R.drawable.eearthblock1);
                //the height of the layout includes the height of the decoration, therefore directly taking the height is necessary.
                float Scalefactor = dplat.getIntrinsicWidth() < width ? (float) dplat.getIntrinsicWidth() / width: (float) width /dplat.getIntrinsicWidth();
                Log.d(TAG,"Scalefactor: "+Scalefactor);
                PointF platformSize = new PointF(dplat.getIntrinsicWidth() * Scalefactor / platforWidthScale,dplat.getIntrinsicHeight() * Scalefactor / platforWidthScale);



                //getting the size of the player
                player = new ImageView(flPlayerContainer.getContext());
                player.setImageResource(R.drawable.jumper);
                player.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //this adjusts the scaling so that the aspect ratio stays the same.
                player.setAdjustViewBounds(true);
                flPlayerContainer.addView(player);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) player.getLayoutParams();
                Drawable dPlayer = getResources().getDrawable(R.drawable.jumper);
                lp.width = (int) (dPlayer.getIntrinsicWidth() * Scalefactor / platforWidthScale);
                lp.height = (int) (dPlayer.getIntrinsicHeight() * Scalefactor / platforWidthScale);
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                flPlayerContainer.updateViewLayout(player,lp);

                //Adding Height to the View because when the Platform is near the Top or Bottom it Resizes to still fit into the Container
                ConstraintLayout.LayoutParams containerParams = (ConstraintLayout.LayoutParams) flContainer.getLayoutParams();
                PointF screenSize = new PointF(flContainer.getWidth(), containerParams.height);
                containerParams.height =(int)platformSize.y * 6 + flContainer.getHeight();
                containerParams.width = lp.width + flContainer.getWidth();
                flContainer.setLayoutParams(containerParams);
                ConstraintLayout.LayoutParams playerContainerParams = (ConstraintLayout.LayoutParams) flPlayerContainer.getLayoutParams();
                playerContainerParams.height = containerParams.height;
                playerContainerParams.width = containerParams.width;
                flPlayerContainer.setLayoutParams(playerContainerParams);

                PointF gameFieldSize = new PointF(containerParams.width, containerParams.height);
                PointF playerSize = new PointF(lp.width,lp.height);

                //now initialising the gamemanager, and stop listening anymore.
                GameVariables gameVariables = new GameVariables(
                        screenSize, gameFieldSize, playerSize, platformSize,
                        R.drawable.jumper ,new int[]{
                        R.drawable.eearthblock1, R.drawable.eearthblock2,
                        R.drawable.grasblock1, R.drawable.grasblock2,
                        R.drawable.stoneblock1, R.drawable.stoneblock2,
                        R.drawable.stoneblockwithgrass1, R.drawable.stoneblockwithgrass2,
                        R.drawable.cloud1, R.drawable.cloud2 },
                        new int[]{
                                R.drawable.tree, R.drawable.bush1, R.drawable.bush2 }
                );
                gameManager = new GameManager(notifier,gameVariables);
                movementSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                sensorManager.registerListener(sensorEventListener,movementSensor,SensorManager.SENSOR_DELAY_GAME);

                Log.d(TAG,"Screen: "+screenSize.toString()+" | Player: "+playerSize.toString()+" | Plat: "+platformSize);
                gameManager.start();
                flContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });
    }

    /**
     * füegt eine platform auf dem layout hinzu
     * @param platform
     * @return
     */
    private ConstraintLayout Add(Platform platform){
        LayoutInflater rl = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ConstraintLayout template = (ConstraintLayout) rl.inflate(R.layout.placeable_template,null);
        ImageView plat = template.findViewById(R.id.IvPlatform);
        ImageView dec = template.findViewById(R.id.IvDeco);

        plat.setImageResource(platform.getDrawableId());
        ConstraintLayout.LayoutParams platLayout = (ConstraintLayout.LayoutParams) plat.getLayoutParams();
        int width = flContainer.getWidth();
        platLayout.width = width / platforWidthScale;
        template.updateViewLayout(plat,platLayout);

        ConstraintLayout.LayoutParams decLatout = (ConstraintLayout.LayoutParams) dec.getLayoutParams();
        decLatout.width = platLayout.width / 3;
        if (platform.getDecoration() != null){
            dec.setImageResource(platform.getDecoration().getDrawableId());
            decLatout.horizontalBias = platform.getDecoration().getPosition();
        }else {
            dec.setVisibility(View.GONE);
        }
        template.updateViewLayout(dec,decLatout);

        flContainer.addView(template);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)template.getLayoutParams();
        layoutParams.leftMargin = (int)platform.getPosition().x;
        layoutParams.bottomMargin = (int)platform.getPosition().y;
        layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        flContainer.updateViewLayout(template,layoutParams);
        return  template;
    }

    /**
     * rueft von der superklass die onStart methode auf
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * rueft von der superklass die onStop methode auf
     */
    @Override
    protected void onStop() {
        this.gameManager.stop();
        super.onStop();
    }

    /**
     * rueft von der superklass die onPause methode auf
     * und pausiert das spiel
     */
    @Override
    protected void onPause() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.gameManager.pause();
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    /**
     * wird aufgerufen beim wiederstarten der activity
     */
    @Override
    protected void onResume() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if(gameManager != null)
            this.gameManager.start();
        if(movementSensor != null)
            sensorManager.registerListener(this,movementSensor,SensorManager.SENSOR_DELAY_GAME);
        time = System.nanoTime();
        super.onResume();

    }

    /**
     * beendet den sound effeckt
     */
    @Override
    protected void onDestroy() {
        soundEngine.release();
        super.onDestroy();
    }

    @Override
    public void addPlatform(Platform platform) {
        platforms.put(platform,Add(platform));
    }
    @Override
    public void removePlatform(Platform platform) {
        ConstraintLayout layout = platforms.get(platform);
        flContainer.removeView(layout);
        platforms.remove(platform);
    }
    @Override
    public void UpdateGame(List<Platform> platforms, Player player, float reachedHeight,GameVariables gameVariables) {
        for (Platform platform: platforms){
            ConstraintLayout layout = this.platforms.get(platform);
            FrameLayout.LayoutParams layoutParams =  (FrameLayout.LayoutParams) layout.getLayoutParams();
            layoutParams.bottomMargin =  (int)(platform.getPosition().y - reachedHeight);
            layoutParams.leftMargin = (int)platform.getPosition().x;
            layout.setLayoutParams(layoutParams);
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.player.getLayoutParams();
        lp.bottomMargin = (int)(player.getPosition().y - reachedHeight);
        lp.leftMargin = (int)player.getPosition().x;
        this.player.setLayoutParams(lp);
    }

    /**
     * Aktualisiert die auf dem Bildschirm angezeigte höche.
     * @param height höche vom Spieler
     */
    @Override
    public void updateUi(float height) {
        int showHeight = (int) height;
        tvReachedHeight.setText(String.valueOf(showHeight));

    }

    /**
     * bendet das spiel und ruft die activity record auf
     * @param height
     */
    @Override
    public void gameOver(float height) {
        soundEngine.play("fall");
        handler.postDelayed(()->{
            if(cancelGameOverRunnable) {
                return;
            }

            Intent recordIntent = new Intent(getBaseContext(), RecordActivity.class);
            recordIntent.putExtra(REACHED_HEIGHT,(int)height);
            startActivity(recordIntent);
            gameManager.stop();
            //soundEngine.release();
            finish();
        },4000);
    }

    /**
     * gibt den passenden soundeffekt für die plattform zurück
     * @param platform
     */
    @Override
    public void playerCollidedWith(Platform platform) {
        if(platform.isOneTimeUse()){
            soundEngine.play("bloop");
        }
        else
            soundEngine.play("bounce");
    }

    long time;
    /*
     * Called when there is a new sensor event.  Note that "on changed"
     * is somewhat of a misnomer, as this will also be called if we have a
     * new reading from a sensor with the exact same sensor values (but a
     * newer timestamp).
     *
     * <p>See {@link SensorManager SensorManager}
     * for details on possible sensor types.
     * <p>See also {@link SensorEvent SensorEvent}.
     *
     * <p><b>NOTE:</b> The application doesn't own the
     * {@link SensorEvent event}
     * object passed as a parameter and therefore cannot hold on to it.
     * The object may be part of an internal pool and may be reused by
     * the framework.
     *
     * @param event the {@link SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        long deltatime  = event.timestamp - time;
        //Log.d(TAG,"OldTime: "+time + "TimeStamp: "+event.timestamp +" delta: "+deltatime);
        time = event.timestamp;

        //Log.d(TAG, event.sensor.getName()+" " + Arrays.toString(event.values));

		if(event.values == null || (event.values[1] == Float.NaN))
			return;
		gameManager.setHorizontalPlayerAcceleration(event.values[1] - ofSetValues[1],deltatime);
		//Log.d(TAG,"Raw: "+ event.values[1] + " Normalized:"+ event.values[1] * deltatime );
	}

	/**
	 * Called when the accuracy of the registered sensor has changed.  Unlike
	 * onSensorChanged(), this is only called when this accuracy value changes.
	 *
	 * <p>See the SENSOR_STATUS_* constants in
	 * {@link SensorManager SensorManager} for details.
	 *
	 * @param sensor
	 * @param accuracy The new accuracy of this sensor, one of
	 *                 {@code SensorManager.SENSOR_STATUS_*}
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}
