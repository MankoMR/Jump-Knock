package ch.band.jumpknock;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class CalibrateActivity extends AppCompatActivity implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor movementSensor;
	Button ok;
	public static final String SENSOR_OFFSETS = "sensor_offsets";
	private float[] ofSetValues;

	/**
	 * startet die aktivität
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calibrate);
		ok = findViewById(R.id.btn_calibrate_ok);

		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		movementSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	}

	/**
	 * wird beim wiederstarten der aktivität aufgerufen
	 */
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this,movementSensor,SensorManager.SENSOR_DELAY_GAME);
		ok.setOnClickListener(view ->{
			Intent i = new Intent(this, GameActivity.class);
			i.putExtra(SENSOR_OFFSETS,ofSetValues);
			startActivity(i);
			finish();
		});
	}

	/**
	 * wird beim pausieren der aktivität aufgerufen
	 */
	@Override
	protected void onPause() {
		ok.setOnClickListener(null);
		sensorManager.unregisterListener(this);
		super.onPause();
	}

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
		ofSetValues = event.values;
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
