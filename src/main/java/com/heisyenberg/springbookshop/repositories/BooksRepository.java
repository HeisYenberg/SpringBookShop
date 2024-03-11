package com.heisyenberg.springbookshop.repositories;

import com.heisyenberg.springbookshop.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM books " +
            "WHERE title ILIKE '%' || :search || '%' " +
            "OR author ILIKE '%' || :search || '%' " +
            "OR genre ILIKE '%' || :search || '%'")
    Page<Book> findBySearch(@Param("search") String search, Pageable pageable);
}
