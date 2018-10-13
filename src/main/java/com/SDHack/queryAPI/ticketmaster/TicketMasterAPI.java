package com.SDHack.queryAPI.ticketmaster;

import com.SDHack.EventsClass.EventResult;
import org.json.*;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


public class TicketMasterAPI {
    private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    private static final String DEFAULT_KEYWORD = ""; // no restriction
    private static final String API_KEY = "G6LdqVgSG8oO0qXQASGOfXnG6SdBrjOV";

    List<EventResult> search(double lat, double lon, String keyword) {
        if(keyword == null) {
            keyword = DEFAULT_KEYWORD;
        }

        try {
            keyword = java.net.URLEncoder.encode(keyword,"UTF-8");
        }catch(Exception e) {
            e.printStackTrace();
        }

        String geoHash = Utility.encodeGeohash(lat,lon, 8);

        String query = String.format("apikey=%s&geoPoint=%s&keyword=%s&radius=%s",
                API_KEY,geoHash,keyword,50);

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
                JSONObject event = events.getJSONObject(i);
                EventResult eventResult = new EventResult();
                String address = Utility.getAddress(event);
                eventResult.setLocation(address);
                String imageUrl = Utility.getImageUrl(event);
                eventResult.setPictureUri(imageUrl);
                String infoPageUri = Utility.getInfoUrl(event);
                eventResult.setInfoPageUri(infoPageUri);
                //eventResult.setCategory();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}