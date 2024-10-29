package com.example.bookybookey.Service;

import com.example.bookybookey.Api.ApiException;
import com.example.bookybookey.Model.Book;
import com.example.bookybookey.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private  String API_URL ;//= "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";

    // add book using ISBN
    public Book addBookByISBN(String isbn) {
        API_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";

        // Create a new RestTemplate instance for making API calls
        RestTemplate restTemplate = new RestTemplate();

        // Replace the placeholder in the API URL with the actual ISBN provided
        String url = API_URL.replace("{isbn}", isbn);

        // Make a GET request to the API and store the response in a Map
        Map<String, Object> response = restTemplate.getForObject(url, HashMap.class);

        // Check if the response is null or does not contain book data
        if (response == null || !response.containsKey("ISBN:" + isbn)) {
            throw new RuntimeException("Book not found");
        }

        // Retrieve the book data from the response using the ISBN key
        Map<String, Object> bookData = (Map<String, Object>) response.get("ISBN:" + isbn);

        Book book = new Book();

        book.setBookTitle((String) bookData.get("title"));

        book.setAuthorName(((List<Map<String, String>>) bookData.get("authors")).get(0).get("name"));

        book.setNoOfPages((Integer) bookData.get("number_of_pages"));

        book.setISBN(isbn);

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public void addBook(Book book) {
        bookRepository.save(book);

    }


    /*
    public void updateBook( Integer bookId, Book book){
        Book book1=bookRepository.findBookByBookId(bookId);

        book1.setBookTitle(book.getBookTitle());
        book1.setNoOfPages(book.getNoOfPages());
        //book1.setISBN(book.getISBN());
        book1.setAuthorName(book.getAuthorName());

        bookRepository.save(book1);
    }*/

    //delete book
    public void deleteBook(Integer bookId) {
        Book book = bookRepository.findBookByBookId(bookId);
        if (book == null) {
            throw new ApiException("Book not found");
        }
        bookRepository.delete(book);
    }



}
