package com.example.crashcart;

import android.util.Log;
import android.view.MotionEvent;

// Created by TanSiewLan2021

// Manages the touch events

public class TouchManager {

    public final static TouchManager Instance = new TouchManager();


    private final Object lock = new Object();


    private TouchManager(){

    }

    public enum TouchState{
        NONE,
        DOWN,
        MOVE
    }

    private static final String TAG ="Touche Manager";
    private int posX, posY;

    boolean moved;
    public float x1 = -5,x2 = -5,y1 = -5,y2 = -5;
    private TouchState status = TouchState.NONE; //Set to default as NONE

    public boolean HasTouch(){  // Check for a touch status on screen
        return status == TouchState.DOWN || status == TouchState.MOVE;
    }

    public boolean IsDown(){
        return status == TouchState.DOWN;
    }

    public boolean IsUp(){
        return status == TouchState.NONE;
    }

    public void ResetMoved() {
        moved = false;

//        TouchManager.Instance.y2
    }

    public void Moved() {
        moved = true;
//        x1 = 0;
//        x2 = 0;
//        y1 = 0;
//        y2 = 0;
    }
    public int GetPosX(){
        return posX;
    }

    public int GetPosY(){
        return posY;
    }

    public void Update(int _posX, int _posY, int _motionEventStatus){
        posX = _posX;
        posY = _posY;

        switch (_motionEventStatus){
            case MotionEvent.ACTION_DOWN:
                status = TouchState.DOWN;
                x1 = _posX;
                y1 = _posY;
                Log.d(TAG, "FirstX: " + x1);

                break;

            case MotionEvent.ACTION_MOVE:
                status = TouchState.MOVE;
                break;

            case MotionEvent.ACTION_UP:
                 x2 = _posX;
                 y2 = _posY;
                status = TouchState.NONE;
                Log.d(TAG, "SecondX: " + x2);
                ResetMoved();
                break;
        }
    }
}

