package com.example.pokemonservice.Repository;

import com.example.pokemonservice.Domain.Pokemon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String> {
    @Modifying
    @Transactional
    @Query("UPDATE Pokemon p SET p.pokemonLevel = ?2 WHERE p.pokemonId = ?1")
    void increaseLevel(String pokemonId, Integer level);

    @Modifying
    @Transactional
    Integer deletePokemonByPokemonId(String pokemonId);
}
