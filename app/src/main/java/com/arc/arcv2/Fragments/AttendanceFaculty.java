package com.arc.arcv2.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arc.arcv2.R;
import com.arc.arcv2.ui.AttendanceOption;

public class AttendanceFaculty extends Fragment implements View.OnClickListener {
    private RelativeLayout optionMenu;
    private Button gen_qr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_faculty,container,false);
        initUi(view);

        optionMenu.setOnClickListener(this);
        return view;
    }

    private void initUi(View view) {
        optionMenu = view.findViewById(R.id.attendance_status);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.attendance_status:
                AttendanceOption attendanceOption = new AttendanceOption(view.getContext());
                attendanceOption
                        .setMyView(((Activity)view.getContext())
                                .getLayoutInflater()
                                .inflate(R.layout.attendance_options,null));
                attendanceOption.initUi();
                attendanceOption.createAndShow();
                break;
        }

    }
}
