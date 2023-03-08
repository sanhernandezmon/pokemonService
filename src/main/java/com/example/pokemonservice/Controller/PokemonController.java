package com.example.pokemonservice.Controller;

import com.example.pokemonservice.Domain.Pokemon;
import com.example.pokemonservice.Domain.User;
import com.example.pokemonservice.Service.PokemonService;
import com.example.pokemonservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/api/v1/pokemon")
public class PokemonController {
    private final PokemonService pokemonService;
    private final UserService userService;

    @Autowired
    PokemonController(PokemonService pokemonService, UserService userService) {
        this.pokemonService = pokemonService;
        this.userService = userService;
    }

    @PostMapping()
    ResponseEntity<?> CreatePokemon(@RequestBody Pokemon pokemon, @RequestParam("user_id") String userId){
        User user = userService.getUserByUserId(userId);
        pokemon.setUser(user);
        return pokemonService.addPokemon(pokemon);
    }
    @PutMapping("/levelinc/{pokemonId}/{levelIncrease}")
    ResponseEntity<?> IncreasePokemonLevel( @PathVariable String pokemonId ,  @PathVariable Integer levelIncrease, @RequestParam("user_id") String userId){
        return pokemonService.increaseLevel(pokemonId, levelIncrease, userId);
    }

    @DeleteMapping("/delete/{pokemonId}")
    ResponseEntity<?> DeletePokemon(@PathVariable String pokemonId ,@RequestParam("user_id") String userId ){
        return pokemonService.deletePokemon(pokemonId, userId);
    }

}
