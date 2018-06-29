package com.example.chriseze.cernlib;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chriseze.cernlib.Adapters.BookRecycler;
import com.example.chriseze.cernlib.Adapters.ClickListener;
import com.example.chriseze.cernlib.Adapters.StudentRecycler;
import com.example.chriseze.cernlib.Models.BookModel;
import com.example.chriseze.cernlib.Models.StudentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.chriseze.cernlib.Models.URLs.URL_BOOKS;
import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS_LOGIN;

public class Books extends AppCompatActivity implements ClickListener {
    private RecyclerView mRecyclerview;
    private BookRecycler bookRecycler;
    private List<BookModel> bookList;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Cern-Books");
        bookList = new ArrayList<>();

        mRecyclerview = (RecyclerView)findViewById(R.id.recycler_books);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookAction(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void bookAction(final ClickListener clickListener){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BOOKS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("books");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject book = jsonArray.getJSONObject(i);
                                String title = book.getString("title");
                                String ISBN = book.getString(("isbn"));
                                String author = book.getString("author");
                                String publisher = book.getString("publisher");
                                String field = book.getString(("field"));
                                String quantity = book.getString("quantity");

                                String _id = book.getString("_id");

                                bookList.add(new BookModel(ISBN, title, author, publisher, field, quantity, _id));
                            }
                            bookRecycler = new BookRecycler(getApplicationContext(), bookList);
                            mRecyclerview.setAdapter(bookRecycler);
                            bookRecycler.setClickListener(clickListener);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("TAG: BOOKS", "CLICKED");
        Intent intent = new Intent(getApplicationContext(), BookDetails.class);
        intent.putExtra("_id", bookList.get(position).get_id());
        startActivity(intent);
        finish();
    }
}
