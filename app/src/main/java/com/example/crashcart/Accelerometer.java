package com.example.crashcart;

import android.app.assist.AssistStructure;
import android.content.Context;
import android.hardware.Sensor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//public class Accelerometer extends AppCompatActivity implements SensorEventListener
public class Accelerometer implements SensorEventListener {

    public final static Accelerometer Instance = new Accelerometer();
    private static final String TAG ="Accel";
    public static float[] values = {1, 1, 1};
    View ball;

    public float tempX, tempY;
    private SensorManager _sensorManager;
    private int ScreenWidth, ScreenHeight;
    private long lastTime = System.currentTimeMillis();

    public void Init(SurfaceView _view) {
        _sensorManager = (SensorManager) _view.getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Log.e(TAG, "INITTTTTTTTTTTTTTTTTTTTT.");

        if (accelerometer != null) {
            _sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

            // Initialize other variables here
            DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
            ScreenWidth = metrics.widthPixels;
            ScreenHeight = metrics.heightPixels;

            // Assign the ball view
            // ball = _view.findViewById(R.id.your_ball_id); // Uncomment and replace with your actual ID


        } else {
            Log.e(TAG, "Accelerometer not available on this device.");
        }
    }


    public static Accelerometer Create(SurfaceView _view) {
        Accelerometer result = new Accelerometer();
        result.Init(_view); // Call the Init method to perform necessary initialization
        //Log.d(TAG, "Created ");
        return result;
    }



    public void Update(float _dt) {

        SensorMove();

        Log.d(TAG, "Updating: " + values[1]);
    }

//    public static void setUpSensorStuff()
//    {
//        sensor = (SensorManager) _view.getContext().getSystemService(Context.SENSOR_SERVICE);
//
//        sensorManager =
//    }



    // Implement the methods from SensorEventListener
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // Handle sensor changes
            values = event.values;
            //values[0] = event.values[0];
            //values[1] = event.values[1];

            Log.d(TAG, "SensorChanged X: " + values[0]);
            Log.d(TAG, "SensorChanged Y: " + values[1]);

            GetTilt();
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {
        // Handle accuracy changes
        return;
    }

    public void SensorMove() {
        // Temp Variables

        //Log.d(TAG, "Sensormove: ");

        // bX and bY are variables used for moving the object
        // values [1] – sensor values for x axis
        // values [0] – sensor values for y axis
        float bX = 0;
        float bY = 0;
        tempX = bX + (values[1] * ((System.currentTimeMillis() - lastTime) / 1000));
        tempY = bY + (values[0] * ((System.currentTimeMillis() - lastTime) / 1000));
       // Log.d(TAG, "SensorMove X: " + tempX);
        //Log.d(TAG, "SensorMove Y: " + tempY);
    }


    public float GetTilt() {

        Log.d(TAG, "Tilt Y: " + values[1]);
        return values[1];
    }
//    public Accelerometer(SurfaceView _view) {
//        // Initialization code
//        Init(_view);
//    }

//    public void BackupSensorMove() {
//        // Temp Variables
//
//        //Log.d(TAG, "Sensormove: ");
//        float tempX, tempY;
//        // bX and bY are variables used for moving the object
//        // values [1] – sensor values for x axis
//        // values [0] – sensor values for y axis
//        float bX = 0;
//        float bY = 0;
//        tempX = bX + (values[1] * ((System.currentTimeMillis() - lastTime) / 1000));
//        tempY = bY + (values[0] * ((System.currentTimeMillis() - lastTime) / 1000));
//
////        // Check if the ball is going out of screen along the x-axis
////        if (tempX <= ball.getWidth()/2 || tempX >= ScreenWidth - ball.getWidth()/2)
////        {
////            // Check if ball is still within screen along the y-axis
////            if ( tempY > ball.getHeight()/2 && tempY < ScreenHeight - ball.getHeight()/2)
////            {
////                bY = tempY;
////            }
////        }
////        // Check if the ball is going out of screen along the y-axis
////        if (tempY <= ball.getHeight()/2 || tempY >= ScreenHeight - ball.getHeight()/2)
////        {
////        // Check if ball is still within screen along the x-axis
////            if (tempX > ball.getWidth()/2 && tempX < ScreenWidth - ball.getWidth()/2)
////            {
////                bX = tempX;
////            }
////        }
//
//        // If not, both axis of the ball's position is still within screen
////        else
////        {
////        // Move the ball with frame independent movement
////            float testX = 0;
////            float testY = 0;
////            bX = testX;
////            bY = testY;
////        }
//    }
}
