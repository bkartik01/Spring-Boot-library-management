package com.example.demo.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.demo.model.Book;
import com.example.demo.model.Rental;
import com.example.demo.repository.BookRepository;
import com.example.demo.Service.BookService;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testRentBook() {
        // Mock data
        Book book = new Book();
        book.setId(1L);
        book.setAvailableForRent(true);
        Rental rental = new Rental();
        rental.setRentalDate(LocalDate.now());

        // Mock repository
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Test
        Rental rentedBook = bookService.rentBook(1L, "John Doe");
        assertEquals(rental.getRentalDate(), rentedBook.getRentalDate());
        assertEquals("John Doe", rentedBook.getRenterName());
    }

}
