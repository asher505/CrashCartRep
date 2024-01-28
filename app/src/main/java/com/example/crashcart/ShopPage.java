package com.example.crashcart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShopPage extends Activity implements OnClickListener, StateBase {

    public static int coinValue;

    protected Typeface myfont;

    public static int selectedCartType = 1; // Initialize to an invalid value
    public static final int DEFAULT_CART_TYPE = 1;
    public static final int BLUE_CART_TYPE = 2;
    public static final int CAT_CART_TYPE = 3;
    private static final String TAG ="shoppagetag ";
    private Button btn_back;
    private ImageButton btn_blue;
    private ImageButton btn_cat;

    private Button btn_use;

    public static TextView coinText;

    @Override
    protected void onCreate(Bundle SaveInstanceState){
        super.onCreate(SaveInstanceState);


        setContentView(R.layout.shoppage);
        coinText = (TextView) findViewById(R.id.coinText);
        coinValue = GameSystem.Instance.GetIntFromSave("Coins");
        if (coinText == null)
        {
            Log.d(TAG, "oncreate coinText is NULL: ");
        }
        else
        {
            Log.d(TAG, "oncreate coinText is NOT NULL: ");
        }
        coinText.setText("Coins: " + coinValue);
        updateCoinText(coinValue);




        //NEVER import R *
        //when android studio tells you to import R, DO NOT do it
        //whenm u see this message, it will mean xml has an error.


        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_blue = (ImageButton) findViewById(R.id.BlueCart);
        btn_blue.setOnClickListener(this);
        btn_cat = (ImageButton) findViewById(R.id.BlueCart);
        btn_cat.setOnClickListener(this);
        btn_use = (Button) findViewById(R.id.btn_use);
        btn_use.setOnClickListener(this);
        // this allows the correct button to be assigned to the object name and
        // for this case button
        // setonclicklistener to the specified button so that we know
        // when the specific button is clicked / touch
        // it knows what to do

        StateManager.Instance.AddState(new ShopPage());
    }

    @Override
    //Invoke a callback method event in the view
    public void onClick(View v){
        //Intent = action to be perofrmed
        //intent is an object provides runtime binding
        //you have to create a new instance of this object to use it
        //we need to check if start or back button is clicked / touched on the screen
        // then after, decide what to do
        // if start button, it will go to another page example gamepage

        Intent intent = new Intent();

//        if (v == btn_start){
//            //intent -> to set to another class which is another page or screen to be launch.
//            //Equal to change screen
//            intent.setClass(this,GamePage.class);
//            StateManager.Instance.ChangeState("MainGame"); // Press Start button
//            //Goes to the MainGameSceneState hence thats why need to set class to Game
//        }
         if (v == btn_back){
             intent.setClass(this,Mainmenu.class);
             StateManager.Instance.ChangeState("Mainmenu");
             startActivity(intent);
        }

        if (v == btn_blue){
            coinValue = GameSystem.Instance.GetIntFromSave("Coins");

            int bluecheck;
            bluecheck = GameSystem.Instance.GetIntFromSave("BlueCart");

            if(coinValue >= 2 && bluecheck == 0)
            {
                coinValue = coinValue - 2;

                updateCoinText(coinValue);
                GameSystem.Instance.SetIntInSave("Coins", coinValue);
                GameSystem.Instance.SetIntInSave("BlueCart", 1);
                Log.d(TAG, "Coins after buy: "+ coinValue);


               // CartEntity.cartBLUE = true;
            }
            if(bluecheck == 1)
            {
                selectedCartType = BLUE_CART_TYPE;
                Log.d(TAG, " selectedCartType : " +  selectedCartType);
            }
            //BE ABLE TO SELECT WHAT CART
        }

        if (v == btn_cat){
            coinValue = GameSystem.Instance.GetIntFromSave("Coins");

            int catcheck;
            catcheck = GameSystem.Instance.GetIntFromSave("CatCart");

            if(coinValue >= 10 && catcheck == 0)
            {
                coinValue = coinValue - 10;

                updateCoinText(coinValue);
                GameSystem.Instance.SetIntInSave("Coins", coinValue);
                GameSystem.Instance.SetIntInSave("CatCart", 1);
                Log.d(TAG, "Coins after buy: "+ coinValue);


                selectedCartType = CAT_CART_TYPE;
                Log.d(TAG, " selectedCartType : " +  selectedCartType);
                // CartEntity.cartBLUE = true;
            }
            if(catcheck == 1)
            {
                selectedCartType = CAT_CART_TYPE;
                Log.d(TAG, " selectedCartType : " +  selectedCartType);
            }
            //BE ABLE TO SELECT WHAT CART
        }

        if(v == btn_use)
        {
            Log.d(TAG, " cartNUM : " +  CartEntity.cartNUM );

            CartEntity.cartNUM = selectedCartType;

        }



//        // exit button
//        else if (v == btn_quit){
//            this.finishAffinity();
//        }

    }

    //Other 3 methods to be written here based on the Android activity lifecycle
    @Override
    protected void onPause(){super.onPause();}

    @Override
    protected void onStop(){super.onStop();}

    @Override
    protected void onDestroy(){super.onDestroy();}

    @Override
    public String GetName(){ // State name for the Main menu
        return "ShopPage";
    }

    @Override
    public void OnEnter(SurfaceView _view) {


        coinValue = GameSystem.Instance.GetIntFromSave("Coins");
        updateCoinText(coinValue);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fruitstarfont.ttf");
    }

    private void updateCoinText(int coinValue) {

        Log.d(TAG, "updateCoinText called: ");
        if (coinText != null) {
            coinText.setText("Coins : " + coinValue);

            Log.d(TAG, "UPDATED COIN VALUE: " + coinValue );
        }
        else {
            Log.d(TAG, "coinText is NULL: ");
        }
    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {

    }

    @Override
    public void Update(float _dt) {

    }
}
