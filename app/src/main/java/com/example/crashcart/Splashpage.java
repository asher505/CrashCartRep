package com.example.crashcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class Splashpage extends Activity {
    protected boolean _active = true;
    // Boolean use to check for whether the page is active and running
    protected int _splashTime = 5000;

    // meant for wait so after something the splashpage will auto transit to the
    //main menu
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashpage);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(200);
                        if (_active) {
                            waited += 200;
                        }
                    }
                } catch (InterruptedException e) {
                    //do nothing
                } finally {
                    finish();
                    //Create new activity based on and intent with CurrentActivity
                    Intent intent = new Intent(Splashpage.this, Mainmenu.class);
                    startActivity(intent);
                }
            }
        };
        splashTread.start();
    }
    // Create the view from splashpage.xm
        //thread for displaying the Splash Screen

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            _active = false;
        }
        return true;
    }
// boolean active = false = dun want this view anymore = go to the next screen
}
