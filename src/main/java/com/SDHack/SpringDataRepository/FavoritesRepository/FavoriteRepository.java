package com.SDHack.SpringDataRepository.FavoritesRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {

    Favorite findByFirstName(String firstName);
    List<Favorite> findByLastName(String lastName);

}
