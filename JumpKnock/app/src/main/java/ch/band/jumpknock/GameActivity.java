package ch.band.jumpknock;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ch.band.jumpknock.game.GameManager;
import ch.band.jumpknock.game.GameVariables;
import ch.band.jumpknock.game.Platform;
import ch.band.jumpknock.game.Player;
import ch.band.jumpknock.game.UiNotifier;
import ch.band.jumpknock.storage.Record;
import ch.band.jumpknock.storage.RecordRepository;

public class GameActivity extends AppCompatActivity implements UiNotifier, SensorEventListener {
    private static final String TAG = GameActivity.class.getCanonicalName();
    public static final  String REACHED_HEIGHT = "reached_height";
    GameManager gameManager;
    TextView tvReachedHeight;
    //All moving things are in the flContainer
    FrameLayout flContainer;
    //For Debug Purposes
    LinearLayout debugContainer;
    Button btnBounce, btnGameOver, btnFinish;
    EditText etHeight;
    //To manage the platforms groupview a mapping is necessesary to associate a platform to its visual oponent.
    HashMap<Platform, ConstraintLayout> platforms = new HashMap<>();
    //managing the player is easier
    ImageView player;
    int platforWidthScale = 3;
    private SensorManager sensorManager;
    private Sensor movementSensor;
    private float[] ofSetValues = new float[4];
    private MediaPlayer[] bounceSounds = new MediaPlayer[3];
    private MediaPlayer backgroundMusic;
    private MediaPlayer fallSound;
    private Random random;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Initialising variables.
        tvReachedHeight = findViewById(R.id.TvHeight);
        flContainer = findViewById(R.id.FlGameContainer);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        random = new Random();
        ofSetValues = getIntent().getFloatArrayExtra(CalibrateActivity.SENSOR_OFFSETS);

