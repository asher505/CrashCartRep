package com.example.crashcart;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public interface EntityBase
{
    String GetType();

    //used for entities such as background
    enum ENTITY_TYPE{
        //ENT_PLAYER,
        ENT_CART,
        ENT_BOULDER,
        ENT_COIN,
        ENT_ARROW,
        ENT_BARRIER,
        //ENT_PAUSE,
        ENT_TEXT,
         ENT_PAUSE,
         //ENT_NEXT,
        ENT_DEFAULT
    }



    boolean IsDone();
    void SetIsDone(boolean _isDone);
    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);
    boolean IsInit();
    int GetRenderLayer();
    void SetRenderLayer(int _newLayer);
	ENTITY_TYPE GetEntityType();
}
