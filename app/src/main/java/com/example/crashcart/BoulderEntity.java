package com.example.crashcart;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.os.Build;
import android.os.VibrationEffect;
import android.view.SurfaceView;
import java.util.Random;

public class BoulderEntity implements EntityBase, Collidable{

    public final static BoulderEntity Instance = new BoulderEntity();

    private BoulderEntity(){

    }

    // 1. Declare the use of spritesheet using Sprite class.
    private Sprite spritesheet = null;

    private boolean isDone = false;
    private boolean isInit = false;

    // Variables to be used or can be used.
    public float xPos, yPos, xDir, yDir, lifeTime;

    // For use with the TouchManager.class
    private boolean hasTouched = false;

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
        spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.boulder), 1, 1, 1);

        // 3. Get some random position of x and y
        // This part is really random, You can have different ways to move or interact with your character.

        // In java util library, random generator
        //Random ranGen = new Random();

        xPos = _view.getWidth() / 2;
        yPos = _view.getHeight() / 2;

//        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
//        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        if (GameSystem.Instance.GetIsPaused())
            return;

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

    public static BoulderEntity Create()
    {
        BoulderEntity result = new BoulderEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_BOULDER);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_BOULDER;}

    @Override
    public String GetType() {
        return "BoulderEntity";
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
        return spritesheet.GetHeight() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {
        //if (_other.GetType() == "StarEntity") //Another Entity
        {
            //SetIsDone(true);
            //Play an audio
        }
    }
}
