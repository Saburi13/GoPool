package com.example.gopool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MyBookedRidesActivity extends AppCompatActivity {

    private ListView listView;
    private RideAdapter adapter;
    private RideDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booked_rides);

        listView = findViewById(R.id.bookedRidesListView);
        db = new RideDatabase(this);

        loadBookedRides();
    }

    private void loadBookedRides() {
        List<Ride> bookedRides = db.getAllRides();

        if (bookedRides == null || bookedRides.isEmpty()) {
            Toast.makeText(this, "No booked rides found.", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter = new RideAdapter(this, bookedRides, ride -> {
            Intent intent = new Intent(MyBookedRidesActivity.this, RideDetailsActivity.class);
            intent.putExtra("ride_id", ride.getId());
            startActivity(intent);
        });

        // Set the adapter to the ListView
        listView.setAdapter(adapter);
    }
}
