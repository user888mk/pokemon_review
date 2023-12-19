package com.user888mk.pokemonreview.api.repository;

import com.user888mk.pokemonreview.api.models.Pokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void pokemonRepository_saveAll_ReturnSavedPokemon() {

        //arrange
        Pokemon pokemon = new Pokemon().builder()
                .name("pikachu")
                .type("electric")
                .build();
        //act
        Pokemon save = pokemonRepository.save(pokemon);
        //assert
        assertThat(save).isNotNull();
        assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    public void pokemonRepository_getAll_returnMoreThanOnePokemon() {
        Pokemon pokemon1 = new Pokemon().builder()
                .name("pikachu")
                .type("electric")
                .build();

        Pokemon pokemon2 = new Pokemon().builder()
                .name("pikachu")
                .type("electric")
                .build();

        Pokemon save1 = pokemonRepository.save(pokemon1);
        Pokemon save2 = pokemonRepository.save(pokemon2);
        List<Pokemon> all = pokemonRepository.findAll();

        assertThat(all.size()).isGreaterThan(1);
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    public void pokemonRepository_findById_returnPokemonWithSpecificId() {
        Pokemon pokemon1 = new Pokemon().builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemon = pokemonRepository.findById(pokemon1.getId()).get();
        assertThat(pokemon.getId()).isEqualTo(1);
    }

    @Test
    public void pokemonRepository_findByType_returnPokemonNotNull() {
        Pokemon pokemon1 = new Pokemon().builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemon = pokemonRepository.findByType(pokemon1.getType()).get();
        assertThat(pokemon.getId()).isEqualTo(1);
    }

    @Test
    public void pokemonRepository_updatePokemon_returnPokemonNotNull() {
        Pokemon pokemon = new Pokemon().builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonSaved = pokemonRepository.findById(pokemon.getId()).get();
        pokemonSaved.setType("electric");
        pokemonSaved.setName("Raichu");

        Pokemon savedUpdated = pokemonRepository.save(pokemonSaved);

        assertThat(savedUpdated).isNotNull();
        assertEquals(savedUpdated.getName(), "Raichu");
        assertEquals(savedUpdated.getType(), "electric");

    }

    @Test
    public void pokemonRepository_deleteById_returnPokemonIsEmpty() {
        Pokemon pokemon1 = new Pokemon().builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon1);

        pokemonRepository.deleteById(pokemon1.getId());
        Optional<Pokemon> pokemon = pokemonRepository.findById(pokemon1.getId());

        assertThat(pokemon).isEmpty();
        assertThat(pokemonRepository.findAll()).doesNotContain(pokemon1);

    }
}
