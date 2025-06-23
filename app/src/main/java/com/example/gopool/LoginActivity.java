package com.example.gopool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        SharedPreferences preferences = getSharedPreferences("RegisteredUsers", Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish(); // Close LoginActivity
        }

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences registeredUsers = getSharedPreferences("RegisteredUsers", Context.MODE_PRIVATE);
        String storedPassword = registeredUsers.getString("password_" + email, null);

        if (storedPassword != null && storedPassword.equals(password)) {
            // Save login state
            SharedPreferences.Editor editor = registeredUsers.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

            // Redirect to HomeActivity
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish(); // Close LoginActivity
        } else {
            Toast.makeText(LoginActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
        }
    }
}
