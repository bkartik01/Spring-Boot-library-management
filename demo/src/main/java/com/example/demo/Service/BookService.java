package com.example.demo.Service;

import com.example.demo.model.Book;
import com.example.demo.model.Rental;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Book getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }
    }

    // Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Update an existing book
    public Book updateBook(Long id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id); // Ensure the correct ID is set
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }
    }

    // Delete a book
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }
    }

    @Autowired
    private RentalService rentalService;

    public Rental rentBook(Long bookId, String renterName) {
        Book book = getBookById(bookId);
        if (book.isAvailableForRent()) {
            Rental rental = new Rental(book, renterName, LocalDate.now());
            return rentalService.rentBook(rental);
        } else {
            throw new IllegalStateException("The book is already rented or unavailable.");
        }
    }

}
