package com.example.android.popaptest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Alon on 30/05/2017.
 */

public class PopupService extends Service {

    private static final String TAG = PopupService.class.getSimpleName();
    WindowManager mWindowManager;
    View mView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showDialog("");
        return super.onStartCommand(intent, flags, startId);
    }

    private void showDialog(String aTitle)
    {
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock((PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "YourServie");
        mWakeLock.acquire();
        mWakeLock.release();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mView = View.inflate(getApplicationContext(), R.layout.popup, null);
        mView.setTag(TAG);

        TextView close = (TextView) mView.findViewById(R.id.dismiss);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideDialog();
            }
        });


        final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER, Gravity.CENTER,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ,
                PixelFormat.RGBA_8888);

        mView.setVisibility(View.VISIBLE);
        mWindowManager.addView(mView, mLayoutParams);
        mWindowManager.updateViewLayout(mView, mLayoutParams);

    }

    private void hideDialog(){
        if(mView != null && mWindowManager != null){
            mWindowManager.removeView(mView);
            mView = null;
        }
        stopService(new Intent(this, PopupService.class));
    }

    @Override
    public void onDestroy() {
        hideDialog();
        super.onDestroy();
    }
}