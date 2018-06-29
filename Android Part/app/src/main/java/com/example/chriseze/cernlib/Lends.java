package com.example.chriseze.cernlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chriseze.cernlib.Adapters.BookRecycler;
import com.example.chriseze.cernlib.Adapters.LendRecycler;
import com.example.chriseze.cernlib.Models.BookModel;
import com.example.chriseze.cernlib.Models.LendModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.chriseze.cernlib.Models.URLs.URL_LENDS;
import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS;

public class Lends extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private LendRecycler lendRecycler;
    private List<LendModel> lendList;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Cern-Lends");
        lendList = new ArrayList<>();

        mRecyclerview = (RecyclerView)findViewById(R.id.recycler_borrow);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        lendAction();
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

    private void lendAction(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("TAG", "HERE1");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("lends");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject lend = jsonArray.getJSONObject(i);
                                String book = lend.getString("book");
                                String student = lend.getString("student_name");
                                String regno   = lend.getString("student_regno");
                                String isbn   = lend.getString("ISBN");
                                lendList.add(new LendModel(book, student, regno, isbn));

                            }
                            lendRecycler = new LendRecycler(getApplicationContext(), lendList);
                            mRecyclerview.setAdapter(lendRecycler);

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
}
