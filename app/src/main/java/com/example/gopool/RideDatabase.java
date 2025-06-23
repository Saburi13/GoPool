package com.example.gopool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RideDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gopool.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_RIDES = "rides";
    private static final String TABLE_BOOKED = "booked_rides";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PICKUP = "pickup";
    private static final String COLUMN_DROP = "drop_location";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_SEATS = "seats";
    private static final String COLUMN_VEHICLE = "vehicle";

    private static final String COLUMN_BOOKED_ID = "id";
    private static final String COLUMN_BOOKED_RIDE_ID = "ride_id";
    private static final String COLUMN_BOOKED_USERNAME = "username";

    public RideDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRideTable = "CREATE TABLE " + TABLE_RIDES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PICKUP + " TEXT, " +
                COLUMN_DROP + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_SEATS + " TEXT, " +
                COLUMN_VEHICLE + " TEXT)";
        db.execSQL(createRideTable);

        String createBookedTable = "CREATE TABLE " + TABLE_BOOKED + " (" +
                COLUMN_BOOKED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOKED_RIDE_ID + " INTEGER, " +
                COLUMN_BOOKED_USERNAME + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_BOOKED_RIDE_ID + ") REFERENCES " + TABLE_RIDES + "(" + COLUMN_ID + "))";
        db.execSQL(createBookedTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDES);
        onCreate(db);
    }

    public void addRide(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PICKUP, ride.getPickup());
        values.put(COLUMN_DROP, ride.getDrop());
        values.put(COLUMN_DATE, ride.getDate());
        values.put(COLUMN_TIME, ride.getTime());
        values.put(COLUMN_SEATS, String.valueOf(ride.getSeats()));
        values.put(COLUMN_VEHICLE, ride.getVehicle());
        db.insert(TABLE_RIDES, null, values);
        db.close();
    }

    public List<Ride> getAllRides() {
        List<Ride> rideList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RIDES + " WHERE CAST(" + COLUMN_SEATS + " AS INTEGER) > 0", null);

        if (cursor.moveToFirst()) {
            do {
                Ride ride = new Ride(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PICKUP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DROP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEATS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE))
                );
                ride.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                rideList.add(ride);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return rideList;
    }

    public void updateAvailableSeats(int rideId, int newSeatCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SEATS, String.valueOf(newSeatCount));
        db.update(TABLE_RIDES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(rideId)});
        db.close();
    }

    public void deleteRideById(int rideId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RIDES, COLUMN_ID + " = ?", new String[]{String.valueOf(rideId)});
        db.close();
    }

    // ✅ Book a ride by inserting into booked_rides table
    public void addBookedRide(int rideId, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKED_RIDE_ID, rideId);
        values.put(COLUMN_BOOKED_USERNAME, username);
        db.insert(TABLE_BOOKED, null, values);
        db.close();
    }

    // ✅ Get list of booked rides for a user
    public List<Ride> getBookedRides(String username) {
        List<Ride> rideList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.* FROM " + TABLE_RIDES + " r " +
                "INNER JOIN " + TABLE_BOOKED + " b ON r." + COLUMN_ID + " = b." + COLUMN_BOOKED_RIDE_ID + " " +
                "WHERE b." + COLUMN_BOOKED_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                Ride ride = new Ride(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PICKUP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DROP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEATS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE))
                );
                ride.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                rideList.add(ride);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return rideList;
    }

    // ✅ Get ride details by ID
    public Ride getRideById(int rideId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RIDES + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(rideId)});
        if (cursor.moveToFirst()) {
            Ride ride = new Ride(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PICKUP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DROP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEATS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE))
            );
            ride.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            cursor.close();
            db.close();
            return ride;
        }
        cursor.close();
        db.close();
        return null;
    }
}
