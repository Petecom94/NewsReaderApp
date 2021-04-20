package com.example.pscproba46;

import java.util.ArrayList;
import java.util.List;

public class MenetrendAdat {

    private List<EventMenetrend> events;

    public MenetrendAdat(ArrayList<EventMenetrend> events) {
        this.events = events;
    }
    //private  Kep image;


    public List<EventMenetrend> getEvents() {
        return events;
    }
}



