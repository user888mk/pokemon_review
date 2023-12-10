package com.user888mk.pokemonreview.api.exception;

public class ReviewNotFoundException extends RuntimeException {
    private final static long serialCerisionUID = 2;

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
