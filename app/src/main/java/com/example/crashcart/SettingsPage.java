package com.example.crashcart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsPage extends Activity implements OnClickListener, StateBase {

    private ImageButton btn_back;

    private SeekBar musicSlider;
    private SeekBar sfxSlider;

    private Switch vibrateSwitch;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SaveInstanceState){
        super.onCreate(SaveInstanceState);
        setContentView(R.layout.settingspage);
        //NEVER import R *
        //when android studio tells you to import R, DO NOT do it
        //whenm u see this message, it will mean xml has an error.

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        musicSlider = (SeekBar) findViewById(R.id.musicSlider);
        musicSlider.setProgress((int)(GameSystem.Instance.GetFloatFromSave("Music") * 100));

        sfxSlider = (SeekBar) findViewById(R.id.sfxSlider);
        sfxSlider.setProgress((int)(GameSystem.Instance.GetFloatFromSave("SFX") * 100));

        vibrateSwitch = (Switch) findViewById(R.id.vibrateSwitch);
        if (GameSystem.Instance.GetIntFromSave("Vibrate") == 0)
            vibrateSwitch.setChecked(true);
        else if (GameSystem.Instance.GetIntFromSave("Vibrate") == 1)
            vibrateSwitch.setChecked(false);
        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    GameSystem.Instance.SetIntInSave("Vibrate", 0);
                if (!b)
                    GameSystem.Instance.SetIntInSave("Vibrate", 1);
            }
        });
        musicSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                GameSystem.Instance.SetFloatInSave("Music",  ((float)i / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sfxSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                GameSystem.Instance.SetFloatInSave("SFX",  ((float)i / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // this allows the correct button to be assigned to the object name and
        // for this case button
        // setonclicklistener to the specified button so that we know
        // when the specific button is clicked / touch
        // it knows what to do


        StateManager.Instance.AddState(new SettingsPage());
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

        if (v == btn_back){
            intent.setClass(this,Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu");
        }

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
        return "SettingsPage";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

    }



    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {
//        paint.setARGB(255, red, green, blue); // Text is black and not transparent
//        paint.setTypeface(myfont);
//
//        String scoreText = String.format("High Score: %d", GameSystem.Instance.GetIntFromSave("Score"));
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(64);
//        _canvas.drawText(scoreText, 500, 200, paint);


    }
    public int GetRenderLayer(){
        return LayerConstants.RENDERTEXT_LAYER;
    }
    @Override
    public void Update(float _dt) {

    }
}
