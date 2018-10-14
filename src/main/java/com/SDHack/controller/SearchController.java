package com.SDHack.controller;

import com.SDHack.EventsClass.EventResult;
import com.SDHack.PortalNLP.PortalNLP;
import com.SDHack.SpringDataRepository.FavoritesRepository.Favorite;
import com.SDHack.SpringDataRepository.FavoritesRepository.FavoriteRepository;
import com.SDHack.queryAPI.MacysAPI;
import com.SDHack.queryAPI.YelpCrawler;
import com.SDHack.queryAPI.ticketmaster.TicketMasterAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class SearchController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/search")
    List<EventResult> getIdByValue(@RequestParam String type
            , @RequestParam String userId ) throws Exception {
        System.out.println("type is " + type);

        String startTime = "1539485667";
        //guess type
        type = type.replaceAll("%20", " ");
        type = PortalNLP.classify(type);

        //find the result
        System.out.println("type is " + type);
        List<EventResult> ans;
        if(type.equals("0")) {
            ans = TicketMasterAPI.search(null);
        } else if(type.equals("1")) {
            ans = YelpCrawler.search(Integer.parseInt(startTime),0);
        } else {
            ans = MacysAPI.search("");
        }
        for(int i = 0 ; i < ans.size() ; i++) {
            ans.get(i).setId(i);
            ans.get(i).setCategory(Integer.parseInt(type));
        }

        //update the history
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(userId);
        if(favoriteOptional.isPresent()) {
            Favorite favorite = favoriteOptional.get();
            favorite.getIdSet().clear();
            favorite.setHistory(ans);
            favoriteRepository.save(favorite);
        } else {
            Favorite favorite = new Favorite(userId);
            favorite.setHistory(ans);
            favoriteRepository.save(favorite);
        }
        return ans;
    }

}