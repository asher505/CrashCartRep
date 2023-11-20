package com.example.crashcart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Mainmenu extends Activity implements OnClickListener, StateBase {
    //WE have 2 buttons. Start button and Back button
    // Start button, qhen press will go to another page.. maybe gamepage(next week)
    //back button, when press wil go back to splashpage (for now)

    //define buttons. buttons are objects.

    private Button btn_start; //int a

    private Button btn_back;

    @Override
    protected void onCreate(Bundle SaveInstanceState){
        super.onCreate(SaveInstanceState);

        setContentView(R.layout.mainmenu);

        //NEVER import R *
        //when android studio tells you to import R, DO NOT do it
        //whenm u see this message, it will mean xml has an error.

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        // this allows the correc t button to be assigned to the object name and
        // for this case button
        // setonclicklistener to the specified button so that we know
        // when the specific button is clicked / touch
        // it knows what to do

        StateManager.Instance.AddState(new Mainmenu());
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