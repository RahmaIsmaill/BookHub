package com.example.kitabhub.dto;

import com.example.kitabhub.enums.Category;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {

    private Long id;
    private String title;
    private String description;
    private String author;
    private String coverImg;
    private Category category;
    private double price;
    private int likesCount;
    private boolean liked;
}
