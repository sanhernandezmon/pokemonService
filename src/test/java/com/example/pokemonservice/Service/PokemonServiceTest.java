package com.example.pokemonservice.Service;

import com.example.pokemonservice.Domain.Pokemon;
import com.example.pokemonservice.Domain.User;
import com.example.pokemonservice.Repository.GetPokemonRepository;
import com.example.pokemonservice.Repository.PokemonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private GetPokemonRepository getPokemonRepository;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonLevel(3);
        when(pokemonRepository.save(any(Pokemon.class))).thenReturn(pokemon);

        ResponseEntity<?> responseEntity = pokemonService.addPokemon(pokemon);

        assertEquals(ResponseEntity.ok(pokemon), responseEntity);
        verify(pokemonRepository, times(1)).save(any(Pokemon.class));
    }

    @Test
    void testIncreaseLevel() {
        // Mocking
        String pokemonId = "1";
        Integer levelIncrement = 5;
        String userId = "user123";
        Integer InitialPokemonLevel = 10;
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.setPokemonId(pokemonId);
        mockPokemon.setPokemonLevel(InitialPokemonLevel); // Set a sample level
        // Mock the User object
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockPokemon.setUser(mockUser);

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(mockPokemon));

        // Test
        ResponseEntity<?> responseEntity = pokemonService.increaseLevel(pokemonId, levelIncrement, userId);

        // Assertions
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(InitialPokemonLevel + levelIncrement, ((Pokemon) responseEntity.getBody()).getPokemonLevel());
        verify(pokemonRepository, times(1)).findById(pokemonId);
        verify(pokemonRepository, times(1)).increaseLevel(pokemonId, InitialPokemonLevel + levelIncrement);
    }

    @Test
    void testDeletePokemon() {
        // Mocking
        String pokemonId = "1";
        String userId = "user123";

        User mockUser = new User();
        mockUser.setUserId(userId);


        Pokemon mockPokemon = new Pokemon(/* initialize your mock Pokemon object */);
        mockPokemon.setUser(mockUser);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(mockPokemon));
        when(pokemonRepository.deletePokemonByPokemonId(pokemonId)).thenReturn(1);

        // Test
        ResponseEntity<?> responseEntity = pokemonService.deletePokemon(pokemonId, userId);

        // Assertions
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(mockPokemon.getPokemonId(), responseEntity.getBody());
        verify(pokemonRepository, times(1)).findById(pokemonId);
        verify(pokemonRepository, times(1)).deletePokemonByPokemonId(pokemonId);
    }

    // Add more test methods as needed

}
