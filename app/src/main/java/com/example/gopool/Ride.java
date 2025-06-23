// Ride.java (Updated)
package com.example.gopool;

public class Ride {
    private int id;
    private String pickup, drop, date, time, vehicle;
    private int availableSeats;
    private String driverName, driverContact, driverRating;
    private double pickupLatitude, pickupLongitude, dropLatitude, dropLongitude;
    private double pricePerSeat;
    private String genderPreference;
    private String status;

    public Ride(String pickup, String drop, String date, String time, String seats, String vehicle) {
        this.pickup = pickup;
        this.drop = drop;
        this.date = date;
        this.time = time;
        this.vehicle = vehicle;

        try {
            this.availableSeats = Integer.parseInt(seats);
        } catch (NumberFormatException e) {
            this.availableSeats = 1;
        }

        this.driverName = "Not Assigned";
        this.driverContact = "N/A";
        this.driverRating = "N/A";
        this.status = "Active";
        this.genderPreference = "Any";
        this.pricePerSeat = 0.0;
    }

    public Ride(String pickup, String drop, String date, String time, int availableSeats, String vehicle,
                String driverName, String driverContact, String driverRating,
                double pickupLat, double pickupLong, double dropLat, double dropLong,
                double pricePerSeat, String genderPreference, String status) {
        this.pickup = pickup;
        this.drop = drop;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
        this.vehicle = vehicle;
        this.driverName = driverName;
        this.driverContact = driverContact;
        this.driverRating = driverRating;
        this.pickupLatitude = pickupLat;
        this.pickupLongitude = pickupLong;
        this.dropLatitude = dropLat;
        this.dropLongitude = dropLong;
        this.pricePerSeat = pricePerSeat;
        this.genderPreference = genderPreference;
        this.status = status;
    }

    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return availableSeats <= 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPickup() { return pickup; }
    public String getDrop() { return drop; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getAvailableSeats() { return availableSeats; }
    public String getVehicle() { return vehicle; }
    public String getDriverName() { return driverName; }
    public String getDriverContact() { return driverContact; }
    public String getDriverRating() { return driverRating; }

    public double getPickupLatitude() { return pickupLatitude; }
    public double getPickupLongitude() { return pickupLongitude; }
    public double getDropLatitude() { return dropLatitude; }
    public double getDropLongitude() { return dropLongitude; }

    public double getPricePerSeat() { return pricePerSeat; }
    public String getGenderPreference() { return genderPreference; }
    public String getStatus() { return status; }

    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public void setStatus(String status) { this.status = status; }
    public void setPricePerSeat(double pricePerSeat) { this.pricePerSeat = pricePerSeat; }
    public void setGenderPreference(String genderPreference) { this.genderPreference = genderPreference; }

    @Override
    public String toString() {
        return pickup + " to " + drop + " | " + date + " " + time +
                " | Seats: " + availableSeats + " | Rs. " + pricePerSeat + "/seat | " + vehicle +
                (driverName.equals("Not Assigned") ? "" : " | Driver: " + driverName + " (" + driverRating + ")");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ride ride = (Ride) obj;
        return id == ride.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
