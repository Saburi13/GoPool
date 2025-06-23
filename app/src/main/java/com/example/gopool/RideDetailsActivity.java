package com.example.gopool;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RideDetailsActivity extends AppCompatActivity {

    private TextView rideDetailsText;
    private RideDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        rideDetailsText = findViewById(R.id.rideDetailsText);
        db = new RideDatabase(this);

        int rideId = getIntent().getIntExtra("ride_id", -1);
        if (rideId != -1) {
            Ride ride = db.getRideById(rideId);
            if (ride != null) {
                String details = "Pickup: " + ride.getPickup() +
                        "\nDrop: " + ride.getDrop() +
                        "\nDate: " + ride.getDate() +
                        "\nTime: " + ride.getTime() +
                        "\nSeats: " + ride.getSeats() +
                        "\nVehicle: " + ride.getVehicle();
                rideDetailsText.setText(details);
            } else {
                rideDetailsText.setText("Ride not found");
            }
        }
    }
}
