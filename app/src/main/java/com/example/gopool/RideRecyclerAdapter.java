package com.example.gopool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.ViewHolder> {

    private List<Ride> rideList;
    private RideClickListener listener;

    public interface RideClickListener {
        void onRideClick(Ride ride);
    }

    public RideRecyclerAdapter(List<Ride> rideList, RideClickListener listener) {
        this.rideList = rideList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RideRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ride_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideRecyclerAdapter.ViewHolder holder, int position) {
        Ride ride = rideList.get(position);

        holder.tvSource.setText("Source: " + ride.getPickup());
        holder.tvDestination.setText("Destination: " + ride.getDrop());
        holder.tvDate.setText("Date: " + ride.getDate());
        holder.tvTime.setText("Time: " + ride.getTime());
        holder.tvSeats.setText("Seats: " + ride.getAvailableSeats());

        holder.btnBook.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRideClick(ride);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSource, tvDestination, tvDate, tvTime, tvSeats;
        Button btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSeats = itemView.findViewById(R.id.tvSeats); // Add this
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
