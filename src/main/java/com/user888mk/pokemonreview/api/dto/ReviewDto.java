package com.user888mk.pokemonreview.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Integer id;
    private String title;
    private String content;
    private int stars;
}
