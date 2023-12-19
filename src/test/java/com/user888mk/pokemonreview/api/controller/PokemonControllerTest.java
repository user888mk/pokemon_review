package com.user888mk.pokemonreview.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user888mk.pokemonreview.api.dto.PokemonDto;
import com.user888mk.pokemonreview.api.dto.ReviewDto;
import com.user888mk.pokemonreview.api.models.Pokemon;
import com.user888mk.pokemonreview.api.models.Review;
import com.user888mk.pokemonreview.api.service.PokemonService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PokemonController.class)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @Autowired
    private ObjectMapper objectMapper;
    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").build();
        pokemonDto = PokemonDto.builder().id(1).name("Pikachu").type("electric").build();
        review = Review.builder().title("test").content("test content").stars(3).pokemon(pokemon).build();
        reviewDto = ReviewDto.builder().title("testDto").content("testDto content").stars(3).build();
    }

    @Test
    public void pokemonController_createPokemon_returnCreated() throws Exception {
        given(pokemonService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void pokemonController_getAllPokemon_returnResponseDto(){

    }
}
