package com.arc.arcv2.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arc.arcv2.Adapters.SubjectAdapter;
import com.arc.arcv2.MainActivity;
import com.arc.arcv2.Model.SubjectModel;
import com.arc.arcv2.R;
import com.arc.arcv2.Utils.Helper;
import com.arc.arcv2.main_student;

import java.util.ArrayList;

public class choose_subject extends Fragment implements View.OnClickListener{
    private RecyclerView subject_recycler;
    private ArrayList<SubjectModel> subject_list;
    private SubjectAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_subject,container,false);
        initUi(v);
        addSampleData();
        Helper.next.setOnClickListener(this);
        subject_recycler.setOnClickListener(this);
        return v;
    }

    private void addSampleData() {
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        adapter.notifyDataSetChanged();
    }

    private void initUi(View v) {
        subject_recycler = v.findViewById(R.id.sub_recycler);
        subject_list = new ArrayList<>();
        adapter = new SubjectAdapter(v.getContext(),subject_list);
        subject_recycler.setLayoutManager(new LinearLayoutManager(v.getContext().getApplicationContext()));
        subject_recycler.setAdapter(adapter);
        Helper.next= v.findViewById(R.id.next);
        Helper.next.setEnabled(false);
        setBgColor(Helper.next);
    }

   private void setBgColor(Button next) {
        if(next.isEnabled())
        {
            next.setBackgroundColor(getResources().getColor(R.color.status_bar_color));
            next.setTextColor(getResources().getColor(R.color.enabledText));
        }
        else
        {
            next.setBackgroundColor(getResources().getColor(R.color.btn_diabled));
            next.setTextColor(getResources().getColor(R.color.disabledText));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.next:
                if(Helper.acc_type.equals(getResources().getString(R.string.faculty)))
                    openFacultyActivity(view);
                else if (Helper.acc_type.equals(getResources().getString(R.string.student)))
                     openStudentActivity(view);
                break;
        }
    }

    private void openStudentActivity(View view) {
        Intent i = new Intent(view.getContext(), main_student.class);
        startActivity(i);
    }

    private void openFacultyActivity(View view) {
        Intent i = new Intent(view.getContext(), MainActivity.class);
        startActivity(i);
    }


}
