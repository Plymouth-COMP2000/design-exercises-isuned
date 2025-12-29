package com.example.hqrestaurant;

public class ReservationsModel {
    public int id;
    public String username;
    public String date;
    public String time;
    public int guests;

    public ReservationsModel(int id, String username, String date, String time, int guests) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.time = time;
        this.guests = guests;
    }
}
