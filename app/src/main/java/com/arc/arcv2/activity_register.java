package com.arc.arcv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class activity_register extends AppCompatActivity implements View.OnClickListener {
    TextView sign_up,back_indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
        back_indicator.setOnClickListener(this);
    }

    private void initUi() {
        //init views
        sign_up = findViewById(R.id.sign_up);
        back_indicator = findViewById(R.id.back_indicator);

        //methods for animation and typeface
        setTypeFace();
        startAnimation();
        changeStatusBar();
    }

    private void changeStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar_color));
    }

    private void setTypeFace()
    {
        Typeface ealing_reg = Typeface.createFromAsset(getAssets(),"fonts/ealing_regular.otf");
        Typeface ealing_bold = Typeface.createFromAsset(getAssets(),"fonts/ealing_black.otf");
        sign_up.setTypeface(ealing_reg);
        back_indicator.setTypeface(ealing_reg);
    }

    private void startAnimation()
    {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.back_indicator:
                onBackPressed();
                break;
        }
    }
}
