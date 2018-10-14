package com.SDHack.SpringDataRepository.FavoritesRepository;

import com.SDHack.EventsClass.EventResult;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Favorite {

    @Id
    public String userName;

    private List<EventResult>  favorites = new ArrayList<>();

    private List<EventResult> history;

    private HashSet<String> idSet = new HashSet<>();

    public Favorite(String userName) {
        this.userName = userName;
    }

    public void addFavorite(EventResult eventResult) {
        favorites.add(eventResult);
    }

    public void setHistory(List<EventResult> history) {
        this.history = history;
    }

    public List<EventResult> getHistory() {
        return this.history;
    }

    public List<EventResult> getFavorites() {
        return favorites;
    }

    public HashSet<String> getIdSet() { return idSet; }

    public void setIdSet(HashSet<String> idSet) { this.idSet = idSet; }

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
