package com.user888mk.pokemonreview.api.dto;

import lombok.Data;

@Data
public class ReviewDto {

    private Integer id;
    private String title;
    private String content;
    private int stars;
}
