package com.example.crashcart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase {

	//7. Render a scrolling background
    private Bitmap bmp = null; //Graphic object. bmp is the name defined for this object

    private boolean isDone = false;

    private float xPos = 0, yPos = 0; // Screen is pixel so either 1, 2,3, 0

    int ScreenWidth, ScreenHeight;

    private Bitmap scaledbmp = null;
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

        yPos += _dt * 750;
        if (yPos > ScreenHeight   ){
            yPos = 0;
        }
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

    public static RenderBackground Create(){
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
