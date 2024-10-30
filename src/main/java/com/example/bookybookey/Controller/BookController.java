package com.example.bookybookey.Controller;

import com.example.bookybookey.Api.ApiException;
import com.example.bookybookey.Api.ApiResponse;
import com.example.bookybookey.Model.Book;
import com.example.bookybookey.Service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/get")
    public ResponseEntity getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());

    }

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody @Valid Book book) {
        bookService.addBook(book);
        return ResponseEntity.ok(new ApiResponse("Book added successfully"));
    }
    
    @PostMapping("/addd/{isbn}")
    public ResponseEntity addBookByISBN(@PathVariable String isbn) {
        Book book = bookService.addBookByISBN(isbn);
        return ResponseEntity.ok(new ApiResponse("Book added successfully: " + book.getBookTitle()));
    }


    /*
    @PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@PathVariable Integer bookId ,@RequestBody @Valid Book book) {
        bookService.updateBook(bookId,book);
        return ResponseEntity.ok(new ApiResponse("Book updated successfully"));
    }*/

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(new ApiResponse("Book deleted successfully"));
    }


}
