package com.user888mk.pokemonreview.api.service;

import com.user888mk.pokemonreview.api.dto.PokemonDto;
import com.user888mk.pokemonreview.api.dto.PokemonResponse;
import com.user888mk.pokemonreview.api.models.Pokemon;
import com.user888mk.pokemonreview.api.repository.PokemonRepository;
import com.user888mk.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void pokemonService_createPokemon_returnPokemonDto() {

        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .type("electric")
                .build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        PokemonDto pokemonDto1 = pokemonService.create(pokemonDto);

        assertThat(pokemonDto1).isNotNull();
    }

    @Test
    public void pokemonService_findAll_returnResponseDto() {
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);
        PokemonResponse pokemonServiceAll = pokemonService.findAll(1, 10);
        Assertions.assertThat(pokemonServiceAll).isNotNull();
    }

    @Test
    public void pokemonService_findById_returnResponseDto() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        PokemonDto byId = pokemonService.findById(1);

        Assertions.assertThat(byId).isNotNull();
    }

    @Test
    public void pokemonService_updatePokemon_returnPokemonDto() {

        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .type("electric")
                .build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        PokemonDto pokemonDto1 = pokemonService.update(pokemonDto, 1);

        assertThat(pokemonDto1).isNotNull();
    }

    @Test
    public void pokemonService_delete_returnVoid() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

       assertAll(() -> pokemonService.delete(1));
    }
}
