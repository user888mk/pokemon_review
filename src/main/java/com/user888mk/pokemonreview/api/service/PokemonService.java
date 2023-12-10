package com.user888mk.pokemonreview.api.service;

import com.user888mk.pokemonreview.api.dto.PokemonDto;
import com.user888mk.pokemonreview.api.dto.PokemonResponse;

import java.util.List;

public interface PokemonService {

    PokemonDto create(PokemonDto pokemonDto);

    PokemonResponse findAll(int pageNo, int pageSize);

    PokemonDto findById(int id);

    PokemonDto update(PokemonDto pokemonDto, int id);

    void delete(int id);
}
