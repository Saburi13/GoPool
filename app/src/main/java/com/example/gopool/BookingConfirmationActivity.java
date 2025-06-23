package com.example.gopool;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookingConfirmationActivity extends AppCompatActivity {

    private TextView tvPickup, tvDrop, tvDate, tvTime, tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        tvPickup = findViewById(R.id.tvPickup);
        tvDrop = findViewById(R.id.tvDrop);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvMessage = findViewById(R.id.tvMessage);

        // Get data from intent
        String pickup = getIntent().getStringExtra("pickup");
        String drop = getIntent().getStringExtra("drop");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");

        // Set text
        tvPickup.setText("Pickup: " + pickup);
        tvDrop.setText("Drop: " + drop);
        tvDate.setText("Date: " + date);
        tvTime.setText("Time: " + time);
        tvMessage.setText("🎉 Your ride has been successfully booked!");
    }
}
