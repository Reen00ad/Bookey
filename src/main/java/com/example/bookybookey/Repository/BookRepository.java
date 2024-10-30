package com.example.bookybookey.Repository;

import com.example.bookybookey.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findBookByBookId(Integer bookId);
    Book findBookByISBN(String isbn);
}
