package com.example.crashcart;

import android.os.Vibrator;
import android.os.VibrationEffect;
import android.os.Build;
import android.view.SurfaceView;

public class VibrateManager {
    public final static VibrateManager Instance = new VibrateManager();

    private Vibrator _vibrator;

    private VibrateManager() {
    }

    public void Init(SurfaceView _view) {
        _vibrator = (Vibrator) _view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
    }

    public void startVibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            _vibrator.vibrate(VibrationEffect.createOneShot(150, 10));
        } else {
            long pattern[] = {0, 50, 0};
            _vibrator.vibrate(pattern, -1);
        }
    }

    public void stopVibrate(){
        _vibrator.cancel();
    }
}