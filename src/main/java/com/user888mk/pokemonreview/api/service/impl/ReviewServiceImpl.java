package com.user888mk.pokemonreview.api.service.impl;

import com.user888mk.pokemonreview.api.dto.ReviewDto;
import com.user888mk.pokemonreview.api.exception.PokemonNotFoundException;
import com.user888mk.pokemonreview.api.exception.ReviewNotFoundException;
import com.user888mk.pokemonreview.api.models.Pokemon;
import com.user888mk.pokemonreview.api.models.Review;
import com.user888mk.pokemonreview.api.repository.PokemonRepository;
import com.user888mk.pokemonreview.api.repository.ReviewRepository;
import com.user888mk.pokemonreview.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException(("Pokemon with associated review")));

        Review review = reviewDtoToEntity(reviewDto);
        review.setPokemon(pokemon);
        reviewRepository.save(review);

        return reviewEntityToDto(review);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {
        List<Review> byPokemonId = reviewRepository.findByPokemonId(id);
        return byPokemonId.stream().map(review -> reviewEntityToDto(review)).collect(Collectors.toList());

    }

//    public List<ReviewDto> findAll() {
//        List<ReviewDto> contentDto = reviewRepository.findAll().stream()
//                .map(review -> reviewEntityToDto(review)).collect(Collectors.toList());
//
//        return contentDto;
//    }


    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException(("Pokemon with associated review")));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(MessageFormat.format("Review with associate pokemon not found ", reviewId)));

        if (!review.getPokemon().getId().equals(pokemon.getId())) {
            throw new ReviewNotFoundException("This review is does not  belong to a pokemon");
        }

        reviewRepository.delete(review);
    }

    @Override
    public ReviewDto getReviewById(int pokemonId, int reviewId) {

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException(("Pokemon with associated review")));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(MessageFormat.format("Review with associate pokemon not found ", reviewId)));

        if (!review.getPokemon().getId().equals(pokemon.getId())) {
            throw new ReviewNotFoundException("This review is does not  belong to a pokemon");
        }
        return reviewEntityToDto(review);
    }

    @Override
    public ReviewDto updateReview(int reviewId, int pokemonId, ReviewDto reviewDto) {

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException(("Pokemon with associated review")));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(MessageFormat.format("Review with associate pokemon not found ", reviewId)));

        if (!review.getPokemon().getId().equals(pokemon.getId())) {
            throw new ReviewNotFoundException("This review is does not  belond to a pokemon");
        }

        Review reviewEntity = reviewDtoToEntity(reviewDto);

        reviewEntity.setTitle(reviewDto.getTitle());
        reviewEntity.setContent(reviewDto.getContent());
        reviewEntity.setStars(reviewDto.getStars());

        reviewRepository.save(reviewEntity);

        return reviewEntityToDto(reviewEntity);
    }

    private ReviewDto reviewEntityToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(review.getId());
        reviewDto.setContent(review.getContent());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setStars(review.getStars());

        return reviewDto;
    }

    private Review reviewDtoToEntity(ReviewDto reviewDto) {
        Review review = new Review();

        review.setId(reviewDto.getId());
        review.setContent(reviewDto.getContent());
        review.setTitle(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());

        return review;
    }
}
