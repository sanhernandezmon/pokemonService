package com.example.pokemonservice.Service;

import com.example.pokemonservice.Domain.Pokemon;
import com.example.pokemonservice.Repository.PokemonRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class PokemonService {

    PokemonRepository pokemonRepository;

    @Autowired
    PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public ResponseEntity<?> addPokemon(Pokemon pokemon){
        validateLevel(pokemon);

        return ResponseEntity.ok(pokemonRepository.save(pokemon));
    }

    public ResponseEntity<?> increaseLevel(String pokemonId, Integer levelIncrement, String userid){
        if (levelIncrement>0){
            Optional<Pokemon> pokemon = pokemonRepository.findById(pokemonId);
            if (pokemon.isPresent()){
                Pokemon pokemon1 = pokemon.get();
                if (pokemon1.getUser().getUserId().equals(userid)){
                    pokemon1.setPokemonLevel(pokemon1.getPokemonLevel() + levelIncrement);
                    validateLevel(pokemon1);
                    pokemonRepository.increaseLevel(pokemonId,pokemon1.getPokemonLevel());
                    return ResponseEntity.ok(pokemon1);
                }else{
                    return ResponseEntity.badRequest().body("user with id " + userid + "is not authorized to edit pokemon with id " + pokemonId );
                }
            }else{
                return ResponseEntity.badRequest().body("cannot find pokemon with id" + pokemonId);
            }
        }else{
            return ResponseEntity.badRequest().body("cannot decrease pokemon level");
        }
    }

    public ResponseEntity<?> deletePokemon(String pokemonId, String userId){
        Optional<Pokemon> pokemon = pokemonRepository.findById(pokemonId);
        if (pokemon.isPresent()) {
            Pokemon pokemon1 = pokemon.get();
            if (pokemon1.getUser().getUserId().equals(userId)){
                if(pokemonRepository.deletePokemonByPokemonId(pokemonId)==1){
                    return ResponseEntity.ok(pokemon1.getPokemonId());
                }else{
                    return ResponseEntity.badRequest().body("nothing to delete");
                }
            }else{
                return ResponseEntity.badRequest().body("user with id " + userId + "is not authorized to edit pokemon with id " + pokemonId );
            }
        }else{
            return ResponseEntity.badRequest().body("cannot find pokemon with id" + pokemonId);
        }
    }

    public void validateLevel(Pokemon pokemon){
        if (pokemon.getPokemonLevel()>100){
            pokemon.setPokemonLevel(100);
        }
        if (pokemon.getPokemonLevel()<0){
            pokemon.setPokemonLevel(1);
        }
    }

}
