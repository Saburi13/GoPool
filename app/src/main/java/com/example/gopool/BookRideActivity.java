package com.example.gopool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookRideActivity extends AppCompatActivity {

    private EditText etPickup, etDrop;
    private Button btnSearchRide;
    private RecyclerView recyclerViewRides;
    private RideRecyclerAdapter rideAdapter;
    private List<Ride> rideList, filteredRides;
    private RideDatabase rideDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        etPickup = findViewById(R.id.etPickup);
        etDrop = findViewById(R.id.etDrop);
        btnSearchRide = findViewById(R.id.btnSearchRide);
        recyclerViewRides = findViewById(R.id.recyclerViewRides);
        recyclerViewRides.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database
        rideDatabase = new RideDatabase(this);

        // Load all rides from database
        rideList = rideDatabase.getAllRides();
        filteredRides = new ArrayList<>(rideList);

        // Use RecyclerView adapter
        rideAdapter = new RideRecyclerAdapter(filteredRides, new RideRecyclerAdapter.RideClickListener() {
            @Override
            public void onRideClick(Ride ride) {
                if (ride.bookSeat()) {
                    rideDatabase.updateAvailableSeats(ride.getId(), ride.getAvailableSeats());

                    if (ride.isFull()) {
                        rideDatabase.deleteRideById(ride.getId());
                        rideList.remove(ride);
                        filteredRides.remove(ride);
                    }

                    rideAdapter.notifyDataSetChanged();

                    Toast.makeText(BookRideActivity.this, "Ride booked!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BookRideActivity.this, BookingConfirmationActivity.class);
                    intent.putExtra("pickup", ride.getPickup());
                    intent.putExtra("drop", ride.getDrop());
                    intent.putExtra("date", ride.getDate());
                    intent.putExtra("time", ride.getTime());
                    startActivity(intent);

                } else {
                    Toast.makeText(BookRideActivity.this, "Ride is full!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerViewRides.setAdapter(rideAdapter);

        btnSearchRide.setOnClickListener(v -> filterRides());
    }

    private void filterRides() {
        String pickup = etPickup.getText().toString().trim();
        String drop = etDrop.getText().toString().trim();

        if (pickup.isEmpty() || drop.isEmpty()) {
            Toast.makeText(this, "Please enter both pickup and drop locations", Toast.LENGTH_SHORT).show();
            return;
        }

        filteredRides.clear();
        for (Ride ride : rideList) {
            if (ride.getPickup().equalsIgnoreCase(pickup) &&
                    ride.getDrop().equalsIgnoreCase(drop)) {
                filteredRides.add(ride);
            }
        }

        if (filteredRides.isEmpty()) {
            Toast.makeText(this, "No rides available for this route", Toast.LENGTH_SHORT).show();
        }

        rideAdapter.notifyDataSetChanged();
    }
}
