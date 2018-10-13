package com.SDHack.queryAPI;

import com.SDHack.EventsClass.EventResult;

import java.util.ArrayList;
import java.util.List;

import com.SDHack.queryAPI.ticketmaster.Utility;
import org.json.*;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class YelpCrawler {

    private String requestUrl = "https://api.yelp.com/v3/businesses/search";
    private String token = "kcjt1y5c411tEPU_djKWyveyShfRoWdgbMMJrqe-C0QZmOVe4VwCbUvoX_oqpbJxz59PL_AfoGisrzaSYABenycK2HEAgWeajzyDsg7Kh_Ttrvqg9OI7GvBcVhTCW3Yx";
    private int totalNumber = 50;
    private String sort_on = "popularity";
    private String latitude = "32.8858947";
    private String longtitude = "-117.2394694";



    /*function that starts to search 50 events*/
    public List<EventResult> search(int startTime, int endTime)
    {
        List<EventResult> result = new ArrayList();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization","Bearer "+token);
            //connection.setRequestProperty("Client ID",clientID);
            connection.setConnectTimeout( 100000 );
            connection.setReadTimeout( 100000 );
            connection.setRequestProperty("sort_on",sort_on);
            connection.setRequestProperty("start_date",Integer.toString(startTime));
            connection.setRequestProperty("end_date",Integer.toString(endTime));


            int responseCode = connection.getResponseCode();

            System.out.println("\nSending 'GET' request to URL: " + requestUrl);
            System.out.println("Response code: " + responseCode);
            if(responseCode != 200) {
                //
                System.out.println("We failed to get the information ");
            }

//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//            while((inputLine = in.readLine())!= null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            JSONObject obj = new JSONObject(response.toString());
//            if(obj.isNull("_embedded")) { //key needs to be exact match
//                return new ArrayList<>();
//            }
//            JSONObject embedded = obj.getJSONObject("_embedded");
//            JSONArray events = embedded.getJSONArray("events");
//            List<EventResult> eventResultList = new ArrayList<>();
//            for(int i = 0 ; i < totalNumber ; i++) {
//                JSONObject event = events.getJSONObject(i);
//                EventResult eventResult = new EventResult();
//                String address = Utility.getAddress(event);
//                eventResult.setLocation(address);
//                String imageUrl = Utility.getImageUrl(event);
//                eventResult.setPictureUri(imageUrl);
//                String infoPageUri = Utility.getInfoUrl(event);
//                eventResult.setInfoPageUri(infoPageUri);
//                //eventResult.setCategory();
//            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
