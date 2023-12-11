package com.example.crashcart;

import android.graphics.Canvas;
import android.view.SurfaceView;
import java.util.Random;

public class CartEntity implements EntityBase, Collidable{
    
	 // 1. Declare the use of spritesheet using Sprite class.
     private Sprite spritesheet = null;

    private boolean isDone = false;
    private boolean isInit = false;

	 // Variables to be used or can be used.
    public float xPos, yPos, xDir, yDir, lifeTime;
    
	 // For use with the TouchManager.class
    private boolean hasTouched = false;

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
        spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.minecart_sprite), 1, 4, 4);

        // 3. Get some random position of x and y
      // This part is really random, You can have different ways to move or interact with your character.

        // In java util library, random generator
        Random ranGen = new Random();

        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();

        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        if (GameSystem.Instance.GetIsPaused())
            return;

        spritesheet.Update(_dt);
        // 5. Deal with the touch on screen for interaction of the image using collision check
        if (TouchManager.Instance.HasTouch())
        {
            // 6. Check collision here!!!
            float imgRadius = spritesheet.GetWidth() * 0.5f;

            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched) {
                // Collided!
                hasTouched = true; // We want to move the character anywhere in the scene.

                // 7. Drag the sprite around the screen
                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();
                xDir += xDir * _dt;
                yDir += yDir * _dt;
            }
        }
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

    public static CartEntity Create()
    {
        CartEntity result = new CartEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_CART);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){return ENTITY_TYPE.ENT_CART;}

    @Override
    public String GetType() {
        return "CartEntity";
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
        return spritesheet.GetHeight() * 0.4f;
    }

    @Override
    public void OnHit(Collidable _other) {
        switch (_other.GetType()) {
            case "BoulderEntity":
                VibrateManager.Instance.startVibrate(1000);
                SetIsDone(true);
                break;
            case "ArrowEntity":
                VibrateManager.Instance.startVibrate(50);
                //push cart back a square
                break;
            case "BarrierEntity":
                VibrateManager.Instance.startVibrate(50);
                //push cart back a square
                break;
        }
    }
}
