package com.example.crashcart;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2023

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";
    public static final String COIN_KEY= "Coins";
    // Game stuff
    private boolean isPause = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    private boolean isPaused = false;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID, 0);
        sharedPref = GamePage.Instance.getSharedPreferences(COIN_KEY, 0);
        // 2. We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());

        StateManager.Instance.AddState(new ShopPage());
			// Please add state, NextPage.

			// Plese add state, MainGameSceneState.
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new ShopPage());
    }

    public void SaveEditBegin()
    {
        if (editor != null)
            return;

        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        if(editor == null)
            return;

        editor.commit();
        editor = null;
    }

    public void SetIntInSave(String _key, int _value)
    {
        if(editor == null)
            return;
        editor.putInt(_key,_value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 0);
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

//    public void SaveCoins(int coins) {
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt(COIN_KEY, coins);
//        editor.apply();
//    }
//
//    // Retrieve coin value from SharedPreferences
//    public int GetCoins() {
//        return sharedPref.getInt(COIN_KEY, 0); // Default value is 0 if key not found
//    }

}
