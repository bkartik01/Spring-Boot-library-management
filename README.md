Book Management System API Documentation

This document outlines the API endpoints and their usage for a Book Management System.


Book Operations

Create a Book

Endpoint: POST /api/books

Payload:
json
Copy code
{
  "title": "Example Book",
  "author": {
    "name": "Author Name",
    "biography": "Author Biography"
  },
  "isbn": "978-3-16-148410-0",
  "publicationYear": 2022
}

Retrieve all Books

Endpoint: GET /api/books

Retrieve a Book by ID

Endpoint: GET /api/books/{id}

Update a Book

Endpoint: PUT /api/books/{id}

Payload:
json
Copy code
{
  "title": "Updated Book Title",
  "author": "Updated Author Name",
  "isbn": "978-3-16-148410-0",
  "publicationYear": 2022
}

Delete a Book

Endpoint: DELETE /api/books/{id}

Author Operations

Create an Author

Endpoint: POST /api/authors

Payload:
json
Copy code
{
  "name": "Author Name",
  "biography": "Author's Biography"
}

Retrieve all Authors

Endpoint: GET /api/authors

Retrieve an Author by ID

Endpoint: GET /api/authors/{id}

Update an Author

Endpoint: PUT /api/authors/{id}

Payload:

json
Copy code
{
  "name": "Updated Author Name",
  "biography": "Updated Biography"
}

Delete an Author

Endpoint: DELETE /api/authors/{id}

Rental Operations

Create a Rental Record

Endpoint: POST /api/rentals

Payload:

json
Copy code
{
  "bookId": 1,
  "renterName": "Renter Name"
}

Retrieve all Rental Records

Endpoint: GET /api/rentals

Retrieve a Rental Record by ID

Endpoint: GET /api/rentals/{id}

Additional Endpoints

Retrieve Books by Author

Endpoint: GET /api/books?author={authorName}

Retrieve Books Available for Rent

Endpoint: GET /api/books?available=true

Retrieve Books Currently Rented

Endpoint: GET /api/books?rented=true

Rent a Book

Endpoint: POST /api/rentals

Payload:

json

Copy code
{
  "bookId": 1,
  "renterName": "Renter Name"
}

Return a Book

Endpoint: PUT /api/rentals/{id}/return

Payload:

json
Copy code
{
  "returnDate": "2024-04-25"
}

Check for Overdue Rentals

Endpoint: GET /api/rentals/overdue

