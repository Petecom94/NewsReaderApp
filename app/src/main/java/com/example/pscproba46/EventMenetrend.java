package com.example.pscproba46;

public class EventMenetrend {

    public String title;
    public String start_date;
    public String end_date;
    public Kep image;
    public EventMenetrend(String slug) {
        this.title = slug;
    }




    public String getTitle() {
        return title;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public Kep getImage() {
        return image;
    }
}
