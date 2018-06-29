package com.example.chriseze.cernlib;

import android.content.Intent;
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
import com.example.chriseze.cernlib.Adapters.ClickListener;
import com.example.chriseze.cernlib.Adapters.StudentRecycler;
import com.example.chriseze.cernlib.Models.StudentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS;
import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS_LOGIN;

public class Students extends AppCompatActivity implements ClickListener{
    private RecyclerView mRecyclerview;
    private StudentRecycler studentRecycler;
    private List<StudentModel> studentList;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Cern-Students");
        studentList = new ArrayList<>();

        mRecyclerview = (RecyclerView)findViewById(R.id.recycler_students);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        studentAction(this);
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

    private void studentAction(final ClickListener clickListener){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_STUDENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("students");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject student = jsonArray.getJSONObject(i);
                                String name = student.getString("name");
                                String level = student.getString("level");
                                String department = student.getString("department");
                                String regno = student.getString("regno");

                                String _id = student.getString("_id");

                                studentList.add(new StudentModel(name, regno, department, level, _id));
                            }
                            studentRecycler = new StudentRecycler(Students.this, studentList);
                            mRecyclerview.setAdapter(studentRecycler);
                            studentRecycler.setClickListener(clickListener);

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
        Log.d("TAG: STUDENTS", "CLICKED");
        Intent intent = new Intent(getApplicationContext(), StudentDetails.class);
        intent.putExtra("_id", studentList.get(position).get_id());
        startActivity(intent);
        finish();
    }
}
