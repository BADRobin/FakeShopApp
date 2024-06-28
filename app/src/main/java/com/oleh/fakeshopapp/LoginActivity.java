package com.oleh.fakeshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;

    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                //                .add("username", "mor_2314")
                //                .add("password", "83r5^_")
                .add("username", username)
                .add("password", password)
                .build();

        Request request  = new Request.Builder()
                .url("https://fakestoreapi.com/auth/login")
                .post(requestBody)
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                runOnUiThread(() -> {
                    handleLoginResponse(response.code());
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    private void handleLoginResponse(int statusCode) {

        if(statusCode == 200) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (statusCode == 400) {
            Toast.makeText(this, "Please input username and password", Toast.LENGTH_SHORT).show();
        }else if (statusCode == 401){
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "WTF? I don't know whats happens", Toast.LENGTH_SHORT).show();
        }

    }
}