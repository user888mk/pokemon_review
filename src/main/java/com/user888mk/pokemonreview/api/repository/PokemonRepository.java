package com.user888mk.pokemonreview.api.repository;

import com.user888mk.pokemonreview.api.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}
