package com.example.crashcart;

import static android.app.ProgressDialog.show;

//import static com.example.crashcart.CoinEntity.coinValue;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

public class CartEntity extends Accelerometer implements EntityBase, Collidable {

    public static float score;
    public static int roundedScore;
    public static int roundedDist;
    private static TouchManager touchManager;
    private static Accelerometer accelerometer;
    private static Context context;
    private static final String TAG ="cartentity tag";

    public static int coinValue;
    public static int cartNUM;

    public boolean rewardGIVEN = true;
    public static boolean cartBLUE;

	 // 1. Declare the use of spritesheet using Sprite class.
     private Sprite spritesheet = null;

    public static float DistBonus;
    private static int minSwipeDist = 50; // min dist to count a swipe
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
        Log.d("hey", "new: " + Integer.toString(roundedScore));
        //Log.d("hey", "old: " + Integer.toString(GameSystem.Instance.GetIntFromSave("Score")));

        LoseScreenDialogFragment newLose = new LoseScreenDialogFragment();
        newLose.show(GamePage.Instance.getSupportFragmentManager(), "LoseScreen");

        DistBonus = 0;
        isDone = _isDone;
    }
    public CartEntity(TouchManager touchManager, Context context, Accelerometer accelerometer) {

        this.touchManager = TouchManager.Instance;
        this.accelerometer = accelerometer.Instance;
        this.context = context;
    }
    @Override
    public void Init(SurfaceView _view) {
        // New method using our own resource manager : Returns pre-loaded one if exists
        // 2. Loading spritesheet


        //cartNUM = ShopPage.selectedCartType;
        cartNUM = GameSystem.Instance.GetIntFromSave("ChosenCart");
        if(cartNUM == 0)
        {
            cartNUM = 1;
        }

        //Log.d(TAG, " chosencart : " + GameSystem.CHOSENCART  );

        Log.d(TAG, " cartNUM : " + cartNUM );

        //spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.minecart_sprite), 1, 4, 4);
        switch (cartNUM) {
            case 1:
                spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.minecart_sprite), 1, 4, 4);
                Log.d(TAG, "MINECART 1: " );
                break;
            case 2:
                spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.minecart_blue_sprite), 1, 4, 4);
                Log.d(TAG, "MINECART 2: " );
                break;
            case 3:
                spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.minecart_cat_sprite), 1, 4, 4);
                Log.d(TAG, "MINECART 3: " );
                break;

            default:

                break;
        }



        // 3. Get some random position of x and y
      // This part is really random, You can have different ways to move or interact with your character.

        // In java util library, random generator
        Random ranGen = new Random();

        // Define the grid position
        int initialGridX = 2; // Adjust as needed
        int initialGridY = 2; // Adjust as needed


        float gridStep = 280.0f;

        // Set the initial position based on the grid
        xPos = initialGridX * gridStep;
        yPos = 1100 + initialGridY * gridStep;

        // Define the number of rows and columns

        //spacing between cart positions in the grid

       //xPos = 2;  // Set it to the middle grid
        //yPos = _view.getHeight(); ;  // Set it to the bottom grid

        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        int multiplier =  Math.round(6 - accelerometer.Instance.GetTilt()) / 2;

        if (GameSystem.Instance.GetIsPaused())
            return;

        if(yPos > 2000)
        {
            SetIsDone(true);
        }

        score += multiplier * _dt / 4;
        DistBonus += multiplier * _dt / 4 ;


        roundedDist = Math.round(CartEntity.DistBonus * 10);
        Log.d(TAG, "distance bounus" + roundedDist);
        roundedScore = Math.round(CartEntity.score * 10);
        spritesheet.Update(_dt);
        accelerometer.Update(_dt);

        Log.d(TAG, "accc: " + multiplier);



        if (roundedDist == 49)
        {
            rewardGIVEN = false;
        }
        else if (rewardGIVEN == false)
        {
            DistBonus = 0;
            DistanceReward();

        }

        // Define the number of rows and columns
        int numRows = 3;
        int numCols = 3;

        //spacing between cart positions in the grid
        float XgridStep = 280.0f;
        float YgridStep = 280.0f;
        // 5. Deal with the touch on screen for interaction of the image using collision check
        //if (TouchManager.Instance.HasTouch()) {
            float imgRadius = spritesheet.GetWidth() * 0.5f;

            // 6. Check collision here!!!

            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched) {
                hasTouched = true; // We want to move the character  the scene.

                //horizontal swipe
                float valueX = TouchManager.Instance.x2 - TouchManager.Instance.x1;

                //vertical swipe
                float valueY = TouchManager.Instance.y2 - TouchManager.Instance.y1;

                Log.d(TAG, "X1 before swipe: " + TouchManager.Instance.x1);
                Log.d(TAG, "X2 before swipe: " + TouchManager.Instance.x2);

                 //7a. Move the cart to next track if user swipes
                if(Math.abs(valueX) > minSwipeDist && !TouchManager.Instance.moved && Math.abs(valueX) > Math.abs(valueY))
                {
                    if(TouchManager.Instance.x2 != TouchManager.Instance.x1) {   // detect left to right swipe



                            // Snap cart within the grid
                            //pos based on grid

                            if (TouchManager.Instance.x2 > TouchManager.Instance.x1)
                            {
                                xPos += 255;
                                Log.d(TAG, "Right Swipe");
                                TouchManager.Instance.Moved();

                            }
                                //detect right to left swipe
                            else {
                                xPos -= 255;

                                Log.d(TAG, "Left Swipe");
                                TouchManager.Instance.Moved();

                            }


                    }
                }
                if(Math.abs(valueY) > minSwipeDist && !TouchManager.Instance.moved && Math.abs(valueY)> Math.abs(valueX))
                {
                    if(TouchManager.Instance.y2 != TouchManager.Instance.y1) {   // detect left to right swipe



                        // Snap cart within the grid
                        //pos based on grid

                        if (TouchManager.Instance.y2 > TouchManager.Instance.y1)
                        {
                            yPos += 255;
                            Log.d(TAG, "Up Swipe");
                            TouchManager.Instance.Moved();

                        }
                        //detect right to left swipe
                        else {
                            yPos -= 255;

                            Log.d(TAG, "Down Swipe");
                            TouchManager.Instance.Moved();

                        }


                    }

                }

                xPos = Math.round(xPos / XgridStep) * XgridStep;
                yPos = Math.round(yPos / YgridStep) * YgridStep;
                // Ensure the cart stays within grid bounds
                // limit the horizontal grid
                xPos = Math.max(255, Math.min(numCols * XgridStep, xPos));

                // limit the vertical grid
                yPos = Math.max(2200 - numRows * YgridStep, Math.min(2000, yPos));

//                Log.d(TAG, "X1 after: " + TouchManager.Instance.x1);
//                Log.d(TAG, "X2 after: " + TouchManager.Instance.x2);

                // 7b. Drag the sprite around the screen
                //pos based on touch
                //xPos = TouchManager.Instance.GetPosX();
                //yPos = TouchManager.Instance.GetPosY();
//                xDir += xDir * _dt;
//                yDir += yDir * _dt;




                //if xPos > 255, set it to numCols * XgridStep
                //bottommost position cart can be at is (2200 - numRows * YgridStep)
            }
       // }
    }

    public void DistanceReward()
    {
        coinValue = GameSystem.Instance.GetIntFromSave("Coins");
        Log.d(TAG, "cooins before" + coinValue);
        coinValue = coinValue + 10;
        Log.d(TAG, "cooins after" + coinValue);
        GameSystem.Instance.SetIntInSave("Coins", coinValue);
        Log.d(TAG, "REWARD GIVEN" + coinValue);
        rewardGIVEN = true;
    }



