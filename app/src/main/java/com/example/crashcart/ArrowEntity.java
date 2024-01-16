package com.example.crashcart;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.os.Build;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.SurfaceView;

import kotlin.math.UMathKt;
import kotlin.random.Random;
import kotlin.random.URandomKt;

public class ArrowEntity implements EntityBase, Collidable{

    public final static ArrowEntity Instance = new ArrowEntity();

    private ArrowEntity(){

    }

    // 1. Declare the use of spritesheet using Sprite class.
    private Sprite spritesheet = null;

    private boolean isDone = false;
    private boolean isInit = false;

    // Variables to be used or can be used.
    public float xPos, yPos, xDir, yDir, lifeTime;

    // For use with the TouchManager.class
    private boolean hasTouched = false;

    private static Accelerometer accelerometer;
    private Vibrator _vibrator;

    int ScreenWidth, ScreenHeight;

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
        // New method using our own resource manager : Returns pre-loaded one if exists
        // 2. Loading spritesheet
        spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.arrow_sprite), 1, 3, 3);

        // 3. Get some random position of x and y
        // This part is really random, You can have different ways to move or interact with your character.

        // In java util library, random generator
        //Random ranGen = new Random();
        int lane = Random.Default.nextInt(3);
        float sideOffset = _view.getWidth() / 10;
        switch (lane){
            case 0:
                xPos = (_view.getWidth() / 9) + sideOffset;
                break;
            case 1:
                xPos = (_view.getWidth() / 9) * 3 + sideOffset * 2;
                break;
            case 2:
                xPos = (_view.getWidth() / 9) * 5 + sideOffset * 2;
                break;
        }

        yPos = -_view.getHeight();

//        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
//        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        yPos += _dt * 1800  * (accelerometer.Instance.GetTilt() * 0.8);

        // 4. Update spritesheet
        spritesheet.Update(_dt);
    }





    @Override
    public void Render(Canvas _canvas) {

        // This is for our sprite animation!
        spritesheet.Render(_canvas, (int)xPos, (int)yPos);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.PLAYER_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    public static ArrowEntity Create()
    {
        ArrowEntity result = new ArrowEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_ARROW);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_ARROW;}

    @Override
    public String GetType() {
        return "ArrowEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return spritesheet.GetHeight() * 0.3f;
    }

    @Override
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "CartEntity") //Another Entity
        {
            SetIsDone(true);
        }
        if (_other.GetType() == "BoulderEntity")
        {
            AudioManager.Instance.PlayAudio(R.raw.arrowsfx, 1f);
            SetIsDone(true);
        }
    }
}
