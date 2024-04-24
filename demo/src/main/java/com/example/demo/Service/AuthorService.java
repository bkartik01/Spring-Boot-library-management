package com.example.demo.Service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {


    private final AuthorRepository authorRepository;
    private final BookService bookService; // Inject BookService dependency

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    // Get all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Get author by ID
    public Author getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        } else {
            throw new IllegalArgumentException("Author not found with ID: " + id);
        }
    }

    // Add a new author
    public Author addAuthor(Author author) {
        // Save the author to the database
        author = authorRepository.save(author);

        // Check if the author's biography is provided
        if (author.getBiography() != null) {
            // Update the biography for each book by this author
            List<Book> booksByAuthor = author.getBooks();
            if (booksByAuthor != null) {
                for (Book book : booksByAuthor) {
                    // Update biography for the book's author
                    book.getAuthor().setBiography(author.getBiography());
                    // Update the book
                    bookService.updateBook(book.getId(), book);
                }
            }

            // Create a book for the author's autobiography
            Book autobiography = new Book();
            autobiography.setTitle("Autobiography of " + author.getName());
            autobiography.setPublicationYear(Year.now().getValue());
            autobiography.setAuthor(author);

            // Save the autobiography
            bookService.addBook(autobiography);
        }

        return author;
    }

    // Update an existing author
    public Author updateAuthor(Long id, Author author) {
        if (authorRepository.existsById(id)) {
            author.setId(id); // Ensure the correct ID is set
            return authorRepository.save(author);
        } else {
            throw new IllegalArgumentException("Author not found with ID: " + id);
        }
    }

    // Delete an author
    public void deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Author not found with ID: " + id);
        }
    }

    public Author getAuthorByName(String name) {
        if(authorRepository.findByName(name)!=null)
        {
            return  authorRepository.findByName(name);
        }
        else{
            return null;
        }
    }

    public Author save(Author author) {
        authorRepository.save(author);
        return author;
    }
}
