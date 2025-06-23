package com.example.gopool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RideAdapter extends BaseAdapter {

    private Context context;
    private List<Ride> rideList;
    private RideClickListener listener;

    public interface RideClickListener {
        void onRideClick(Ride ride);
    }

    public RideAdapter(Context context, List<Ride> rideList, RideClickListener listener) {
        this.context = context;
        this.rideList = rideList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return rideList.size();
    }

    @Override
    public Object getItem(int position) {
        return rideList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Or ride.getId()
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Ride ride = rideList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ride_item, parent, false);
        }

        TextView rideDetailsText = convertView.findViewById(R.id.rideDetailsText);
        rideDetailsText.setText("From: " + ride.getPickup() + " To: " + ride.getDrop());

        convertView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRideClick(ride);
            }
        });

        return convertView;
    }
}
