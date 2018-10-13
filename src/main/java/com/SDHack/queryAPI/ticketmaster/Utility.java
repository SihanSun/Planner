package com.SDHack.queryAPI.ticketmaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    private static final String BASE_32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    private static int divideRangeByValue(double value, double[] range) {
        double mid = middle(range);
        if (value >= mid) {
            range[0] = mid;
            return 1;
        } else {
            range[1] = mid;
            return 0;
        }
    }

    private static double middle(double[] range) {
        return (range[0] + range[1]) / 2;
    }

    public static String encodeGeohash(double latitude, double longitude, int precision) {
        double[] latRange = new double[]{-90.0, 90.0};
        double[] lonRange = new double[]{-180.0, 180.0};
        boolean isEven = true;
        int bit = 0;
        int base32CharIndex = 0;
        StringBuilder geohash = new StringBuilder();

        while (geohash.length() < precision) {
            if (isEven) {
                base32CharIndex = (base32CharIndex << 1) | divideRangeByValue(longitude, lonRange);
            } else {
                base32CharIndex = (base32CharIndex << 1) | divideRangeByValue(latitude, latRange);
            }

            isEven = !isEven;

            if (bit < 4) {
                bit++;
            } else {
                geohash.append(BASE_32.charAt(base32CharIndex));
                bit = 0;
                base32CharIndex = 0;
            }
        }

        return geohash.toString();
    }

    static String getAddress(JSONObject event) throws JSONException {
        if(!event.isNull("_embedded")) {
            JSONObject embedded = event.getJSONObject("_embedded");
            if(!embedded.isNull("venues")) {
                JSONArray venues = embedded.getJSONArray("venues");
                for(int i=0;i<venues.length();i++) {
                    JSONObject venue = venues.getJSONObject(i);

                    StringBuilder sb = new StringBuilder();
                    if(!venue.isNull("address")) {
                        JSONObject address = venue.getJSONObject("address");
                        if(!address.isNull("line1")) {
                            sb.append(address.getString("line1"));
                        }
                        if (!address.isNull("line2")) {
                            sb.append(" ");
                            sb.append(address.getString("line2"));
                        }
                        if (!address.isNull("line3")) {
                            sb.append(" ");
                            sb.append(address.getString("line3"));
                        }

                        if (!venue.isNull("city")) {
                            JSONObject city = venue.getJSONObject("city");

                            if (!city.isNull("name")) {
                                sb.append(" ");
                                sb.append(city.getString("name"));
                            }
                        }

                        if (!sb.toString().equals("")) {
                            return sb.toString();
                        }
                    }
                }
            }
        }
        return "";
    }

    static String getImageUrl(JSONObject event) throws JSONException{
        if(!event.isNull("images")) {
            JSONArray images = event.getJSONArray("images");
            for(int i=0;i<images.length();i++) {
                JSONObject image = images.getJSONObject(i);

                if(!image.isNull("url")) {
                    return image.getString("url");
                }

            }
        }
        return "";
    }

    static String getInfoUrl(JSONObject event) throws JSONException {
        if(!event.isNull("url")) {
            String url = event.getString("url");
            return url;
        }
        return "";
    }
}
