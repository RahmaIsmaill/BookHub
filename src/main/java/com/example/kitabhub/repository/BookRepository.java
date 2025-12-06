package com.example.kitabhub.repository;

import com.example.kitabhub.entity.Book;
import com.example.kitabhub.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where lower(b.title) like LOWER(CONCAT('%', :title, '%'))")
    Page<Book> searchByTitle(@Param("title") String  title , Pageable pageable);
    Page<Book> searchByCategory(Category category , Pageable pageable);
    @Query("select b from Book b where b.category=:category order by b.likesCount desc ")
    Page<Book> findByCategoryOrderByLikesDesc(Category category, Pageable pageable);

    @Query("select b from Book b  order by b.likesCount desc ")
    Page<Book> getTopLikedBooks( Pageable pageable);
}
