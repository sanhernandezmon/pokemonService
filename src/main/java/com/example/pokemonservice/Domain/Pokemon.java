package com.example.pokemonservice.Domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Setter
@NoArgsConstructor
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "pokemon_id")
    private String pokemonId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name ="pokemon_number")
    private long pokemonNumber;
    @Column(name = "pokemon_name")
    private String pokemonName;
    @Column(name = "given_name")
    private String givenName;
    @Column(name="pokemon_level")
    private Integer pokemonLevel;
}