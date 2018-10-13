package com.SDHack.queryAPI;

import com.SDHack.EventsClass.EventResult;

import java.util.ArrayList;
import java.util.List;

import org.json.*;


import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class YelpCrawler {




    /*function that starts to search 50 events*/
    public static List<EventResult> search(int startTime, int endTime)
    {
        String url = "https://api.yelp.com/v3/businesses/search";
        String token = "kcjt1y5c411tEPU_djKWyveyShfRoWdgbMMJrqe-C0QZmOVe4VwCbUvoX_oqpbJxz59PL_AfoGisrzaSYABenycK2HEAgWeajzyDsg7Kh_Ttrvqg9OI7GvBcVhTCW3Yx";
        List<EventResult> result = new ArrayList();
        try {

            URL requestUrl = new URL(url+"?latitude=32.880058&longitude=-117.234016&limit=50&open_at="+startTime);
            System.out.println(requestUrl);
            HttpURLConnection connection = (HttpURLConnection)(requestUrl.openConnection());
            connection.setRequestProperty("Authorization","Bearer "+token);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);


            //connection.setRequestProperty("Client ID",clientID);
            connection.setConnectTimeout( 100000 );
            connection.setReadTimeout( 100000 );



            int responseCode = connection.getResponseCode();

            System.out.println("\nSending 'GET' request to URL: " + url);
            System.out.println("Response code: " + responseCode);
            if(responseCode != 200) {
                System.out.println("We failed to get the information ");
                return result;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine())!= null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            if(obj.length()==0) { //key needs to be exact match
                System.err.println("No information displayed");
                return new ArrayList<>();
            }
            JSONArray events = obj.getJSONArray("businesses");
            for(int i = 0 ; i < events.length() ; i++) {
                JSONObject event = events.getJSONObject(i);
                EventResult eventResult = new EventResult();
                eventResult.setCategory(1);
                if(event.has("name"))
                    eventResult.setName(event.getString("name"));
                if(event.has("price"))
                    eventResult.setCost(event.getString("price"));
                if(event.has("location")) {
                    JSONObject longLocation = new JSONObject(event.getString("location"));
                    String location = longLocation.getString("display_address").replaceAll("\"", "");
                    eventResult.setLocation(location.substring(1, location.length() - 1));
                }
                if(event.has("image_url"))
                    eventResult.setPictureUri(event.getString("image_url"));
                if(event.has("url"))
                    eventResult.setInfoPageUri(event.getString("url"));
                if(event.has("distance"))
                    eventResult.setDistance(event.getString("distance"));

                System.out.println(eventResult.getName());
                result.add(eventResult);


            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
