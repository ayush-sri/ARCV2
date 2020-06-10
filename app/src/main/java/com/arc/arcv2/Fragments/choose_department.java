package com.arc.arcv2.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arc.arcv2.Adapters.DeptAdapter;
import com.arc.arcv2.R;

import java.util.ArrayList;

public class choose_department extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<String>dept_list;
    private DeptAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_department,container,false);
        initUi(view);
        addSampleData();
        return view;
    }

    private void addSampleData() {
        dept_list.add("Computer Science");
        dept_list.add("Electronics");
        dept_list.add("Electrical");
        dept_list.add("Mechanical");
        dept_list.add("Maths");
        dept_list.add("Physics");
        dept_list.add("Chemistry");
        adapter.notifyDataSetChanged();

    }

    private void initUi(View view) {
        recyclerView = view.findViewById(R.id.dept_recycler);
        dept_list = new ArrayList<>();
        adapter = new DeptAdapter(view.getContext(),dept_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext().getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }


}
