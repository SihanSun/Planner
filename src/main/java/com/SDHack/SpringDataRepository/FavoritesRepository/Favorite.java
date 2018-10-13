package com.SDHack.SpringDataRepository.FavoritesRepository;

import com.SDHack.EventsClass.EventResult;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;


public class Favorite {

    @Id
    public String userName;

    private List<EventResult>  favorites = new ArrayList<>();

    public Favorite(String userName) {
        this.userName = userName;
    }

    public void addFavorite(EventResult eventResult) {
        favorites.add(eventResult);
    }

    public List<EventResult> getFavorites() {
        return favorites;
    }
    @Override
    public String toString() {
        String ans = userName + "\n";
        for(int i = 0 ; i < favorites.size() ; i++) {
            ans += favorites.get(i).getName();
            ans += "\n";
        }
        return ans;
    }

}
