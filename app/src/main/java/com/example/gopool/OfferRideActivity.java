package com.example.gopool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OfferRideActivity extends AppCompatActivity {

    private EditText editTextPickup, editTextDrop, editTextDate, editTextTime, editTextSeats, editTextVehicle;
    private Button buttonOfferRide;
    private RideDatabase rideDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        // Initialize database
        rideDatabase = new RideDatabase(this);

        // Initialize UI elements
        editTextPickup = findViewById(R.id.editTextPickup);
        editTextDrop = findViewById(R.id.editTextDrop);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextSeats = findViewById(R.id.editTextSeats);
        editTextVehicle = findViewById(R.id.editTextVehicle);
        buttonOfferRide = findViewById(R.id.buttonOfferRide);

        // Button Click Listener
        buttonOfferRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRideDetails();
            }
        });
    }

    private void saveRideDetails() {
        String pickup = editTextPickup.getText().toString().trim();
        String drop = editTextDrop.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String seats = editTextSeats.getText().toString().trim();
        String vehicle = editTextVehicle.getText().toString().trim();

        if (pickup.isEmpty() || drop.isEmpty() || date.isEmpty() || time.isEmpty() || seats.isEmpty() || vehicle.isEmpty()) {
            Toast.makeText(this, "Please fill all details!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save ride to SQLite
        Ride newRide = new Ride(pickup, drop, date, time, seats, vehicle);
        rideDatabase.addRide(newRide);

        Toast.makeText(this, "Ride Offered Successfully!", Toast.LENGTH_SHORT).show();

        // Redirect to Home Activity
        Intent intent = new Intent(OfferRideActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
