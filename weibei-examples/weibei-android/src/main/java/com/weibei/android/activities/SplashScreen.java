package com.weibei.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.weibei.android.R;

public class SplashScreen extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent();
                intent.setClass(SplashScreen.this, PaymentAlternatives.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}