package com.SDHack.EventsClass;

import java.util.ArrayList;
import java.util.List;

public class CategoryEvents {
    private List<EventResult> ticketMasterList = new ArrayList<>();

    private List<EventResult> yelpList = new ArrayList<>();

    private List<EventResult> macyList = new ArrayList<>();

    public List<EventResult> getTicketMasterList() { return ticketMasterList; }

    public void setTicketMasterList(List<EventResult> ticketMasterList) { this.ticketMasterList = ticketMasterList; }

    public List<EventResult> getYelpList() { return yelpList; }

    public void setYelpList(List<EventResult> yelpList) { this.yelpList = yelpList; }

    public List<EventResult> getMacyList() { return macyList; }

    public void setMacyList(List<EventResult> macyList) { this.macyList = macyList; }
}
