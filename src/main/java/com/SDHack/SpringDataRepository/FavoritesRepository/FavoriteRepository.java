package com.SDHack.SpringDataRepository.FavoritesRepository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    Optional<Favorite> findById(String userId);
}
