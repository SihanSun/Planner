package com.SDHack.controller;

import com.SDHack.EventsClass.EventResult;
import com.SDHack.SpringDataRepository.FavoritesRepository.Favorite;
import com.SDHack.SpringDataRepository.FavoritesRepository.FavoriteRepository;
import com.SDHack.queryAPI.YelpCrawler;
import com.SDHack.queryAPI.ticketmaster.TicketMasterAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class FavoriteController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    String addFavorite(@RequestParam String userId
            , @RequestParam int id) throws Exception {
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(userId);
        System.out.println("received");
        System.out.println("id is " + id);
        if(favoriteOptional.isPresent()) {
            System.out.println("update");
            Favorite favorite = favoriteOptional.get();
            List<EventResult> history = favorite.getHistory();
            favorite.addFavorite(history.get(id));
            favoriteRepository.save(favorite);
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

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    List<EventResult> getIdByValue(@RequestParam String userId) throws Exception {
//        Favorite favoriteTest = new Favorite("testName");
//        EventResult eventResultTest = new EventResult();
//        eventResultTest.setName("testResult");
//        favoriteTest.addFavorite(eventResultTest);
//        favoriteRepository.save(favoriteTest);
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(userId);
        if(favoriteOptional.isPresent()) {
            Favorite favorite = favoriteOptional.get();
            return favorite.getFavorites();
        } else {
            throw new Exception("user not found");
        }
    }
}