//    @Override
//    public void Update(float _dt) {
//
//        if (GameSystem.Instance.GetIsPaused())
//            return;
//
//        spritesheet.Update(_dt);
//        // 5. Deal with the touch on screen for interaction of the image using collision check
//        if (TouchManager.Instance.HasTouch())
//        {
//            // 6. Check collision here!!!
//            float imgRadius = spritesheet.GetWidth() * 0.5f;
//
//            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched) {
//                // Collided!
//                hasTouched = true; // We want to move the character anywhere in the scene.
//
//                // 7. Drag the sprite around the screen
//                xPos = TouchManager.Instance.GetPosX();
//                yPos = TouchManager.Instance.GetPosY();
//                xDir += xDir * _dt;
//                yDir += yDir * _dt;
//            }
//        }
//    }


    @Override
    public void Render(Canvas _canvas) {
        score += 0;
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
        score = 0;
        AudioManager.Instance.PlayLoopAudio(R.raw.railwaysfx, 0.4f);
        CartEntity result = new CartEntity(touchManager, context, accelerometer);
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
                AudioManager.Instance.PlayAudio(R.raw.bouldersfx, GameSystem.Instance.GetFloatFromSave("SFX"));
                VibrateManager.Instance.startVibrate(1000);
                SetIsDone(true);

                break;
            case "ArrowEntity":
                AudioManager.Instance.PlayAudio(R.raw.arrowsfx, GameSystem.Instance.GetFloatFromSave("SFX"));
                VibrateManager.Instance.startVibrate(50);
                //push cart back a square
                yPos += 255;
                break;
            case "BarrierEntity":
                AudioManager.Instance.PlayAudio(R.raw.barriersfx, GameSystem.Instance.GetFloatFromSave("SFX"));
                VibrateManager.Instance.startVibrate(50);
                //push cart back a square
                yPos += 255;
                break;
            case "CoinEntity":
                AudioManager.Instance.PlayAudio(R.raw.coinsfx, GameSystem.Instance.GetFloatFromSave("SFX"));
                VibrateManager.Instance.startVibrate(20);
                //push cart back a square
                break;
        }
    }
}
