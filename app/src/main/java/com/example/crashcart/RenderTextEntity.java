package com.example.crashcart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

public class RenderTextEntity implements EntityBase {

    // Paint Object - Helps to define color, size and varies use of the type.
    static Paint paint = new Paint();

    // Color - RGB We are going to set 3 variable names to Red Green and Blue. So that we can use a range of numbers from 0 to 255 to play around ..
    // If you create a random numbers from 0 to 255 to be used in these 3 RGB variables, you can get rainbow, Disco text.
    private int red = 0, green = 0, blue = 0;

    private boolean isDone = false;
    private boolean isInit = false;



    // We want to draw text/ have a text that shows the framerate of the build running.  FPS : 60

    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;



    // Define a name to the font object
    protected Typeface myfont;

    @Override
    public String GetType() {
        return "RenderTextEntity";
    }

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        // Load the font.
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fruitstarfont.ttf");
        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;
        //Get the framerate fps number
        long currenttime = System.currentTimeMillis();
        lastTime = currenttime;
        if (currenttime - lastFPSTime > 1000) {
            fps = (frameCount * 1000) / (currenttime - lastFPSTime);
            lastFPSTime = currenttime;
            frameCount = 0;
        }
        frameCount++;


    }

    @Override
    public void Render(Canvas _canvas) {
        paint.setARGB(255, red, green, blue); // Text is black and not transparent
        paint.setTypeface(myfont);
        paint.setTextSize(50);

        _canvas.drawText("FPS " + fps, 30, 80, paint);

        paint.setTextSize(80);

        _canvas.drawText("Score: " + CartEntity.roundedScore, 30, 160, paint);

        _canvas.drawText("Coins: " + CoinEntity.coins, 30, 340, paint);

        String scoreText = String.format("High Score: %d", GameSystem.Instance.GetIntFromSave("Score"));
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);

        _canvas.drawText(scoreText, 10, 220, paint);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.RENDERTEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    public static RenderTextEntity Create()
    {
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_TEXT;
    }
}