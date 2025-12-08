package com.example.kitabhub.controller;

import com.example.kitabhub.dto.BookRequestDto;
import com.example.kitabhub.dto.BookResponseDto;
import com.example.kitabhub.dto.UserResponseDto;
import com.example.kitabhub.exception.CustomValidationException;
import com.example.kitabhub.service.BookService;
import com.example.kitabhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("bookApi/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final UserService userService;


    @GetMapping("/books")
    public ResponseEntity<Page<BookResponseDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookService.getAllBooks(page, size));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @GetMapping("/books/search/title")
    public ResponseEntity<Page<BookResponseDto>> searchForBookByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookService.searchByTitle(title, page, size));
    }

    @GetMapping("/books/search/category")
    public ResponseEntity<Page<BookResponseDto>> searchForBookByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookService.searchByCategory(category, page, size));
    }


    @GetMapping("/books/top-liked/category")
    public ResponseEntity<Page<BookResponseDto>> getTopLikedBooksByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookService.getTopLikedBooksByCategory(category, page, size));
    }


    @GetMapping("/books/top-liked")
    public ResponseEntity<Page<BookResponseDto>> getTopLikedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookService.getTopLikedBooks(page, size));
    }


    private ResponseEntity<Map<String, Object>> checkAdminResponse(Long userId) {
        try {
            UserResponseDto user = userService.getUser(userId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("errorMessages", Map.of("Error", "User not found")));
            }

            if (!"ADMIN".equalsIgnoreCase(String.valueOf(user.getRole()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("errorMessages", Map.of("Error", "Only admins can perform this action")));
            }

            return null;
        } catch (CustomValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("errorMessages", Map.of("Error", "User not found")));
        }
    }

    @PostMapping("/book")
    public ResponseEntity<?> createBook(
            @ModelAttribute BookRequestDto bookRequestDto,
            @RequestParam Long userID
    ) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(userID);
        if (adminCheck != null) return adminCheck;

        BookResponseDto savedBook = bookService.addBook(bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @ModelAttribute BookRequestDto bookRequestDto,
            @RequestParam Long userID
    ) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(userID);
        if (adminCheck != null) return adminCheck;

        BookResponseDto updatedBook = bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(
            @PathVariable Long id,
            @RequestParam Long userID
    ) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(userID);
        if (adminCheck != null) return adminCheck;

        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("message", "Book Deleted Successfully"));
    }

    @GetMapping("books/admin/{adminID}")
    public ResponseEntity<?> getAllMyBooks(
            @PathVariable Long adminID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(adminID);
        if (adminCheck != null) return adminCheck;

        return ResponseEntity.ok(bookService.getBooksAddedByAdmin(adminID, page, size));
    }

    @PostMapping("/books/like")
    public ResponseEntity<BookResponseDto> likeBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(bookService.likeBook(userId, bookId));
    }

    @DeleteMapping("/books/like")
    public ResponseEntity<BookResponseDto> unlikeBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(bookService.unlikeBook(userId, bookId));
    }
}