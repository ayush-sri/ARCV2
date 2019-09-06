package com.arc.arcv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class activity_login extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout main_layout;
    TextView set_title,sign_in,
    sign_in_facebook,
    sign_in_google,
    sign_up_pre,
    sign_up,
    forgot_password;
    EditText et_username,et_password;
    Button user_login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();
        user_login_btn.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

    }

    private void initUi() {
        //all layout elements
        main_layout = findViewById(R.id.main_layout);
        set_title = findViewById(R.id.title);
        et_username = findViewById(R.id.username);
        sign_in = findViewById(R.id.sign_in);
        et_password = findViewById(R.id.password);
        sign_in_google = findViewById(R.id.sign_in_google);
        sign_in_facebook = findViewById(R.id.sign_in_facebook);
        user_login_btn = findViewById(R.id.login);
        sign_up_pre = findViewById(R.id.sign_up_pre);
        sign_up = findViewById(R.id.sign_up);
        forgot_password = findViewById(R.id.forgot_password);

        //methods for Animation,etc
        startEnteringAnimation();
        changeStatusBar();
        setTypeFace();
    }

    private void setTypeFace() {
        Typeface ealing_reg = Typeface.createFromAsset(getAssets(),"fonts/ealing_regular.otf");
        Typeface ealing_bold = Typeface.createFromAsset(getAssets(),"fonts/ealing_black.otf");
        set_title.setTypeface(ealing_bold);
        et_username.setTypeface(ealing_reg);
        et_password.setTypeface(ealing_reg);
        user_login_btn.setTypeface(ealing_reg);
        sign_in.setTypeface(ealing_reg);
        sign_up.setTypeface(ealing_bold);
        sign_up_pre.setTypeface(ealing_reg);
        forgot_password.setTypeface(ealing_reg);
    }

    private void changeStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar_color));
    }

    private void startEnteringAnimation() {
        Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation slide_in_right = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
        Animation slide_in_left = AnimationUtils.loadAnimation(this,R.anim.slide_in_left);
        main_layout.startAnimation(fade);
        et_username.startAnimation(slide_in_right);
        et_password.startAnimation(slide_in_left);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.login:
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_up:
                Pair[] pairs = new Pair[1];
                pairs[0]=new Pair<View,String>(sign_in,"sign_in_to_up");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this,pairs);
                startActivity(
                        new Intent(
                                this,
                                activity_register.class
                        ),
                        activityOptions.toBundle()
                );
                break;
            case R.id.forgot_password:
                break;
        }
    }
}
