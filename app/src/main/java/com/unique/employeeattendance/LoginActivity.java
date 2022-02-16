package com.unique.employeeattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.snackbar.Snackbar;
import com.unique.employeeattendance.helper.ConnectionDetector;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private String BASE_URL = "https://mobile.jugaadfunda.com/auth/api/signin";
    private EditText etUserName, etPassword;
    private Button btnLogin;
    private ProgressBar login_progressbar;
    private LinearLayout linear_layout;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linear_layout = findViewById(R.id.linear_layout);
        login_progressbar = findViewById(R.id.login_progressbar);
        etUserName = findViewById(R.id.login_etUserName);
        etPassword = findViewById(R.id.login_etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    LinearLayout linear_layout = findViewById(R.id.linear_layout);
                    String username = etUserName.getText().toString();
                    if (username.matches("")) {
                        showSnackBar("Please Enter Email Address !", linear_layout);
                    }
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    LinearLayout linear_layout = findViewById(R.id.linear_layout);
                    String username = etUserName.getText().toString();
                    if (username.matches("")) {
                        showSnackBar("Please Enter Password!", linear_layout);
                    }

                }
            }
        });

    }

    void showSnackBar(String msg, View view) {

        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
        snackbar.show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (username.matches("")) {
            showSnackBar("Please Enter Valid Email Address!", linear_layout);
            return false;
        }
        if (password.matches("")) {
            showSnackBar("Please Enter Password", linear_layout);
            return false;
        }
        return ret;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (checkValidation()) {
                    ConnectionDetector detector = new ConnectionDetector(this);
                    if (detector.isConnectingToInternet()) {

                       // login();
                        String username = etUserName.getText().toString();
                        if (username.matches("admin@gmail.com")) {
                            startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                        } else {

                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        }
                    } else {
                        showSnackBar("Please check your data Connection!", linear_layout);
                        login_progressbar.setVisibility(View.GONE);
                    }
                }
                break;
        }

    }

    private void login() {

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB ca
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        JSONObject postparams = new JSONObject();
        try {
            postparams.put("Content-Type", "application/json");
            postparams.put("name", etUserName.getText().toString());
            postparams.put("password", etPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JsonObjectRequest.Method.POST, BASE_URL, postparams,
                new com.android.volley.Response.Listener<JSONObject>() {
            @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(LoginActivity.this, "onResponse: Success", Toast.LENGTH_SHORT).show();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, "onErrorResponse: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }



        });

        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "Connection Failed : " + connectionResult);
    }
}