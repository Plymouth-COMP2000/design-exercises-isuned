package com.example.hqrestaurant;

public class ReservationsModel {

    private final int id;
    private final String username;
    private final String date;
    private final String time;
    private final int guests;

    public ReservationsModel(int id, String username, String date, String time, int guests) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.time = time;
        this.guests = guests;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getGuests() { return guests; }
}
