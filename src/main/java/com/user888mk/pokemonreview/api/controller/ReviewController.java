package com.user888mk.pokemonreview.api.controller;

import com.user888mk.pokemonreview.api.dto.ReviewDto;
import com.user888mk.pokemonreview.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDto> create(@RequestBody ReviewDto reviewDto, @PathVariable(value = "pokemonId") int pokemonId) {
        ReviewDto review = reviewService.createReview(pokemonId, reviewDto);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{pokemonId}/review")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "pokemonId") int pokemonId) {
        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable int reviewId, @PathVariable int pokemonId) {
        return new ResponseEntity<>(reviewService.getReviewById(reviewId, pokemonId), HttpStatus.OK);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable int reviewId, @PathVariable int pokemonId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.updateReview(reviewId, pokemonId, reviewDto), HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable int reviewId, @PathVariable int pokemonId) {
        reviewService.deleteReview(reviewId, pokemonId);
        return new ResponseEntity<>("Review deleted ", HttpStatus.NO_CONTENT);
    }

}