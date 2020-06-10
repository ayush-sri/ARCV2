package com.arc.arcv2.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.arc.arcv2.Model.SubjectModel;
import com.arc.arcv2.R;
import com.arc.arcv2.Utils.Helper;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.Holder>{
    private Context context;
    private ArrayList<SubjectModel> sub_list;

    private int count=0;

    public SubjectAdapter(Context context, ArrayList<SubjectModel> sub_list) {
        this.context = context;
        this.sub_list = sub_list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_item,parent,false);
        Helper.curr_item=0;
        Helper.subjectModelArrayList = new ArrayList<>();
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final SubjectModel subject = sub_list.get(position);
            holder.sub_name.setText(subject.getSub_name());
            holder.sub_code.setText(subject.getSub_code());
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(subject.getSelected()) {
                        Helper.subjectModelArrayList.remove(subject);
                        subject.setSelected(false);
                        count--;
                        Helper.curr_item=count;
                    }
                    else {
                        Helper.subjectModelArrayList.add(subject);
                        subject.setSelected(true);
                        count++;
                        Helper.next.setEnabled(true);
                        setBgColor(Helper.next);
                        Helper.curr_item=count;
                    }
                    if(Helper.curr_item<1){
                    Helper.next.setEnabled(false);
                    setBgColor(Helper.next);
                    }
                    revoke(subject.getSelected(),holder);
                }
            });
    }

    private void revoke(Boolean selected, Holder holder) {
        if(selected)
            setSelected(holder);
        else
            setNotSelected(holder);
    }

    private void setSelected(Holder holder) {
        holder.item.setBackgroundColor(holder.itemView.getResources().getColor(R.color.status_bar_color));
        holder.sub_code.setTextColor(Color.WHITE);
        holder.sub_name.setTextColor(Color.WHITE);
    }

    private void setNotSelected(Holder holder) {

        holder.item.setBackgroundColor(holder.itemView.getResources().getColor(R.color.notSelected));
        holder.sub_code.setTextColor(holder.itemView.getResources().getColor(R.color.textcolor));
        holder.sub_name.setTextColor(holder.itemView.getResources().getColor(R.color.textcolor));

    }

    @Override
    public int getItemCount() {
        return this.sub_list.size();
    }

    private void setBgColor(Button next) {
        if(next.isEnabled())
        {
            next.setBackgroundColor(context.getResources().getColor(R.color.status_bar_color));
            next.setTextColor(context.getResources().getColor(R.color.enabledText));
        }
        else
        {
            next.setBackgroundColor(context.getResources().getColor(R.color.btn_diabled));
            next.setTextColor(context.getResources().getColor(R.color.disabledText));
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView sub_name,sub_code;
        RelativeLayout item;
        public Holder(@NonNull View itemView) {
            super(itemView);
            sub_name = itemView.findViewById(R.id.sub_name);
            sub_code = itemView.findViewById(R.id.sub_code);
            item = itemView.findViewById(R.id.parent);
        }
    }
}
