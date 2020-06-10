package com.arc.arcv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Splash extends AppCompatActivity {
    RelativeLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        main_layout = findViewById(R.id.main_layout);
        Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        main_layout.startAnimation(fade);
        /*Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar_color));
        */

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(
                        new Intent(
                                Splash.this,
                                activity_login.class
                        )
                );
            }
        },1500);

    }
}
