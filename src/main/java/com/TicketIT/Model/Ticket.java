package com.TicketIT.Model;

public class Ticket {

    private String id;

    private String eventId;

    private String name;

    private Double price;

    private Integer numberAvailable;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEventId() { return eventId; }

    public void setEventId(String event) { this.eventId = event; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Integer getNumberAvailable() { return numberAvailable; }

    public void setNumberAvailable(Integer numberAvailable) { this.numberAvailable = numberAvailable; }

}
