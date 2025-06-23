package com.example.gopool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GoPool.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_RIDES = "rides";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PICKUP = "pickup";
    private static final String COLUMN_DROP = "drop";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_SEATS = "availableSeats";
    private static final String COLUMN_VEHICLE = "vehicle";
    private static final String COLUMN_DRIVER_NAME = "driverName";
    private static final String COLUMN_DRIVER_CONTACT = "driverContact";
    private static final String COLUMN_DRIVER_RATING = "driverRating";
    private static final String COLUMN_PICKUP_LAT = "pickupLat";
    private static final String COLUMN_PICKUP_LNG = "pickupLng";
    private static final String COLUMN_DROP_LAT = "dropLat";
    private static final String COLUMN_DROP_LNG = "dropLng";
    private static final String COLUMN_PRICE = "pricePerSeat";
    private static final String COLUMN_GENDER_PREF = "genderPreference";
    private static final String COLUMN_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RIDES_TABLE = "CREATE TABLE " + TABLE_RIDES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PICKUP + " TEXT, "
                + COLUMN_DROP + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_SEATS + " INTEGER, "
                + COLUMN_VEHICLE + " TEXT, "
                + COLUMN_DRIVER_NAME + " TEXT, "
                + COLUMN_DRIVER_CONTACT + " TEXT, "
                + COLUMN_DRIVER_RATING + " TEXT, "
                + COLUMN_PICKUP_LAT + " REAL, "
                + COLUMN_PICKUP_LNG + " REAL, "
                + COLUMN_DROP_LAT + " REAL, "
                + COLUMN_DROP_LNG + " REAL, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_GENDER_PREF + " TEXT, "
                + COLUMN_STATUS + " TEXT)";
        db.execSQL(CREATE_RIDES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDES);
        onCreate(db);
    }

    public long insertRide(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PICKUP, ride.getPickup());
        values.put(COLUMN_DROP, ride.getDrop());
        values.put(COLUMN_DATE, ride.getDate());
        values.put(COLUMN_TIME, ride.getTime());
        values.put(COLUMN_SEATS, ride.getAvailableSeats());
        values.put(COLUMN_VEHICLE, ride.getVehicle());
        values.put(COLUMN_DRIVER_NAME, ride.getDriverName());
        values.put(COLUMN_DRIVER_CONTACT, ride.getDriverContact());
        values.put(COLUMN_DRIVER_RATING, ride.getDriverRating());
        values.put(COLUMN_PICKUP_LAT, ride.getLatitude());
        values.put(COLUMN_PICKUP_LON, ride.getLongitude());
        values.put(COLUMN_DROP_LAT, ride.getDropLat());
        values.put(COLUMN_DROP_LNG, ride.getDropLng());
        values.put(COLUMN_PRICE, ride.getPricePerSeat());
        values.put(COLUMN_GENDER_PREF, ride.getGenderPreference());
        values.put(COLUMN_STATUS, ride.getStatus());
        return db.insert(TABLE_RIDES, null, values);
    }

    public List<Ride> getAllAvailableRides() {
        List<Ride> rides = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RIDES, null,
                COLUMN_SEATS + " > 0 AND " + COLUMN_STATUS + " = ?",
                new String[]{"active"}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Ride ride = new Ride(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PICKUP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DROP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SEATS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRIVER_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRIVER_CONTACT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRIVER_RATING))
                );

                ride.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                ride.setPickupLat(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PICKUP_LAT)));
                ride.setPickupLng(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PICKUP_LNG)));
                ride.setDropLat(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DROP_LAT)));
                ride.setDropLng(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DROP_LNG)));
                ride.setPricePerSeat(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                ride.setGenderPreference(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER_PREF)));
                ride.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)));

                rides.add(ride);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return rides;
    }

    public void updateRide(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SEATS, ride.getAvailableSeats());
        values.put(COLUMN_STATUS, ride.getStatus());
        db.update(TABLE_RIDES, values, COLUMN_ID + "=?", new String[]{String.valueOf(ride.getId())});
    }

    public void deleteRide(int rideId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RIDES, COLUMN_ID + "=?", new String[]{String.valueOf(rideId)});
    }
}
