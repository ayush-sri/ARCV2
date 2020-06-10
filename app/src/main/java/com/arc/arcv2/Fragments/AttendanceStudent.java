package com.arc.arcv2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arc.arcv2.QR_generator;
import com.arc.arcv2.QR_student;
import com.arc.arcv2.R;

public class AttendanceStudent extends Fragment implements View.OnClickListener {
    private Button scan_qr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_student,container,false);
        initUi(view);
        scan_qr.setOnClickListener(this);
        return view;
    }

    private void initUi(View view) {
        scan_qr = view.findViewById(R.id.scan_qr);

    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        switch (id)
        {
            case R.id.scan_qr:
                view.getContext().startActivity(new Intent(view.getContext(), QR_student.class));
                break;
        }
    }
}
