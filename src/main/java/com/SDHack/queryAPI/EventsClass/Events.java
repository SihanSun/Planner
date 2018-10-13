package com.SDHack.queryAPI.EventsClass;

public class Events {

    private int category;

    private String name;

    private int cost;

    private String location;

    private String startTime;

    private String endTime;

    public int getCategory() { return category; }

    public void setCategory(int category) { this.category = category; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getCost() { return cost; }

    public void setCost(int cost) { this.cost = cost; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }
}
