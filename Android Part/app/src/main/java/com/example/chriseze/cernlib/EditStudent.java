package com.example.chriseze.cernlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS;

public class EditStudent extends AppCompatActivity {
    private Button mBtnSave;
    private Toolbar mToolbar;
    private String mName = "";
    private String _id = "";
    private String completeUrl = "";
    private EditText mEdName, mEdRegno, mEdDept, mEdLevel;
    private TextView mTvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEdName = (EditText)findViewById(R.id.name);
        mEdRegno = (EditText)findViewById(R.id.regno);
        mEdDept = (EditText)findViewById(R.id.department);
        mEdLevel = (EditText) findViewById(R.id.level);

        mTvName = (TextView)findViewById(R.id.name_text);
        mBtnSave = (Button)findViewById(R.id.save);

        _id = getIntent().getExtras().getString("_id").toString();
        completeUrl = URL_STUDENTS + "/" + _id;

        studentAction(completeUrl);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEdName.getText().toString();
                String regno = mEdRegno.getText().toString();
                String dept = mEdDept.getText().toString();
                String level = mEdLevel.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(regno) &&!TextUtils.isEmpty(dept)
                && !TextUtils.isEmpty(level)){
                    editStudent(name, regno, dept, level, completeUrl);
                }

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

                            mEdDept.setText(department);
                            mEdLevel.setText(level);
                            mEdName.setText(name);
                            mEdRegno.setText(regno);

                            mTvName.setText(name);

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
    private void editStudent(final String name, final String regno, final String dept, final String level, String completeUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, completeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), StudentDetails.class);
                            intent.putExtra("_id", _id);
                            startActivity(intent);
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("regno", regno);
                map.put("department", dept);
                map.put("level", level);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
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
}
