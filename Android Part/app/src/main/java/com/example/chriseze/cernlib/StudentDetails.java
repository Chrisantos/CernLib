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
import com.example.chriseze.cernlib.Models.BookModel;
import com.example.chriseze.cernlib.Models.StudentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.chriseze.cernlib.Models.URLs.URL_BOOKS;
import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS;

public class StudentDetails extends AppCompatActivity {
    private Toolbar mToolbar;
    private String mName = "";
    private String _id = "";
    private String completeUrl = "";
    private TextView mTvName, mTvRegno, mTvDept, mTvLevel;
    private Button mBtnDelete, mBtnEdit;
    private UserInfo userInfo;
    private StudentModel studentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvName = (TextView)findViewById(R.id.name);
        mTvRegno = (TextView)findViewById(R.id.regno);
        mTvDept = (TextView)findViewById(R.id.dept);
        mTvLevel = (TextView)findViewById(R.id.level);

        mBtnEdit = (Button) findViewById(R.id.edit);
        mBtnDelete = (Button) findViewById(R.id.delete);

        _id = getIntent().getExtras().getString("_id").toString();
        completeUrl = URL_STUDENTS + "/" + _id;

        studentAction(completeUrl);

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStudent(completeUrl);
            }
        });
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditStudent.class);
                intent.putExtra("_id", _id);
                startActivity(intent);
                finish();
            }
        });

    }

    private void studentAction(String completeUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, completeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject student = jsonObject.getJSONObject("student");
                            String name = student.getString("name");
                            String regno = student.getString("regno");
                            String department = student.getString("department");
                            String level = student.getString("level");

                            setTitle("Cern - " + name);

                            mTvDept.setText(department);
                            mTvLevel.setText(level);
                            mTvName.setText(name);
                            mTvRegno.setText(regno);

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
    private void deleteStudent(String completeUrl) {
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
                                startActivity(new Intent(getApplicationContext(), Students.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(), Students.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
