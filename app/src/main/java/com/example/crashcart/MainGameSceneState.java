package com.example.crashcart;

import android.app.GameState;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import kotlin.random.Random;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    //public static final MainGameSceneState Instance = new MainGameSceneState();
    private float timer = 0.0f;
    float obstacleTimer = 0f;
    int obstacleRandom;
    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        AudioManager.Instance.PlayAudio(R.raw.bgm, 0.9f);
        // 3. Create Background 
        RenderBackground.Create();
        // Example to include another Renderview for Pause Button
        CartEntity.Create();
        PauseButtonEntity.Create();
        RenderTextEntity.Create();
    }
    @Override
    public void OnExit() {
        AudioManager.Instance.StopAudio(R.raw.bgm);
        AudioManager.Instance.Release();
			// 4. Clear any instance instantiated via EntityManager.
        EntityManager.Instance.Clean();
        // 5. Clear or end any instance instantiated via GamePage.
        GamePage.Instance.finish();
       
    }


    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {



        ObstacleGenerator(_dt);
        ObstacleGenerator(_dt);
        ObstacleGenerator(_dt);

        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown()) {

            //6. Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
        }
    }

    private void ObstacleGenerator(float _dt)
    {
        obstacleTimer += _dt;
        // generate obstacles
        if (obstacleTimer > 3f) {
            obstacleTimer = 0f;
            obstacleRandom = Random.Default.nextInt(100);
            if (obstacleRandom < 40) //40%
                BarrierEntity.Create();
            else if (obstacleRandom < 70) //30%
                ArrowEntity.Create();
            else
                BoulderEntity.Create(); //30%
        }
    }
}



