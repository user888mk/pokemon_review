package com.user888mk.pokemonreview.api.controller;

import com.user888mk.pokemonreview.api.dto.PokemonDto;
import com.user888mk.pokemonreview.api.dto.PokemonResponse;
import com.user888mk.pokemonreview.api.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/pokemon")
    public ResponseEntity<PokemonResponse> getAllPokemon(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {

        return new ResponseEntity<>(pokemonService.findAll(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/pokemon/{id}")
    public ResponseEntity<PokemonDto> pokemonDetail(@PathVariable int id) {
        return new ResponseEntity<>(pokemonService.findById(id), HttpStatus.OK);
    }

    @PostMapping("pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
        return new ResponseEntity<>(pokemonService.create(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("/pokemon/{id}/update")
    public ResponseEntity<PokemonDto> update(@RequestBody PokemonDto pokemonDto, @PathVariable("id") int id) {
        return new ResponseEntity<>(pokemonService.update(pokemonDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{id}/delete")
    public ResponseEntity<String> update(@PathVariable("id") int id) {
        pokemonService.delete(id);
        return new ResponseEntity<>("Pokemon deleted ", HttpStatus.OK);
    }
}
