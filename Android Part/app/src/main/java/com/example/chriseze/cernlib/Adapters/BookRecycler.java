package com.example.chriseze.cernlib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.chriseze.cernlib.Models.BookModel;
import com.example.chriseze.cernlib.Models.StudentModel;
import com.example.chriseze.cernlib.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by CHRIS EZE on 6/27/2018.
 */

public class BookRecycler extends RecyclerView.Adapter<BookRecycler.BookHolder> {
    private Context context;
    List<BookModel> data = Collections.emptyList();
    private ClickListener clickListener;

    public BookRecycler(Context context, List<BookModel> data){
        this.context = context;
        this.data = data;
    }
    public void setClickListener(ClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }
    @Override
    public BookRecycler.BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_layout, parent, false);
        BookHolder holder = new BookHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookRecycler.BookHolder holder, int position) {
        BookModel book = data.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());

    }

    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvAuthor;

        public BookHolder(View itemView) {
            super(itemView);
            tvAuthor = (TextView)itemView.findViewById(R.id.author);
            tvTitle = (TextView)itemView.findViewById(R.id.title);
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
