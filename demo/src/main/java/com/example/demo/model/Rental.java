package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String renterName;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    // Constructors
    public Rental() {
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Rental(Book book, String renterName, LocalDate rentalDate) {
        this.book = book;
        this.renterName = renterName;
        this.rentalDate = rentalDate;
    }

    // Getters and Setters
    // Other fields...

    // Method to rent a book
    public void rentBook(String renterName, LocalDate rentalDate) {
        if (book != null && book.isAvailableForRent()) {
            this.renterName = renterName;
            this.rentalDate = rentalDate;
            book.setRental(this);
        } else {
            throw new IllegalStateException("The book is already rented or unavailable.");
        }
    }

    // Method to return a book
    public void returnBook(LocalDate returnDate) {
        if (book != null && book.getRental() != null) {
            this.returnDate = returnDate;
            book.returnBook();
        } else {
            throw new IllegalStateException("The book is not rented.");
        }
    }

    // Method to check for overdue rentals
    public boolean isOverdue() {
        if (rentalDate != null && returnDate == null) {
            LocalDate currentDate = LocalDate.now();
            return rentalDate.plusDays(14).isBefore(currentDate); // Assuming rental period is 14 days
        }
        return false; // Rental is not overdue if either rental date or return date is null
    }

    public void setRentalDate(LocalDate now) {
        this.rentalDate=now;
    }

    public void setReturnDate(LocalDate now) {
        this.returnDate=now;
    }
    public Book getBook() {
        return this.book;
    }


    // Other methods...
}
