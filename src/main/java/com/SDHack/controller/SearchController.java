package com.SDHack.controller;

import com.SDHack.EventsClass.EventResult;
import com.SDHack.queryAPI.YelpCrawler;
import com.SDHack.queryAPI.ticketmaster.TicketMasterAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class SearchController {

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/search")
    List<EventResult> getIdByValue(@RequestParam String type
            , @RequestParam String startTime) {
        System.out.println("type is " + type);
        System.out.println("startTime is " + startTime);
        if(type.equals("0")) {
            return TicketMasterAPI.search(32.8858947,-117.2394694, startTime,null);
        } else if(type.equals("1")) {
            return YelpCrawler.search(Integer.parseInt(startTime),0);
        } else {
            return TicketMasterAPI.search(32.8858947,-117.2394694, startTime, null);
        }
    }

}