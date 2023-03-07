package com.example.pokemonservice.Repository;

import com.example.pokemonservice.Domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String> {
}
