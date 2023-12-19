package com.user888mk.pokemonreview.api.service;

import com.user888mk.pokemonreview.api.dto.PokemonDto;
import com.user888mk.pokemonreview.api.dto.ReviewDto;
import com.user888mk.pokemonreview.api.models.Pokemon;
import com.user888mk.pokemonreview.api.models.Review;
import com.user888mk.pokemonreview.api.repository.PokemonRepository;
import com.user888mk.pokemonreview.api.repository.ReviewRepository;
import com.user888mk.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;
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
    public void reviewService_createReview_returnReviewDto() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto review1 = reviewService.createReview(pokemon.getId(), reviewDto);

        Assertions.assertThat(review1).isNotNull();
    }

    @Test
    public void reviewService_getReviewsByPokemonId_returnReviewDto() {
        when(reviewRepository.findByPokemonId(pokemon.getId())).thenReturn(Arrays.asList(review));

        List<ReviewDto> reviewsByPokemonId = reviewService.getReviewsByPokemonId(pokemon.getId());

        Assertions.assertThat(reviewsByPokemonId).isNotNull();
    }

    @Test
    public void reviewService_getReviewById_returnReviewDto() {
        int reviewId = 1;
        int pokemonId = 1;

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ReviewDto reviewById = reviewService.getReviewById(reviewId, pokemonId);

        Assertions.assertThat(reviewById).isNotNull();
    }

    @Test
    public void reviewService_update_returnReviewDto() {
        int reviewId = 2;
        int pokemonId = 11;

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(review)).thenReturn(review);

        ReviewDto updateReview = reviewService.updateReview(reviewId, pokemonId, reviewDto);

        Assertions.assertThat(updateReview).isNotNull();
    }

    @Test
    public void setReviewService_delete_returnVoid() {
        int reviewId = 2;
        int pokemonId = 11;

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertAll(() -> reviewService.deleteReview(pokemonId,reviewId));

    }
}