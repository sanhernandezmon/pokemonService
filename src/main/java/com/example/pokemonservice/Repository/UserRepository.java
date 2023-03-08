package com.example.pokemonservice.Repository;

import com.example.pokemonservice.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmail(String email);

    Integer countUsersByEmail(String email);

}
