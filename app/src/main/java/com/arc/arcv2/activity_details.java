package com.arc.arcv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arc.arcv2.Fragments.choose_department;
import com.arc.arcv2.Utils.Helper;

public class activity_details extends AppCompatActivity implements View.OnClickListener {
    TextView almost_there,choose;
    EditText name,email,id;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initUi();
        done.setOnClickListener(this);
        Toast.makeText(this,
                Helper.user.getUser_phone()+" "+Helper.user.getUser_type(), Toast.LENGTH_SHORT).show();
    }

    private void initUi() {
        almost_there = findViewById(R.id.almost_there);
        choose = findViewById(R.id.choose_photo);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        done = findViewById(R.id.done);
        setTypeFace();
        changeStatusBar();
    }

    private void setTypeFace(){
    Typeface ealing_reg = Typeface.createFromAsset(getAssets(),"fonts/ealing_regular.otf");
    Typeface ealing_bold = Typeface.createFromAsset(getAssets(),"fonts/ealing_black.otf");
        almost_there.setTypeface(ealing_bold);
        choose.setTypeface(ealing_reg);
        name.setTypeface(ealing_bold);
        email.setTypeface(ealing_bold);
        id.setTypeface(ealing_bold);
        done.setTypeface(ealing_reg);
   }

    private void changeStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar_color));
    }

    @Override
    public void onClick(View view) {
       int id = view.getId();
       switch (id)
       {
           case R.id.done :
               inflateFrag(new choose_department());
               break;
       }
    }

    private void inflateFrag(Fragment frag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.replace(R.id.activ_det,frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
