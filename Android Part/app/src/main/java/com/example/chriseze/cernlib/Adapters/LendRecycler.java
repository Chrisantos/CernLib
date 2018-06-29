package com.example.chriseze.cernlib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chriseze.cernlib.Models.LendModel;
import com.example.chriseze.cernlib.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by CHRIS EZE on 6/27/2018.
 */

public class LendRecycler extends RecyclerView.Adapter<LendRecycler.BorrowedHolder> {
    private Context context;
    List<LendModel> data = Collections.emptyList();

    public LendRecycler(Context context, List<LendModel> data){
        this.context = context;
        this.data = data;
    }
    @Override
    public LendRecycler.BorrowedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.borrowed_layout, parent, false);
        BorrowedHolder holder = new BorrowedHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LendRecycler.BorrowedHolder holder, int position) {
        LendModel borrow = data.get(position);
        holder.tvStudent.setText(borrow.getStudent());
        holder.tvRegno.setText(borrow.getRegno());
        holder.tvBook.setText(borrow.getBook());
        holder.tvISBN.setText(borrow.getIsbn());
    }

    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }

    public class BorrowedHolder extends RecyclerView.ViewHolder {
        private TextView tvBook, tvStudent, tvRegno, tvISBN;
        public BorrowedHolder(View itemView) {
            super(itemView);
            tvBook = (TextView)itemView.findViewById(R.id.book);
            tvRegno = (TextView)itemView.findViewById(R.id.regno);
            tvStudent = (TextView)itemView.findViewById(R.id.student);
            tvISBN = (TextView)itemView.findViewById(R.id.isbn);
        }
    }
}
