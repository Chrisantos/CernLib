package com.example.chriseze.cernlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chriseze.cernlib.Models.BookModel;
import com.example.chriseze.cernlib.Models.StudentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.chriseze.cernlib.Models.URLs.URL_BOOKS;
import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS_SIGNUP;

public class AddBook extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText mEdISBN, mEdTitle, mEdAuthor, mEdPublisher, mEdField, mEdQuantity;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Cern-Add Book");

        mEdAuthor = (EditText)findViewById(R.id.author);
        mEdField = (EditText)findViewById(R.id.field);
        mEdPublisher = (EditText)findViewById(R.id.publisher);
        mEdTitle = (EditText)findViewById(R.id.title);
        mEdQuantity = (EditText)findViewById(R.id.quantity);
        mEdISBN = (EditText)findViewById(R.id.isbn);

        mBtnSave = (Button)findViewById(R.id.save);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mEdTitle.getText().toString();
                String author = mEdAuthor.getText().toString();
                String publisher = mEdPublisher.getText().toString();
                String field = mEdField.getText().toString();
                String isbn = mEdISBN.getText().toString();
                String quantity = mEdQuantity.getText().toString();
                addbookAction(isbn, title, author, publisher, field, quantity);
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addbookAction(final String isbn, final String title, final String author,
                               final String publisher, final String field, final String quantity){
        Log.d("TAG", "Here1");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BOOKS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("TAG", "Here2");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject book = jsonObject.getJSONObject("book");

                            BookModel model = new BookModel(
                                    book.getString("ISBN"),
                                    book.getString("title"),
                                    book.getString("author"),
                                    book.getString("publisher"),
                                    book.getString("field"),
                                    book.getString("quantity"),
                                    book.getString("_id"));

                            Toast.makeText(getApplicationContext(), model.getTitle() + " has been saved.",
                                    Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(getApplicationContext(), Books.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("TAG", "Here3");
                Map<String, String> map = new HashMap<>();
                map.put("ISBN", isbn);
                map.put("title", title);
                map.put("author", author);
                map.put("publisher", publisher);
                map.put("field", field);
                map.put("quantity", quantity);
                return map;
            }
        } ;
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
