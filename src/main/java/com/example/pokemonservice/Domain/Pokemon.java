package com.example.pokemonservice.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pokemons")
public class Pokemon {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "pokemon_id")
    private String pokemonId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
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
