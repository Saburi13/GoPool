package com.example.gopool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RideRecyclerAdapter rideAdapter;
    private List<Ride> rideList;
    private Button btnLogin, btnRegister, btnBookRide;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            setContentView(R.layout.activity_main);

            btnLogin = findViewById(R.id.btnLogin);
            btnRegister = findViewById(R.id.btnRegister);

            btnLogin.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this, LoginActivity.class)));

            btnRegister.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        } else {
            setContentView(R.layout.activity_home);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            btnBookRide = findViewById(R.id.btnBookRide);
            btnBookRide.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this, BookRideActivity.class)));

            rideList = new ArrayList<>();
            rideList.add(new Ride("Pune", "Mumbai", "10-Apr-2025", "10:00 AM", "3", "Sedan"));
            rideList.add(new Ride("Delhi", "Agra", "12-Apr-2025", "2:00 PM", "5", "Enova"));
            rideList.add(new Ride("Hyderabad", "Bangalore", "15-Apr-2025", "5:30 PM", "2", "Swift"));

            rideAdapter = new RideRecyclerAdapter(rideList, ride -> {
                Intent intent = new Intent(MainActivity.this, RideDetailsActivity.class);
                intent.putExtra("ride_id", ride.getId()); // Make sure Ride has a getId()
                startActivity(intent);
            });

            recyclerView.setAdapter(rideAdapter);
        }
    }
}
