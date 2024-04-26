package com.example.demo.Service;

import com.example.demo.model.Rental;
import com.example.demo.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookService bookService;

    // Get all rentals
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Get rental by ID
    public Rental getRentalById(Long id) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isPresent()) {
            return rentalOptional.get();
        } else {
            throw new IllegalArgumentException("Rental not found with ID: " + id);
        }
    }

    // Rent a book
    public Rental rentBook(Rental rental) {
        Long bookId = rental.getBook().getId();
        if (bookService.getBookById(bookId).isAvailableForRent()) {
            rental.setRentalDate(LocalDate.now());
            return rentalRepository.save(rental);
        } else {
            throw new IllegalStateException("The book is already rented or unavailable.");
        }
    }

    // Return a rented book
    public Rental returnBook(Long id, Rental rental) {
        Rental existingRental = getRentalById(id);
        if (existingRental.getReturnDate() == null) {
            existingRental.setReturnDate(LocalDate.now());
            bookService.getBookById(existingRental.getBook().getId()).returnBook();
            return rentalRepository.save(existingRental);
        } else {
            throw new IllegalStateException("The book has already been returned.");
        }
    }

    public List<Rental> getOverdueRentals(int thresholdDays) {
        List<Rental> allRentals = rentalRepository.findAll();
        List<Rental> overdueRentals = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (Rental rental : allRentals) {
            LocalDate rentalDate = rental.getRentalDate(); // Assuming you have a method to get the rental date
            if (rentalDate != null) {
                LocalDate dueDate = rentalDate.plusDays(thresholdDays);
                if (currentDate.isAfter(dueDate)) {
                    overdueRentals.add(rental);
                }
            }
        }

        return overdueRentals;
    }



    // Delete a rental
    public void deleteRental(Long id) {
        if (rentalRepository.existsById(id)) {
            rentalRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Rental not found with ID: " + id);
        }
    }
}
