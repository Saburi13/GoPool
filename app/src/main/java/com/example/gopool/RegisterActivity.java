package com.example.gopool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already logged in
        SharedPreferences preferences = getSharedPreferences("RegisteredUsers", Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish(); // Close register activity
        }

        setContentView(R.layout.activity_register);

        // Initialize UI elements
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences registeredUsers = getSharedPreferences("RegisteredUsers", Context.MODE_PRIVATE);

        // Check if user already exists
        if (registeredUsers.contains("password_" + email)) {
            Toast.makeText(RegisterActivity.this, "User already registered!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store user credentials
        SharedPreferences.Editor editor = registeredUsers.edit();
        editor.putString("username_" + email, username);
        editor.putString("password_" + email, password);
        editor.apply();

        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

        // Redirect to Login Page
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}
