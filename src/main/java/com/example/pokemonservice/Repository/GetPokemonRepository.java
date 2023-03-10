package com.example.pokemonservice.Repository;

import com.example.pokemonservice.Domain.Pokemon;
import com.example.pokemonservice.Domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetPokemonRepository extends PagingAndSortingRepository <Pokemon, String>{
    List<Pokemon> findPokemonsByUser(User user, Pageable pageable);
}
