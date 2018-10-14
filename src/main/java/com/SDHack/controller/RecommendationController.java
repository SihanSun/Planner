package com.SDHack.controller;

import com.SDHack.EventsClass.EventResult;
import com.SDHack.SpringDataRepository.FavoritesRepository.Favorite;
import com.SDHack.SpringDataRepository.FavoritesRepository.FavoriteRepository;
import com.SDHack.queryAPI.MacysAPI;
import com.SDHack.queryAPI.YelpCrawler;
import com.SDHack.queryAPI.ticketmaster.TicketMasterAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RecommendationController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/recommendation")
    List<EventResult> getIdByValue(@RequestParam String userId) throws Exception {
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(userId);
        List<EventResult> eventResultList = favoriteOptional.map(u->u.getFavorites())
                .orElseThrow(() -> new Exception("no user found"));
        System.out.println("event list size is " + eventResultList.size());
        int countOf0 = 0;
        int countOf1 = 0;
        int countOf2 = 0;
        for(int i = 0 ; i < eventResultList.size() ; i++) {
            int category = eventResultList.get(i).getCategory();
            System.out.println("category is " + category);
            if(category == 0 ) {
                countOf0++;
            } else if(category == 1) {
                countOf1++;
            } else if(category == 2){
                countOf2++;
            }
        }

        while(countOf0+countOf1+countOf2 < 20) {
            countOf0 *= 2;
            countOf1 *= 2;
            countOf2 *= 2;
        }

        while(countOf0+countOf1+countOf2 > 40) {
            countOf0 /= 2;
            countOf1 /= 2;
            countOf2 /= 2;
        }

        System.out.println("countOf0 is " + countOf0);
        System.out.println("countOf1 is " + countOf0);
        System.out.println("countOf2 is " + countOf0);
        List<EventResult> ans = new ArrayList<>();

        Timestamp ts=new Timestamp(System.currentTimeMillis());
        long timeStamp = ts.getTime();
        timeStamp /= 1000;
        if(countOf0 != 0) {
            List<EventResult> eventResults = TicketMasterAPI.search(null);
            for (int i = 0; i < countOf0; i++) {
                if(i >= eventResults.size()) {
                    break;
                }
                ans.add(eventResults.get(i));
            }
        }
        if(countOf1 != 0) {
            List<EventResult> eventResults = YelpCrawler.search((int)timeStamp,0);
            for(int i = 0 ; i < countOf1 ; i++) {
                if(i >= eventResults.size()) {
                    break;
                }
                ans.add(eventResults.get(i));
            }
        }
        if(countOf2 != 0) {
            List<EventResult> eventResults = MacysAPI.search("");
            for(int i = 0 ; i < countOf2 ; i++) {
                if(i >= eventResults.size()) {
                    break;
                }
                ans.add(eventResults.get(i));
            }
        }

        return ans;
    }
}
