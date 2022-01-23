package com.github.banido_pixel.restaurant_voting.repository;

import com.github.banido_pixel.restaurant_voting.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}