package com.SDHack.EventsClass;

public class EventResult {

    private int category;

    private String name;

    private String cost = "N/A";

    private String location = "N/A";

    private String startTime = "N/A";

    private String endTime = "N/A";

    private String pictureUri ;

    private String infoPageUri;

    private String distance = "N/A";

    int id;

    public int getCategory() { return category; }

    public void setCategory(int category) { this.category = category; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCost() { return cost; }

    public void setCost(String cost) { this.cost = cost; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getPictureUri() { return pictureUri; }

    public void setPictureUri(String pictureUri) { this.pictureUri = pictureUri; }

    public String getInfoPageUri() { return infoPageUri; }

    public void setInfoPageUri(String infoPageUri) { this.infoPageUri = infoPageUri; }

    public String getDistance() { return distance; }

    public void setDistance(String distance) { this.distance = distance; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
