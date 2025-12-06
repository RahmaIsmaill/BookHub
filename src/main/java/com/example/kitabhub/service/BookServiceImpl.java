package com.example.kitabhub.service;


import com.example.kitabhub.dto.BookRequestDto;
import com.example.kitabhub.dto.BookResponseDto;
import com.example.kitabhub.entity.Book;
import com.example.kitabhub.entity.Like;
import com.example.kitabhub.entity.User;
import com.example.kitabhub.enums.Category;
import com.example.kitabhub.exception.CustomValidationException;
import com.example.kitabhub.repository.BookRepository;
import com.example.kitabhub.repository.LikeRepsitory;
import com.example.kitabhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LikeRepsitory likeRepsitory;


    private BookResponseDto convertToDto(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .price(book.getPrice())
                .category(book.getCategory())
                .likesCount(book.getLikesCount())
                .coverImg(book.getCoverImage())
                .build();
    }

    @Override
    public BookResponseDto addBook(BookRequestDto dto) {
        User user = userRepository.findById(dto.getAddedById())
                .orElseThrow(() -> new CustomValidationException(Map.of("Error", "User not found")));
        Category category;
        try {
            category = Category.valueOf(dto.getCategory().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CustomValidationException(Map.of("Error",
                    "Invalid Category. Allowed: SCIENCE, HISTORY, NOVEL, TECHNOLOGY, ART, BUSINESS"));
        }


        try {
            Book.BookBuilder builder = Book.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .author(dto.getAuthor())
                    .price(dto.getPrice())
                    .category(category)
                    .addedBy(user);


            if (dto.getCoverImage() != null && !dto.getCoverImage().isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + dto.getCoverImage().getOriginalFilename();
                Path uploadPath = Paths.get("uploads/" + fileName);
                Files.createDirectories(uploadPath.getParent());
                Files.copy(dto.getCoverImage().getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
                builder.coverImage("/uploads/" + fileName);
            }

            Book book = builder.build();
            bookRepository.save(book);
            return convertToDto(book);

        } catch (Exception e) {
            throw new CustomValidationException(Map.of("Error", "Failed to add book"));
        }
    }


    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto dto) {
       Book book = bookRepository.findById(id).orElseThrow(()-> new CustomValidationException(Map.of("Error", "Book not found")));

       Category category;
        try {
            category = Category.valueOf(dto.getCategory().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CustomValidationException(Map.of("Error",
                    "Invalid Category. Allowed: SCIENCE, HISTORY, NOVEL, TECHNOLOGY, ART, BUSINESS"));
        }


        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setCategory(category);

        if (dto.getCoverImage() != null && !dto.getCoverImage().isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + dto.getCoverImage().getOriginalFilename();
                Path uploadDir = Paths.get("uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(dto.getCoverImage().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                book.setCoverImage("/uploads/" + fileName);
            } catch (IOException e) {
                throw new CustomValidationException(Map.of("Error", "Failed to update cover image"));
            }
        }

        bookRepository.save(book);
        return convertToDto(book);
    }

    @Override
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException(Map.of("Error","Book not found with id: " + id)));
        return convertToDto(book);
    }

    @Override
    public Page<BookResponseDto> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> AllBooks = bookRepository.findAll(pageable);
        Page<BookResponseDto> dtoPage = AllBooks.map(this::convertToDto);
        return dtoPage;
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new CustomValidationException(Map.of("Error","Book not found with id: " + id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResponseDto> searchByTitle(String title, int page ,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.searchByTitle(title, pageable);
        Page<BookResponseDto> dtoPage = booksPage.map(this::convertToDto);
        return dtoPage;
    }

    @Override
    public Page<BookResponseDto> searchByCategory(String categoryName, int page ,int size) {
        Category category;
        try {
            category = Category.valueOf(categoryName.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CustomValidationException(Map.of("Error",
                    "Invalid Category. Allowed: SCIENCE, HISTORY, NOVEL, TECHNOLOGY, ART, BUSINESS"));
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.searchByCategory(category, pageable);

        if(booksPage.getTotalElements() == 0) {
            throw new CustomValidationException(Map.of("Error","No books founded"));
        }
        Page<BookResponseDto> dtoPage = booksPage.map(this::convertToDto);
        return dtoPage;
    }

    @Override
    public Page<BookResponseDto> getTopLikedBooksByCategory(String categoryName, int page, int size) {
        Category category;
        try {
            category = Category.valueOf(categoryName.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CustomValidationException(Map.of("Error",
                    "Invalid Category. Allowed: SCIENCE, HISTORY, NOVEL, TECHNOLOGY, ART, BUSINESS"));
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.findByCategoryOrderByLikesDesc(category, pageable);

        if(booksPage.getTotalElements() == 0) {
            throw new CustomValidationException(Map.of("Error","No books founded"));
        }
        Page<BookResponseDto> dtoPage = booksPage.map(this::convertToDto);
        return dtoPage;
    }

    @Override
    public Page<BookResponseDto> getTopLikedBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.getTopLikedBooks(pageable);
        return booksPage.map(this::convertToDto);

    }

    @Override
    public Page<BookResponseDto> getBooksAddedByAdmin(Long adminId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Book> books = bookRepository.findByAddedById(adminId, pageable);

        return books.map(this::convertToDto);
    }

    @Override
    public BookResponseDto likeBook(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomValidationException(Map.of("Error", "User not found")));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomValidationException(Map.of("Error", "Book not found")));

        if (likeRepsitory.existsByUserIdAndBookId(userId, bookId)) {
            throw new CustomValidationException(Map.of("Error", "Book already liked"));
        }

        Like like = Like.builder()
                .book(book)
                .user(user)
                .build();

        likeRepsitory.save(like);

        book.setLikesCount(book.getLikesCount() + 1);
        bookRepository.save(book);

        return convertToDto(book);
    }

    @Override
    public BookResponseDto unlikeBook(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomValidationException(Map.of("Error", "User not found")));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomValidationException(Map.of("Error", "Book not found")));

        int deleted = book.getLikesCount();

        if (deleted == 0) {
            throw new CustomValidationException(Map.of("Error", "You Can't Unlike this "));
        }

//        likeRepsitory.deleteByUserIdAndBookId(userId, bookId);

        book.setLikesCount(Math.max(book.getLikesCount() - 1, 0));
        bookRepository.save(book);


        return convertToDto(book);
    }



}
