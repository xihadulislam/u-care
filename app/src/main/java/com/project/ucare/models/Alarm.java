package com.project.ucare.models;


public class Alarm {

    private String name;
    private int hour;
    private int minunte;
    private String date;
    private int itemID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinunte() {
        return minunte;
    }

    public void setMinunte(int minunte) {
        this.minunte = minunte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getItemID() { return itemID; }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
