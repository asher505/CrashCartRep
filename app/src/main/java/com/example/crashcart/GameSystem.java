package com.example.crashcart;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import androidx.fragment.app.FragmentActivity;

// Created by TanSiewLan2023

public class GameSystem extends FragmentActivity {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";
    public static final String COIN_KEY= "Coins";
    // Game stuff
    private boolean isPause = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    private boolean isPaused = false;

    private static final String TAG ="GAMESYSTEM ";

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d(TAG, "GAMESYSTEM CREATED: ");

        setContentView(new GameView(this)); // Surfaceview = GameView
    }
    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        Log.d(TAG, "GAMESYSTEM CREATED: ");
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

    // ============asher
    private void SaveEditBegin()
    {
        if (editor != null)
            return;

        editor = sharedPref.edit();
    }

    private void SaveEditEnd()
    {
        if(editor == null)
            return;

        editor.commit();
        editor = null;
    }

    public void savePlayerData(String playerName, int playerScore) {
        String existingData = sharedPref.getString("playerData", "");
        boolean exists = false;
        // parse the existing player data into a map
        Map<String, Integer> playerScores = new HashMap<>();
        String[] players = existingData.split(";");
        for (String player : players) {
            String[] playerInfo = player.split(",");
            if (playerInfo.length == 2)
            {
                playerScores.put(playerInfo[0], Integer.parseInt(playerInfo[1]));
                if (playerName.equals(playerInfo[0]))
                {
                    exists = true;
                    if (playerScore > Integer.parseInt(playerInfo[1])) {
                        playerScores.put(playerName, playerScore);
                    }
                }
            }
        }

        if (!exists) {
            // player doesnt exist, add a new player
            playerScores.put(playerName, playerScore);
        }
        SaveEditBegin();
        editor.putString("playerData", FormatMapToString(playerScores));
        SaveEditEnd();
    }


    private String FormatMapToString(Map<String, Integer> playerScores) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
            result.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
        }
        Log.d("dawg", result.toString());
        return result.toString();
    }

    public int getPlayerScore(String playerName) {
        String allPlayerData = sharedPref.getString("playerData", "");

        String[] players = allPlayerData.split(";");
        for (String player : players) {
            String[] playerData = player.split(",");
            if (playerData.length == 2 && playerData[0].equals(playerName)) {
                // player found, return the score
                return Integer.parseInt(playerData[1]);
            }
        }
        // player not found
        return -1;
    }

    public String getLeaderboard() {
        String allPlayerData = sharedPref.getString("playerData", "");

        // parse the existing player data into a map
        Map<String, Integer> playerScores = new HashMap<>();
        String[] players = allPlayerData.split(";");
        for (String player : players) {
            String[] playerData = player.split(",");
            if (playerData.length == 2) {
                playerScores.put(playerData[0], Integer.parseInt(playerData[1]));
            }
        }

        // create player list
        List<Map.Entry<String, Integer>> playerList = new ArrayList<>(playerScores.entrySet());

        // sort list by the value
        Collections.sort(playerList, Collections.reverseOrder(Map.Entry.comparingByValue()));

        // format string to display
        StringBuilder leaderboard = new StringBuilder();
        int rank = 1;
        for (Map.Entry<String, Integer> player : playerList) {
            leaderboard.append(rank).append(". ").append(player.getKey()).append(" ").append(player.getValue()).append("\n");
            rank++;
            if (rank > 5)
                break;
        }

        return leaderboard.toString();
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    // =============asher

    // =============ethan
    public void SetIntInSave(String _key, int _value)
    {
        SaveEditBegin();
        if(editor == null)
            return;
        editor.putInt(_key,_value);
        SaveEditEnd();
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 0);
    }
    // ============ethan

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
