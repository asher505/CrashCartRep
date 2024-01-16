package com.example.crashcart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class RenderBackground extends Accelerometer implements EntityBase {

    private static final String TAG ="RenderBack";

	//7. Render a scrolling background
    private Bitmap bmp = null; //Graphic object. bmp is the name defined for this object

    private boolean isDone = false;

    private float xPos = 0, yPos = 0; // Screen is pixel so either 1, 2,3, 0

    int ScreenWidth, ScreenHeight;

    private static Accelerometer accelerometer;
    private Bitmap scaledbmp = null;

//    public RenderBackground(Accelerometer accelerometer) {
//
//        this.accelerometer = accelerometer.Instance;
//    }

    @Override
    public String GetType() {
        return "RenderBackgroundEntity";
    }

    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }


    @Override
    public void Init(SurfaceView _view){
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.cc_railbase);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);
    }


    @Override
    public void Update(float _dt){
        // horizontal scrolling
        // Speed of scrolling

        if (GameSystem.Instance.GetIsPaused())
            return;


        //scroll speed
        yPos += (_dt * 1000) *  accelerometer.Instance.GetTilt();
        if (yPos > ScreenHeight   ){
            yPos = 0;
        }

        Log.d(TAG, "Tilt Y: " + accelerometer.Instance.GetTilt());
    }

    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(scaledbmp, xPos, yPos, null);
        _canvas.drawBitmap(scaledbmp,xPos, yPos - ScreenHeight, null);
    }

    @Override
    public boolean IsInit(){
        return bmp!=null;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_DEFAULT;}

    public static RenderBackground Create() {
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
