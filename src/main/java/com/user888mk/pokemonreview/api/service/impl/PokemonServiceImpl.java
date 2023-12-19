package com.user888mk.pokemonreview.api.service.impl;

import com.user888mk.pokemonreview.api.dto.PokemonDto;
import com.user888mk.pokemonreview.api.dto.PokemonResponse;
import com.user888mk.pokemonreview.api.models.Pokemon;
import com.user888mk.pokemonreview.api.repository.PokemonRepository;
import com.user888mk.pokemonreview.api.service.PokemonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    public final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto create(PokemonDto pokemonDtoCommand) {

        //bierzemy pokemona z API, mapDTOtoEntity
        Pokemon pokemon = new Pokemon();
        pokemon.setId(pokemonDtoCommand.getId());
        pokemon.setName(pokemonDtoCommand.getName());
        pokemon.setType(pokemonDtoCommand.getType());
        //zapisujemy zmapowanego pokemonDTO na pokemon i zaposujemy w repo
        Pokemon newPokemon = pokemonRepository.save(pokemon);

        //mapEntityToDto
        PokemonDto pokemonDtoAfter = new PokemonDto();
        pokemonDtoAfter.setId(pokemon.getId());
        pokemonDtoAfter.setName(pokemon.getName());
        pokemonDtoAfter.setType(pokemon.getType());

        return pokemonDtoAfter;
    }

    @Override
    public PokemonResponse findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> allPokemons = pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemon = allPokemons.getContent();
        List<PokemonDto> content = listOfPokemon.stream().map(pokemon -> mapEntityToDto(pokemon)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(allPokemons.getNumber());
        pokemonResponse.setPageSize(allPokemons.getSize());
        pokemonResponse.setTotalElements(allPokemons.getTotalElements());
        pokemonResponse.setTotalPages(allPokemons.getTotalPages());
        pokemonResponse.setLast(allPokemons.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDto findById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(MessageFormat.format("Not found entity with id ", id)));
        return mapEntityToDto(pokemon);

    }

    @Override
    public PokemonDto update(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Not found entity with id ", id)));

        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        pokemonRepository.save(pokemon);

        return mapEntityToDto(pokemon);
    }

    @Override
    public void delete(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Not found entity with id ", id)));
        pokemonRepository.delete(pokemon);
    }

    private PokemonDto mapEntityToDto(Pokemon pokemon) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setType(pokemon.getType());
        pokemonDto.setName(pokemon.getName());

        return pokemonDto;
    }

    private Pokemon mapDtoToEntity(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(pokemonDto.getId());
        pokemon.setType(pokemonDto.getType());
        pokemon.setName(pokemonDto.getName());

        return pokemon;
    }
}