        InitDebugStuff();
        InitSounds();
        ExecuteGameInitialisation();
    }
    private void InitSounds(){
        bounceSounds[0] = MediaPlayer.create(getBaseContext(), R.raw.bounce1);
        bounceSounds[1] = MediaPlayer.create(getBaseContext(), R.raw.bounce2);
        bounceSounds[2] = MediaPlayer.create(getBaseContext(), R.raw.bounce3);
        backgroundMusic = MediaPlayer.create(getBaseContext(), R.raw.bounce1);
        fallSound = MediaPlayer.create(getBaseContext(), R.raw.fall);
        fallSound.setVolume(0.3f,0.3f);
    }
    private void ReleaseSounds(){
        for(MediaPlayer player:bounceSounds){
            player.release();
        }
        backgroundMusic.release();
        fallSound.release();
    }
    private void playBounce(){
        MediaPlayer bouncer = bounceSounds[random.nextInt(3)];
        bouncer.seekTo(0);
        bouncer.start();
    }
    private void playFall(){
        fallSound.seekTo(0);
        fallSound.start();
    }
    private void InitDebugStuff(){
        debugContainer = findViewById(R.id.LlDebug);
        btnBounce = findViewById(R.id.BtnBounce);
        btnGameOver = findViewById(R.id.BtnGameOver);
        btnFinish = findViewById(R.id.BtnGameFinished);
        etHeight = findViewById(R.id.EtvHeight);
        etHeight.setText(random.nextInt(20001)+"");

        btnBounce.setOnClickListener((view)->{
            playBounce();
        });
        btnGameOver.setOnClickListener(view -> {
            playFall();
        });
        btnFinish.setOnClickListener(view ->{
            gameOver(random.nextInt(20001));
        });
    }
    private void NavigateGameOver(int reachedHeight){
        RecordRepository recordRepository = new RecordRepository(getApplicationContext());
        boolean isTopTen = recordRepository.IsInTopTen(new Record(0,"",reachedHeight));
        if(isTopTen){
        }else {

        }
    }
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
                player = new ImageView(flContainer.getContext());
                player.setImageResource(R.drawable.jumper);
                player.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //this adjusts the scaling so that the aspect ratio stays the same.
                player.setAdjustViewBounds(true);
                flContainer.addView(player);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) player.getLayoutParams();
                Drawable dPlayer = getResources().getDrawable(R.drawable.jumper);
                //TODO: Calculate Correct Size
                lp.width = (int) (dPlayer.getIntrinsicWidth() * Scalefactor / platforWidthScale);
                lp.height = (int) (dPlayer.getIntrinsicHeight() * Scalefactor / platforWidthScale);
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                flContainer.updateViewLayout(player,lp);

                //Adding Height to the View because when the Platform is near the Top or Bottom it Resizes to still fit into the Container
                ConstraintLayout.LayoutParams containerParams = (ConstraintLayout.LayoutParams) flContainer.getLayoutParams();
                PointF screenSize = new PointF(flContainer.getWidth(), containerParams.height);
                containerParams.height =(int)platformSize.y * 6 + flContainer.getHeight();
                containerParams.width = lp.width + flContainer.getWidth();
                flContainer.setLayoutParams(containerParams);

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
                flContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    private ConstraintLayout Add(Platform platform){
        LayoutInflater rl = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ConstraintLayout template = (ConstraintLayout) rl.inflate(R.layout.placeable_template,null);
        ImageView plat = template.findViewById(R.id.IvPlatform);
        ImageView dec = template.findViewById(R.id.IvDeco);

        plat.setImageResource(platform.drawableId);
        ConstraintLayout.LayoutParams platLayout = (ConstraintLayout.LayoutParams) plat.getLayoutParams();
        int width = flContainer.getWidth();
        platLayout.width = width / platforWidthScale;
        template.updateViewLayout(plat,platLayout);

        ConstraintLayout.LayoutParams decLatout = (ConstraintLayout.LayoutParams) dec.getLayoutParams();
        decLatout.width = platLayout.width / 3;
        if (platform.decoration != null){
            dec.setImageResource(platform.decoration.drawableId);
            decLatout.horizontalBias = platform.decoration.position;
        }else {
            dec.setVisibility(View.GONE);
        }
        template.updateViewLayout(dec,decLatout);

        flContainer.addView(template);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)template.getLayoutParams();
        layoutParams.leftMargin = (int)platform.position.x;
        layoutParams.bottomMargin = (int)platform.position.y;
        layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        flContainer.updateViewLayout(template,layoutParams);
        return  template;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        this.gameManager.isStopped = true;
        super.onStop();
    }
    @Override
    protected void onPause() {
        this.gameManager.isPaused = true;
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(gameManager != null)
            this.gameManager.isPaused = false;
        if(movementSensor != null)
            sensorManager.registerListener(this,movementSensor,SensorManager.SENSOR_DELAY_GAME);
        time = System.nanoTime();
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        ReleaseSounds();
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
    public void UpdateGame(List<Platform> platforms, Player player, float reachedHeight) {
        for (Platform platform: platforms){
            ConstraintLayout layout = this.platforms.get(platform);
            FrameLayout.LayoutParams layoutParams =  (FrameLayout.LayoutParams) layout.getLayoutParams();
            layoutParams.bottomMargin =  (int)(platform.position.y - reachedHeight);
            layoutParams.leftMargin = (int)platform.position.x;
            layout.setLayoutParams(layoutParams);
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.player.getLayoutParams();
        lp.bottomMargin = (int)(player.position.y - reachedHeight);
        lp.leftMargin = (int)player.position.x;
        this.player.setLayoutParams(lp);
    }
    @Override
    public void updateUi(float height) {
        tvReachedHeight.setText(String.valueOf(height));
    }
    @Override
    public void gameOver(float height) {
        playFall();
        handler.postDelayed(()->{
            Intent recordIntent = new Intent(getBaseContext(), RecordActivity.class);
            recordIntent.putExtra(REACHED_HEIGHT,height);
            startActivity(recordIntent);
            gameManager.isStopped = true;
        },4000);
    }
    @Override
    public void playerCollidedWith(Platform platform) {
        this.playBounce();
    }
    @Override
    public float getSmartPhoneRotation() {
        return 0;
    }

    long time;
    /**
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
        /*
        final float alpha = time / event.timestamp;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        float[] linear_acceleration = new float[3];
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];
        */
        long deltatime  = event.timestamp - time;
        //Log.d(TAG,"OldTime: "+time + "TimeStamp: "+event.timestamp +" delta: "+deltatime);
        time = event.timestamp;

        //Log.d(TAG, event.sensor.getName()+" " + Arrays.toString(event.values));
        gameManager.getAcceleration(event.values[1] - ofSetValues[1],deltatime);
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
