package com.example.pokemonservice.Controller;

import com.example.pokemonservice.Domain.Pokemon;
import com.example.pokemonservice.Domain.User;
import com.example.pokemonservice.Security.JwtTokenUtil;
import com.example.pokemonservice.Service.PokemonService;
import com.example.pokemonservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/pokemon")
public class PokemonController {
    private final PokemonService pokemonService;
    private final UserService userService;

    private final JwtTokenUtil tokenUtil;

    @Autowired
    PokemonController(PokemonService pokemonService, UserService userService, JwtTokenUtil tokenUtil) {
        this.pokemonService = pokemonService;
        this.userService = userService;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping()
    ResponseEntity<?> CreatePokemon(@RequestBody Pokemon pokemon, @RequestHeader("Authorization") String token){
        String useremail = tokenUtil.getUsernameFromToken(token.substring(7));
        User user = userService.getUserByEmail(useremail);
        pokemon.setUser(user);
        return pokemonService.addPokemon(pokemon);
    }
    @PutMapping("/levelinc/{pokemonId}/{levelIncrease}")
    ResponseEntity<?> IncreasePokemonLevel( @PathVariable String pokemonId ,  @PathVariable Integer levelIncrease, @RequestHeader("Authorization") String token){
        String useremail = tokenUtil.getUsernameFromToken(token.substring(7));
        User user = userService.getUserByEmail(useremail);
        return pokemonService.increaseLevel(pokemonId, levelIncrease, user.getUserId());
    }

    @DeleteMapping("/delete/{pokemonId}")
    ResponseEntity<?> DeletePokemon(@PathVariable String pokemonId ,@RequestHeader("Authorization") String token ){
        String useremail = tokenUtil.getUsernameFromToken(token.substring(7));
        User user = userService.getUserByEmail(useremail);
        return pokemonService.deletePokemon(pokemonId, user.getUserId());
    }

}
