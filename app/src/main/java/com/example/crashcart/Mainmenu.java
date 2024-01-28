package com.example.crashcart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Mainmenu extends Activity implements OnClickListener, StateBase {
    //WE have 2 buttons. Start button and Back button
    // Start button, qhen press will go to another page.. maybe gamepage(next week)
    //back button, when press wil go back to splashpage (for now)

    //define buttons. buttons are objects.
    //public static final String COIN_KEY= "Coins";
    private ImageButton btn_start; //int a

    public static final String COIN_KEY= "Coins";

    SharedPreferences sharedPref = null;
    private ImageButton btn_shop; //int a
    private ImageButton btn_lead; //int a

    Bundle SaveInstance;
    static Paint paint = new Paint();
    private Button btn_back;
    protected Typeface myfont;

    public static TextView coinText;
    private int red = 0, green = 0, blue = 0;
    public static int coinValue;

    private static final String TAG ="cointest ";
    @Override
    protected void onCreate(Bundle SaveInstanceState){
        super.onCreate(SaveInstanceState);



        //sharedPref = GamePage.Instance.getSharedPreferences(COIN_KEY, 0);

        //SaveInstance = SaveInstanceState;
            //if (coinValue != null) {

        //}
        //coinValue = GameSystem.Instance.GetIntFromSave("Coins");
        setContentView(R.layout.mainmenu);
        coinText = (TextView) findViewById(R.id.coinText);
        coinText.setText("Coins: " + coinValue);

        if (coinText == null)
        {
            Log.d(TAG, "oncreate coinText is NULL: ");
        }
        else
        {
            Log.d(TAG, "oncreate coinText is NOT NULL: ");
        }


//        coinText = (TextView) findViewById(R.id.coinText);
//        coinText.setText("Coins: " + coinValue);

        //not sure if i need this to swicth to shop
        //setContentView(R.layout.shoppage);

        //NEVER import R *
        //when android studio tells you to import R, DO NOT do it
        //whenm u see this message, it will mean xml has an error.

        btn_start = (ImageButton) findViewById(R.id.btn_ride);
        btn_start.setOnClickListener(this);

        btn_shop = (ImageButton) findViewById(R.id.btn_shop);
        btn_shop.setOnClickListener(this);

        btn_lead = (ImageButton) findViewById(R.id.btn_leaderboard);
        btn_lead.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        // this allows the correct button to be assigned to the object name and
        // for this case button
        // setonclicklistener to the specified button so that we know
        // when the specific button is clicked / touch
        // it knows what to do

        StateManager.Instance.AddState(new Mainmenu());



//        //not sure if i need this to swicth to shop
//        StateManager.Instance.AddState(new ShopPage());
    }

    private void updateCoinText(int coinValue) {


        if (coinText != null) {
            coinText.setText("Coins : " + coinValue);

            Log.d(TAG, "UPDATED COIN VALUE: " + coinValue );
        }
        else {
            Log.d(TAG, "coinText is NULL: ");
        }
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

        if (v == btn_start){
            //intent -> to set to another class which is another page or screen to be launch.
            //Equal to change screen
            intent.setClass(this,GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Press Start button
            //Goes to the MainGameSceneState hence thats why need to set class to Game
        }
        if (v == btn_lead){
            intent.setClass(this,LeadPage.class);
            StateManager.Instance.ChangeState("LeadPage"); // Press shop button
        }
        if (v == btn_shop){

            //change GamePage.class to ShopPage
            intent.setClass(this,ShopPage.class);
            StateManager.Instance.ChangeState("ShopPage"); // Press shop button

        }
        else if (v == btn_back){
//            intent.setClass(this, Nextpage.class);
//            StateManager.Instance.ChangeState("NextPage");
        }

//        // exit button
//        else if (v == btn_quit){
//            this.finishAffinity();
//        }
        startActivity(intent);
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
        return "Mainmenu";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        coinValue = GameSystem.Instance.GetIntFromSave("Coins");
        updateCoinText(coinValue);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fruitstarfont.ttf");
    }





    @Override
    public void OnExit() {

    }


    @Override
    public void Render(Canvas _canvas) {


        paint.setARGB(255, red, green, blue); // Text is black and not transparent
        paint.setTypeface(myfont);
        paint.setTextSize(50);

        _canvas.drawText(String.valueOf(coinValue), 10, 220, paint);
        _canvas.drawText("Coins: " + CoinEntity.coins, 30, 340, paint);

        //Log.d(TAG, "COINS MAINMENU: " + coinValue);
    }

    @Override
    public void Update(float _dt) {



        //Log.d(TAG, "UPDATED VALUE: " + coinValue );

    }
}
