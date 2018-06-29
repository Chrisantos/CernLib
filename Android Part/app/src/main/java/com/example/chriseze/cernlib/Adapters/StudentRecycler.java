package com.example.chriseze.cernlib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chriseze.cernlib.Models.StudentModel;
import com.example.chriseze.cernlib.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by CHRIS EZE on 6/27/2018.
 */

public class StudentRecycler extends RecyclerView.Adapter<StudentRecycler.StudentHolder> {
    private Context context;
    List<StudentModel> data = Collections.emptyList();
    private ClickListener clickListener;

    public StudentRecycler(Context context, List<StudentModel> data){
        this.context = context;
        this.data = data;
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    @Override
    public StudentRecycler.StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_layout, parent, false);
        StudentHolder holder = new StudentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StudentRecycler.StudentHolder holder, int position) {
        // Get current position of item in RecyclerView to bind data and assign values from list
        StudentModel student = data.get(position);
        holder.tvRegno.setText(student.getRegno());
        holder.tvName.setText(student.getName());
        holder.tvDept.setText(student.getDepartment());
        holder.tvLevel.setText(student.getLevel());
    }

    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }

    public class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvRegno, tvDept, tvLevel;
        public StudentHolder(View itemView) {
            super(itemView);
            tvDept = (TextView)itemView.findViewById(R.id.dept);
            tvLevel = (TextView)itemView.findViewById(R.id.level);
            tvName = (TextView)itemView.findViewById(R.id.name);
            tvRegno = (TextView)itemView.findViewById(R.id.regno);
            itemView.setOnClickListener(this);
            itemView.setTag(itemView);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null){
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }
}
