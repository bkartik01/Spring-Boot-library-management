package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Custom query to find books by author ID
    List<Book> findByAuthorId(Long authorId);

    // Custom query using JPQL to find available books for rent
    @Query("SELECT b FROM Book b WHERE b.rental IS NULL")
    List<Book> findAvailableBooksForRent();

    // Custom query using native SQL to find books currently rented
    @Query(value = "SELECT * FROM books b JOIN rentals r ON b.id = r.book_id WHERE r.return_date IS NULL", nativeQuery = true)
    List<Book> findCurrentlyRentedBooks();
}
