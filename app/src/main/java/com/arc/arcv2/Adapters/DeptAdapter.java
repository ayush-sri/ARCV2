package com.arc.arcv2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arc.arcv2.Fragments.choose_subject;
import com.arc.arcv2.R;

import java.util.ArrayList;

public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.Holder> {
    private Context context;
    private ArrayList<String> dept_list;

    public DeptAdapter(Context context, ArrayList<String> dept_list) {
        this.context = context;
        this.dept_list = dept_list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dept_list_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String dept = dept_list.get(position);
        holder.dept_name.setText(dept);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity)view.getContext();
                activity.getFragmentManager()
                        .beginTransaction()
                        .add(R.id.choose_dept,new choose_subject())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.dept_list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView dept_name;
        public Holder(@NonNull View itemView) {
            super(itemView);
            dept_name = itemView.findViewById(R.id.dept_name);
        }
    }
}
