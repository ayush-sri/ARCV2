package com.arc.arcv2.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.arc.arcv2.FaceDetection;
import com.arc.arcv2.QR_generator;
import com.arc.arcv2.R;

public class AttendanceOption extends AlertDialog.Builder implements View.OnClickListener {
    private Context context;
    private View view;
    private Button take_pic,scan_qr,manual;
    public AttendanceOption(Context context) {
        super(context);
        this.context = context;
    }
    public  void setMyView(View view)
    {
        this.view =view;
        this.setView(view);
    }

    public void initUi() {
        take_pic = view.findViewById(R.id.take_pic);
        scan_qr = view.findViewById(R.id.scan_qr);
        manual  = view.findViewById(R.id.manual);
        take_pic.setOnClickListener(this);
        scan_qr.setOnClickListener(this);
        manual.setOnClickListener(this);
    }

    public void createAndShow() {
        this.create();
        this.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.take_pic:
               view.getContext().startActivity(new Intent(view.getContext(), FaceDetection.class));
                break;
            case R.id.scan_qr:
                view.getContext().startActivity(new Intent(view.getContext(), QR_generator.class));
                break;
            case R.id.manual:
                break;
        }
    }
    private void inflateFrag(Fragment frag) {
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.replace(R.id.attendance_layout,frag);
        transaction.commit();
    }
}
