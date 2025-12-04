package com.example.kitabhub.dto;

import com.example.kitabhub.enums.Category;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {

    private String title;
    private String description;
    private String author;
    private String coverUrl;
    private Category category;
    private double price;
    private int likesCount;

}