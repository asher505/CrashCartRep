package com.example.crashcart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import kotlin.random.URandomKt;

public class LoseScreenDialogFragment extends DialogFragment {
    // To use this to check if the dialog button is pressed
    public static boolean IsShown = false;
    private String m_Text = "";
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        IsShown = true;

        //Use the Builder class for creating the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // builder.setMessage ();
        // builder.setPostiveButton ();

        // int x, y = 0;

        builder.setTitle("You Scored " + CartEntity.roundedScore);
        builder.setMessage("Enter your name.");

// Set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StateManager.Instance.ChangeState("Mainmenu"); // Press Start button
                m_Text = input.getText().toString();

                if (m_Text.isEmpty()) {
                    m_Text = "Guest";
                }

                // save player data
                GameSystem.Instance.savePlayerData(m_Text, CartEntity.roundedScore);

                // We will define what to do when +ve button is pressed
                // To pause if press YES.
                // Gamesystem have written 2 methods to check if pause.
                // In the entity,we will need to indicate in the update() method to pause if pause is triggered
                IsShown = false;

            }
        });

        //Create our customised Dialog popup
        return builder.create();
    }
    // Part of the dialog pop up interface to indicate to cancel or not to use it.
    @Override
    public void onCancel (DialogInterface dialog){
        IsShown = false;
    }

    @Override
    public void onDismiss (DialogInterface dialog){
        IsShown = false;
    }


}
