package com.SDHack.queryAPI.ticketmaster;

import com.SDHack.EventsClass.EventResult;
import org.json.*;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TicketMasterAPI {
    private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    private static final String DEFAULT_KEYWORD = ""; // no restriction
    private static final String API_KEY = "G6LdqVgSG8oO0qXQASGOfXnG6SdBrjOV";
    private static final String startTime = "2018-11-23T01:45:00Z";
    private static final String endTime = "2018-11-24T01:45:00Z";
    private static final double lat = 32.8858947;
    private static final double lon = -117.2394694;
    private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    public static List<EventResult> search(String keyword) {
        if(keyword == null) {
            keyword = DEFAULT_KEYWORD;
        }

        Date date=new java.util.Date(System.currentTimeMillis());
        String pattern1 = "yyyy-MM-dd";
        String pattern2 = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

        String formatDateBegin = simpleDateFormat1.format(date);
        formatDateBegin += "T";
        formatDateBegin += simpleDateFormat2.format(date);
        formatDateBegin += "Z";


        Date endDate = new Date(date.getTime() + (7 * DAY_IN_MS));
        String formatDateEnd = simpleDateFormat1.format(endDate);
        formatDateEnd += "T";
        formatDateEnd += simpleDateFormat2.format(endDate);
        formatDateEnd += "Z";

        try {
            keyword = java.net.URLEncoder.encode(keyword,"UTF-8");
        }catch(Exception e) {
            e.printStackTrace();
        }

        String geoHash = Utility.encodeGeohash(lat,lon, 8);

        String query = String.format("apikey=%s&geoPoint=%s&keyword=%s&radius=%s&startDateTime=%s&endDateTime=%s",
                API_KEY,geoHash,keyword,50, formatDateBegin, formatDateEnd);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "?"+query).openConnection();
            int responseCode = connection.getResponseCode();

            System.out.println("\nSending 'GET' request to URL: " + URL + "?" + query);
            System.out.println("Response code: " + responseCode);
            if(responseCode != 200) {
                //
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine())!= null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            if(obj.isNull("_embedded")) { //key needs to be exact match
                return new ArrayList<>();
            }
            JSONObject embedded = obj.getJSONObject("_embedded");
            JSONArray events = embedded.getJSONArray("events");
            List<EventResult> eventResultList = new ArrayList<>();
            for(int i = 0 ; i < events.length() ; i++) {
                EventResult eventResult = new EventResult();


                //get the event object
                JSONObject event = events.getJSONObject(i);

                //get the name of the event
                String name = event.getString("name");
                eventResult.setName(name);

                //set the address
                String address = Utility.getAddress(event);
                eventResult.setLocation(address);

                //set the image URL
                String imageUrl = Utility.getImageUrl(event);
                eventResult.setPictureUri(imageUrl);

                //set the info URL
                String infoPageUri = Utility.getInfoUrl(event);
                eventResult.setInfoPageUri(infoPageUri);

                //set the startTime
                try {
                    String dateTime = event.getJSONObject("dates").getJSONObject("start").getString("dateTime");
                    eventResult.setStartTime(dateTime);
                } catch (Exception e) {
                    continue;
                }

                //eventResult.setCategory();
                eventResultList.add(eventResult);
            }
            return eventResultList;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}