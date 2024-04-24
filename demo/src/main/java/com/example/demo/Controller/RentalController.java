package com.example.demo.Controller;

import com.example.demo.Service.RentalService;
import com.example.demo.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Get all rentals
    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    // Get rental by ID
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id);
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    // Create a new rental
    @PostMapping
    public ResponseEntity<Rental> rentBook(@RequestBody Rental rental) {
        Rental rentedBook = rentalService.rentBook(rental);
        return new ResponseEntity<>(rentedBook, HttpStatus.CREATED);
    }

    // Return a rental
    @PutMapping("/{id}/return")
    public ResponseEntity<Rental> returnBook(@PathVariable Long id, @RequestBody Rental rental) {
        Rental returnedBook = rentalService.returnBook(id, rental);
        return new ResponseEntity<>(returnedBook, HttpStatus.OK);
    }

    // Delete a rental
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
