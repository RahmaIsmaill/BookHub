package com.example.kitabhub.controller;

import com.example.kitabhub.dto.BookRequestDto;
import com.example.kitabhub.dto.BookResponseDto;
import com.example.kitabhub.dto.UserResponseDto;
import com.example.kitabhub.exception.CustomValidationException;
import com.example.kitabhub.service.BookService;
import com.example.kitabhub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("bookApi/v1")
@RequiredArgsConstructor
@Tag(name = "BookHub APIs")
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @Operation(summary = "Get all books", description = "Fetches a paginated list of all books")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Books retrieved successfully")})
    @GetMapping("/books")
    public ResponseEntity<Page<BookResponseDto>> getAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getAllBooks(page, size));
    }


    @Operation(summary = "Get a book by ID", description = "Fetch a single book by its ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Book retrieved successfully"), @ApiResponse(responseCode = "404", description = "Book not found")})
    @GetMapping("/book/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }


    @Operation(summary = "Search books by title", description = "Search books using a title keyword")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Books retrieved successfully"), @ApiResponse(responseCode = "404", description = "Book not found")})
    @GetMapping("/books/search/title")
    public ResponseEntity<Page<BookResponseDto>> searchForBookByTitle(@RequestParam String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.searchByTitle(title, page, size));
    }

    @Operation(summary = "Search books by category", description = "Search books by their category")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Books retrieved successfully")})
    @GetMapping("/books/search/category")
    public ResponseEntity<Page<BookResponseDto>> searchForBookByCategory(@RequestParam String category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.searchByCategory(category, page, size));
    }


    @Operation(summary = "Get top liked books by category", description = "Fetch top liked books filtered by category")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Books retrieved successfully")})
    @GetMapping("/books/top-liked/category")
    public ResponseEntity<Page<BookResponseDto>> getTopLikedBooksByCategory(@RequestParam String category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getTopLikedBooksByCategory(category, page, size));
    }


    @Operation(summary = "Get top liked books", description = "Fetch top liked books across all categories")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Books retrieved successfully")})
    @GetMapping("/books/top-liked")
    public ResponseEntity<Page<BookResponseDto>> getTopLikedBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getTopLikedBooks(page, size));
    }


    @Operation(summary = "Create a new book", description = "Adds a new book (Admin only)")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Book created successfully"), @ApiResponse(responseCode = "403", description = "Forbidden: Only admins can perform this action")})
    @PostMapping("/book")
    public ResponseEntity<?> createBook(@ModelAttribute BookRequestDto bookRequestDto, @RequestParam Long userID) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(userID);
        if (adminCheck != null) return adminCheck;

        BookResponseDto savedBook = bookService.addBook(bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @Operation(summary = "Update a book", description = "Update a book by ID (Admin only)")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Book updated successfully"), @ApiResponse(responseCode = "403", description = "Forbidden: Only admins can perform this action")})
    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @ModelAttribute BookRequestDto bookRequestDto, @RequestParam Long userID) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(userID);
        if (adminCheck != null) return adminCheck;

        BookResponseDto updatedBook = bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete a book", description = "Delete a book by ID (Admin only)")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Book deleted successfully"), @ApiResponse(responseCode = "403", description = "Forbidden: Only admins can perform this action")})
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id, @RequestParam Long userID) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(userID);
        if (adminCheck != null) return adminCheck;

        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("message", "Book Deleted Successfully"));
    }

    @Operation(summary = "Get all books added by admin", description = "Fetch paginated books added by a specific admin")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Books retrieved successfully"), @ApiResponse(responseCode = "403", description = "Forbidden: Only admins can perform this action")})
    @GetMapping("books/admin/{adminID}")
    public ResponseEntity<?> getAllMyBooks(@PathVariable Long adminID, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        ResponseEntity<Map<String, Object>> adminCheck = checkAdminResponse(adminID);
        if (adminCheck != null) return adminCheck;

        return ResponseEntity.ok(bookService.getBooksAddedByAdmin(adminID, page, size));
    }

    @Operation(summary = "Like a book", description = "User likes a book by ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Book liked successfully")})
    @PostMapping("/books/like")
    public ResponseEntity<BookResponseDto> likeBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(bookService.likeBook(userId, bookId));
    }

    @Operation(summary = "Unlike a book", description = "User removes like from a book by ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Book unliked successfully")})
    @DeleteMapping("/books/like")
    public ResponseEntity<BookResponseDto> unlikeBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(bookService.unlikeBook(userId, bookId));
    }

    //    =======================================================================================
    private ResponseEntity<Map<String, Object>> checkAdminResponse(Long userId) {
        try {
            UserResponseDto user = userService.getUser(userId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errorMessages", Map.of("Error", "User not found")));
            }

            if (!"ADMIN".equalsIgnoreCase(String.valueOf(user.getRole()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("errorMessages", Map.of("Error", "Only admins can perform this action")));
            }

            return null;
        } catch (CustomValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errorMessages", Map.of("Error", "User not found")));
        }
    }
}