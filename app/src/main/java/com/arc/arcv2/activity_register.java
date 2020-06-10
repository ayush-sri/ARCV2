package com.arc.arcv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.arc.arcv2.Model.User;
import com.arc.arcv2.Utils.Helper;

public class activity_register extends AppCompatActivity implements View.OnClickListener {
    TextView number,setup_title,choose_title,account_type,student,faculty;
    Button done;
    private static boolean SWI = true;
    String acc_type,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
        student.setOnClickListener(this);
        faculty.setOnClickListener(this);
        done.setOnClickListener(this);
    }
    private void initUi() {
        //init views

        number = findViewById(R.id.number);
        setup_title = findViewById(R.id.set_up_account_title);
        choose_title = findViewById(R.id.choose_text);
        account_type =findViewById(R.id.account_type);
        student   = findViewById(R.id.student);
        faculty = findViewById(R.id.faculty);
        done =findViewById(R.id.done);

        //methods for animation and typeface
        setTypeFace();
        changeStatusBar();
    }

    private void toggleAccountType()
    {
        //true = student
        //false = faculty
        if (SWI)
        {
            student.setBackgroundDrawable(getResources().getDrawable(R.drawable.stud_active));
            faculty.setBackgroundDrawable(getResources().getDrawable(R.drawable.teach_deactive));
            account_type.setText(getResources().getString(R.string.student));
            Helper.acc_type = getResources().getString(R.string.student);
        }
        else {
            student.setBackgroundDrawable(getResources().getDrawable(R.drawable.stud_deactive));
            faculty.setBackgroundDrawable(getResources().getDrawable(R.drawable.teac_active));
            account_type.setText(getResources().getString(R.string.faculty));
            Helper.acc_type = getResources().getString(R.string.faculty);
        }
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
        number.setTypeface(ealing_bold);
        setup_title.setTypeface(ealing_reg);
        choose_title.setTypeface(ealing_reg);
        account_type.setTypeface(ealing_bold);
        done.setTypeface(ealing_reg);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.faculty:
                SWI=false;
                toggleAccountType();
                break;
            case R.id.student:
                SWI=true;
                toggleAccountType();
                break;
            case R.id.done:
                Helper.user.setUser_type(Helper.acc_type);
                startActivity(new Intent(
                        this,
                        activity_details.class
                ));
                break;
            default:
                break;
        }
    }
}
