package com.example.android.popaptest;

import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemClock.sleep(7000);
        startService(new Intent(this, PopupService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
