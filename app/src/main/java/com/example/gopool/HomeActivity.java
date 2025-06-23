package com.example.gopool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnBookRide, btnOfferRide, btnViewHistory, btnProfile, btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("RegisteredUsers", Context.MODE_PRIVATE);

        // Check if the user is logged in
        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        // Initialize buttons
        btnBookRide = findViewById(R.id.btnBookRide);
        btnOfferRide = findViewById(R.id.btnOfferRide);
        btnViewHistory = findViewById(R.id.btnViewHistory);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Set Click Listeners
        btnBookRide.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, BookRideActivity.class)));
        btnOfferRide.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, OfferRideActivity.class)));
        btnViewHistory.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, RideHistoryActivity.class)));
        btnProfile.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));

        // Logout functionality
        btnLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.remove("email");
            editor.apply();

            Toast.makeText(HomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Redirect to login page
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
