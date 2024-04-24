package com.example.demo.repository;

import com.example.demo.model.Book;
import com.example.demo.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByBook(Book book);
    List<Rental> findByRentalDateBeforeAndReturnDateIsNull(LocalDate date);

    List<Rental> findByBookId(Long bookId);
}