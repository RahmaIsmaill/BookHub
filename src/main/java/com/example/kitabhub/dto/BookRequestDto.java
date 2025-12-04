package com.example.kitabhub.dto;

import com.example.kitabhub.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Author is required")
    private String author;

    @URL(message = "Cover URL must be valid")
    private String coverUrl;

    @Positive(message = "Price must be positive")
    private double price;

    private Category category;

    private Long addedById; // id of the admin adding the book
}
