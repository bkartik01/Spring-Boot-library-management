package com.example.demo.Controller;

import com.example.demo.Service.AuthorService;
import com.example.demo.Service.BookService;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Create a new book
    // Create a new book
    // Create a new book
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        // Check if the author of the book is null
        if (book.getAuthor() == null) {
            // Handle the case where author is null (you can throw an exception or return an error response)
            return new ResponseEntity<>("Author cannot be null", HttpStatus.BAD_REQUEST);
        }

        // Check if the author of the book already exists in the database
        Author existingAuthor = authorService.getAuthorByName(book.getAuthor().getName());

        if (existingAuthor != null) {
            // If author exists, set the existing author to the book
            book.setAuthor(existingAuthor);
        } else {
            // If author doesn't exist, save the author first
            Author savedAuthor = authorService.save(book.getAuthor());
            book.setAuthor(savedAuthor);
        }

        Book createdBook = bookService.addBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }



    // Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
