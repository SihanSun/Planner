package com.SDHack.queryAPI;

import antlr.StringUtils;
import com.SDHack.EventsClass.EventResult;
import org.json.*;
import sun.jvm.hotspot.utilities.CStringUtilities;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


public class MacysAPI {
    private static final String URL = "https://api.macys.com/v4/catalog/search";
    private static final String DEFAULT_KEYWORD = "clothes"; // no restriction
    private static final String API_KEY = "h4ckathon";
    private static final int NUM_ELE = 24;

    public static List<EventResult> search(String keyword) {
        if(keyword == null || keyword.equals("")) {
            keyword = DEFAULT_KEYWORD;
        }

        try {
            keyword = java.net.URLEncoder.encode(keyword,"UTF-8");
        }catch(Exception e) {
            e.printStackTrace();
        }

        String query = String.format("searchphrase=%s&show=%s&perpage=%s",
                keyword, "product", NUM_ELE);
        List<EventResult> eventResultList = new ArrayList<>();



        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "?"+ query).openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("x-macys-webservice-client-id", API_KEY);
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

            //System.out.println(response);

            JSONObject obj = new JSONObject(response.toString());
            if(obj.isNull("searchresultgroups")) { //key needs to be exact match
                return new ArrayList<>();
            }
            JSONArray products = obj.getJSONArray("searchresultgroups").getJSONObject(0).getJSONObject("products").getJSONArray("product");
            for(int i = 0 ; i < products.length() ; i++) {
                EventResult eventResult = new EventResult();

                eventResult.setCategory(2);

                //get the event object
                JSONObject product = products.getJSONObject(i);

                //get the name of the event
                String name = product.getJSONObject("summary").getString("name");
                eventResult.setName(name);

//                //set the address
//                // TODO::
//                String address = Utility.getAddress(product);
//                eventResult.setLocation(address);

                //set the image URL
                //System.out.println(product.toString());
                if(product.has("image")) {
                    String images = product.getString("image");
                    JSONObject imageObj = new JSONObject(images.substring(1,images.length()-1));
                    if(imageObj.has("imageurl"))
                        eventResult.setPictureUri(imageObj.getString("imageurl"));

                }

                //set the info URL
                String infoPageUri = product.getJSONObject("summary").getString("producturl");
                eventResult.setInfoPageUri(infoPageUri);

                eventResultList.add(eventResult);
            }
            return eventResultList;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return eventResultList;
    }
}