package com.example.chriseze.cernlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chriseze.cernlib.Adapters.BookRecycler;
import com.example.chriseze.cernlib.Models.BookModel;
import com.example.chriseze.cernlib.Models.StudentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.chriseze.cernlib.Models.URLs.URL_BOOKS;
import static com.example.chriseze.cernlib.Models.URLs.URL_LENDS;

public class BookDetails extends AppCompatActivity {
    private Toolbar mToolbar;
    private String mTitle = "";
    private String ISBNi = "";
    private String _id = "";
    private String completeUrl = "";
    private TextView mTvTitle, mTvISBN, mTvAuthor, mTvPublisher, mTvField, mTvQuantity;
    private Button mBtnDelete, mBtnBorrow;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvTitle = (TextView)findViewById(R.id.title);
        mTvISBN = (TextView)findViewById(R.id.isbn);
        mTvAuthor = (TextView)findViewById(R.id.author);
        mTvPublisher = (TextView)findViewById(R.id.publisher);
        mTvField = (TextView)findViewById(R.id.field);
        mTvQuantity = (TextView)findViewById(R.id.quantity);

        mBtnBorrow = (Button) findViewById(R.id.borrow);
        mBtnDelete = (Button) findViewById(R.id.delete);

        _id = getIntent().getExtras().getString("_id").toString();
        completeUrl = URL_BOOKS + "/" + _id;

        bookAction(completeUrl);

        userInfo = new UserInfo(this);
        final String name = userInfo.getUser().getName();

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook(completeUrl);
            }
        });
        mBtnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrowBook(ISBNi, name, mTitle);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(), Books.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void bookAction(final String completeUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, completeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject book = jsonObject.getJSONObject("book");
                                String title = book.getString("title");
                                String ISBN = book.getString(("ISBN"));
                                String author = book.getString("author");
                                String publisher = book.getString("publisher");
                                String field = book.getString(("field"));
                                String quantity = book.getString("quantity");

                            setTitle("Cern - " + title);
                            ISBNi = ISBN;
                            mTitle = title;

                            mTvAuthor.setText(author);
                            mTvTitle.setText(title);
                            mTvISBN.setText(ISBN);
                            mTvPublisher.setText(publisher);
                            mTvField.setText(field);
                            mTvQuantity.setText(quantity);

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

    private void deleteBook(String completeUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, completeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean bool = jsonObject.getBoolean("bool");
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            if (bool){
                                startActivity(new Intent(getApplicationContext(), Books.class));
                                finish();
                            }


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

    private void borrowBook(final String isbn, final String student_name, final String book_title){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean bool = jsonObject.getBoolean("bool");
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            if (bool){
                                startActivity(new Intent(getApplicationContext(), Books.class));
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("ISBN", isbn);
                        map.put("book", book_title);
                        map.put("student", student_name);
                        return map;
                    }
                };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
