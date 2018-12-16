package com.TicketIT.Model;

import java.util.Random;

public class Event {

    private String id;

    private String title;

    private String description;

    private String location;

    private String date;

    private String time;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getImageBackground(){
        Random random = new Random();
        Integer value = random.nextInt(4);
        return "/images/stock" + value + ".jpg";
    }

}
