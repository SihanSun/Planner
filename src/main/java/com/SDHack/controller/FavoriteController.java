package com.SDHack.controller;

import com.SDHack.EventsClass.CategoryEvents;
import com.SDHack.EventsClass.EventResult;
import com.SDHack.SpringDataRepository.FavoritesRepository.Favorite;
import com.SDHack.SpringDataRepository.FavoritesRepository.FavoriteRepository;
import com.SDHack.queryAPI.YelpCrawler;
import com.SDHack.queryAPI.ticketmaster.TicketMasterAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
@RestController
public class FavoriteController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    String addFavorite(@RequestParam String userId
            , @RequestParam int id) throws Exception {
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(userId);
        System.out.println("received");
        System.out.println("id is " + id);
        if(favoriteOptional.isPresent()) {
            System.out.println("update");
            Favorite favorite = favoriteOptional.get();
            String idString = Integer.toString(id);
            HashSet<String> set = favoriteOptional.get().getIdSet();
            if(set.contains(idString)) {
                return "already in favorite";
            } else {
                List<EventResult> history = favorite.getHistory();
                set.add(idString);
                favorite.addFavorite(history.get(id));
                favoriteRepository.save(favorite);
            }
        } else {
            System.out.println("insert");
            Favorite favorite = new Favorite(userId);
            List<EventResult> history = favorite.getHistory();
            favorite.addFavorite(history.get(id));
            System.out.println("inserting" + history.get(id).getName());
            favoriteRepository.save(favorite);
        }
        return "success";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    List<EventResult> getIdByValue(@RequestParam String userId,
                                   @RequestParam String type) throws Exception {
//        Favorite favoriteTest = new Favorite("testName");
//        EventResult eventResultTest = new EventResult();
//        eventResultTest.setName("testResult");
//        favoriteTest.addFavorite(eventResultTest);
//        favoriteRepository.save(favoriteTest);
        int intType = Integer.parseInt(type);
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(userId);
        if(favoriteOptional.isPresent()) {
            Favorite favorite = favoriteOptional.get();
            List<EventResult> ans = new ArrayList<>();
            for(EventResult event : favorite.getFavorites()) {
                int category = event.getCategory();
                if(category == intType) {
                    ans.add(event);
                }
            }
            return ans;
        } else {
            throw new Exception("user not found");
        }
    }
}
