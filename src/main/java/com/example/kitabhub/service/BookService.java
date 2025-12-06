package com.example.kitabhub.service;

import com.example.kitabhub.dto.BookRequestDto;
import com.example.kitabhub.dto.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookResponseDto addBook(BookRequestDto dto);

    BookResponseDto updateBook(Long id, BookRequestDto dto);

    BookResponseDto getBook(Long id);

    Page<BookResponseDto> getAllBooks(int page, int size);

    void deleteBook(Long id);

    Page<BookResponseDto> searchByTitle(String title, int page, int size);

    Page<BookResponseDto> searchByCategory(String category, int page, int size);

    Page<BookResponseDto> getTopLikedBooksByCategory(String category, int page, int size);
    Page<BookResponseDto> getTopLikedBooks(int page, int size);
    Page<BookResponseDto> getBooksAddedByAdmin(Long adminId, int page, int size);
    BookResponseDto  likeBook(Long userId, Long bookId);
    BookResponseDto  unlikeBook(Long userId, Long bookId);



}


