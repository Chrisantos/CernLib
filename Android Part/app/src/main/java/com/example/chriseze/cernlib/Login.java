package com.example.chriseze.cernlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chriseze.cernlib.Models.StudentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.chriseze.cernlib.Models.URLs.URL_STUDENTS_LOGIN;

public class Login extends AppCompatActivity {
    private SessionManager sessionManager;
    private EditText mEdRegno, mEdPass;
    private Button mBtnLogin;
    private TextView mTvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);

        mEdPass = (EditText)findViewById(R.id.password);
        mEdRegno = (EditText)findViewById(R.id.regno);
        mTvSignup = (TextView)findViewById(R.id.signup);
        mBtnLogin = (Button)findViewById(R.id.login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regno = mEdRegno.getText().toString();
                String pass = mEdPass.getText().toString();
                loginAction(regno, pass);
            }
        });

        mTvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
                finish();
            }
        });

    }

    private void loginAction(final String regno, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STUDENTS_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject student = jsonObject.getJSONObject("student");

                            StudentModel model = new StudentModel(
                                    student.getString("name"),
                                    student.getString("regno"),
                                    student.getString("department"),
                                    student.getString("level"),
                                    student.getString("_id"));
                            UserInfo userInfo = new UserInfo(getApplicationContext());
                            userInfo.setUser(model);
                            Log.d("TAG", "This is for login action");

                            startActivity(new Intent(getApplicationContext(), SplashScreen.class));
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
                        map.put("regno", regno);
                        map.put("password", password);
                        return map;
                    }
                };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
