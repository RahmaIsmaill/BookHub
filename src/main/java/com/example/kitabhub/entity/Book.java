package com.example.kitabhub.entity;

import com.example.kitabhub.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "book")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "author is required")
    private String author;

    private int likesCount = 0;

    @Positive(message = "Price must be positive")
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "added_by")
//    @JsonIgnoreProperties({"password", "email"})
//    private User addedBy;

    // بدل byte[]، نخزن URL أو path
    @Column(name = "cover_image")
    private String coverImage;
}

