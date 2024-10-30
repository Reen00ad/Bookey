package com.example.bookybookey.Service;

import com.example.bookybookey.Api.ApiException;
import com.example.bookybookey.Model.Book;
import com.example.bookybookey.Model.ReadingList;
import com.example.bookybookey.Model.User;
import com.example.bookybookey.Repository.AuthRepository;
import com.example.bookybookey.Repository.BookRepository;
import com.example.bookybookey.Repository.ReadingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingListService {

    private final ReadingListRepository readingListRepository;
    private final AuthRepository authRepository;
    private final BookRepository bookRepository;

    //get all user reading list

    public List<ReadingList> getAllMyReadingLists(Integer userID) {
        User user = authRepository.findUserByUserId(userID);

        return readingListRepository.findAllByUser(user);

    }

    //get 1 reading list

    public ReadingList getMyReadingList(Integer userID, Integer readingListID) {
        User user= authRepository.findUserByUserId(userID);
        ReadingList readingList=readingListRepository.findReadingListByReadingListIdAndUser(readingListID,user);
        if(readingList==null)
            throw new ApiException("no reading list found");
        return readingList;
    }

    //create reading list

    public void createNewReadingList(Integer userId,ReadingList readingList) {
        User user = authRepository.findUserByUserId(userId);
        readingList.setUser(user);
        readingListRepository.save(readingList);

    }
    // update reading list check
    public void updateReadingListName(Integer userID, Integer readingListID, String newName) {
        User user = authRepository.findUserByUserId(userID);
        ReadingList readingList = readingListRepository.findReadingListByReadingListId(readingListID);

        if (user == null) {
            throw new ApiException("User not found");
        }
        if (readingList == null) {
            throw new ApiException("Reading list not found");
        }

        if (!readingList.getUser().getUserId().equals(userID)) {
            throw new ApiException("User does not have permission to update this reading list");
        }

        if (newName != null && !newName.trim().isEmpty()) {
            readingList.setName(newName);
            readingListRepository.save(readingList);
        } else {
            throw new ApiException("New name cannot be null or empty");
        }
    }

    // delete reading list check
    public void deleteReadingList(Integer userID, Integer readingListID) {
        User user = authRepository.findUserByUserId(userID);
        ReadingList readingList = readingListRepository.findReadingListByReadingListId(readingListID);

        if (user == null) {
            throw new ApiException("User not found");
        }
        if (readingList == null) {
            throw new ApiException("Reading list not found");
        }

        if (!readingList.getUser().getUserId().equals(userID)) {
            throw new ApiException("User does not have permission to delete this reading list");
        }

        readingList.getBooks().clear();

        readingListRepository.delete(readingList);
    }


    //add book to reading list

    public void addBookToReadingList(Integer userID, Integer bookId, Integer readingListID) {
        User user = authRepository.findUserByUserId(userID);
        ReadingList readingList=readingListRepository.findReadingListByReadingListId(readingListID);
        Book book=bookRepository.findBookByBookId(bookId);
        if(book==null) {
            throw new ApiException("book not found");
        }
        if(readingList==null) {
            throw new ApiException("readingList not found");
        }
        if(user==null) {
            throw new ApiException("user not found");
        }
        if(readingList.getBooks().contains(book)){
            throw new ApiException("book already exists");
        }

        readingList.getBooks().add(book);
        book.getReadingLists().add(readingList);
        readingListRepository.save(readingList);
        bookRepository.save(book);


    }

    //delete book from reading list --check

    public void deleteBookFromReadingList(Integer userID, Integer bookId, Integer readingListID) {
        User user = authRepository.findUserByUserId(userID);
        ReadingList readingList = readingListRepository.findReadingListByReadingListId(readingListID);
        Book book = bookRepository.findBookByBookId(bookId);

        if (user == null) {
            throw new ApiException("User not found");
        }
        if (readingList == null) {
            throw new ApiException("Reading list not found");
        }
        if (book == null) {
            throw new ApiException("Book not found");
        }

        if (!readingList.getBooks().contains(book)) {
            throw new ApiException("Book not found in reading list");
        }

        readingList.getBooks().remove(book);
        book.getReadingLists().remove(readingList);
        readingListRepository.save(readingList);
        bookRepository.save(book);
    }


}
