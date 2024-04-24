package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Book {
    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private int publicationYear;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @OneToOne(mappedBy = "book")
    private Rental rental;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    // Additional methods
    public boolean isAvailableForRent() {
        return rental == null; // Book is available if it's not currently rented
    }
    public void returnBook() {
        this.rental = null; // Set rental to null to mark the book as available
    }
    // Check if the book is overdue
    public boolean isOverdue() {
        if (rental != null && rental.getReturnDate() != null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate returnDate = rental.getReturnDate();
            return currentDate.isAfter(returnDate.plusDays(14)); // Assuming rental period is 14 days
        }
        return false; // Book is not overdue if it's not rented or doesn't have a return date
    }


}
