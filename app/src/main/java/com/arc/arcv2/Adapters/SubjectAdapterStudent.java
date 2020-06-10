package com.arc.arcv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arc.arcv2.Model.SubjectModel;
import com.arc.arcv2.R;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class SubjectAdapterStudent extends RecyclerView.Adapter<SubjectAdapterStudent.Holder> {
    private Context context;
    private ArrayList<SubjectModel> subjects;

    public SubjectAdapterStudent(Context context, ArrayList<SubjectModel> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final SubjectModel subject = subjects.get(position);
        holder.sub_name.setText(subject.getSub_name());
        holder.sub_code.setText(subject.getSub_code());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView sub_name, sub_code;
        RelativeLayout item;

        public Holder(@NonNull View itemView) {
            super(itemView);
            sub_name = itemView.findViewById(R.id.sub_name);
            sub_code = itemView.findViewById(R.id.sub_code);
            item = itemView.findViewById(R.id.parent);
        }
    }
}