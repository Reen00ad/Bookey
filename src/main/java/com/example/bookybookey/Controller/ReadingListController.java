package com.example.bookybookey.Controller;

import com.example.bookybookey.Api.ApiResponse;
import com.example.bookybookey.Model.Book;
import com.example.bookybookey.Model.ReadingList;
import com.example.bookybookey.Model.User;
import com.example.bookybookey.Service.ReadingListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/readingList")
public class ReadingListController {
    private final ReadingListService readingListService;


    @GetMapping("/getAllMyReadingLists")
    public ResponseEntity getAllMyReadingLists(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(readingListService.getAllMyReadingLists(user.getUserId()));
    }

    @GetMapping("/getReadingList/{readingListId}")
    public ResponseEntity getMyReadingList(@AuthenticationPrincipal User user,@PathVariable Integer readingListId) {
        return ResponseEntity.ok(readingListService.getMyReadingList(user.getUserId(), readingListId));
    }

    @PostMapping("/create")
    public ResponseEntity createNewReadingList(@AuthenticationPrincipal User user, @RequestBody @Valid ReadingList readingList) {
        readingListService.createNewReadingList(user.getUserId(), readingList);
        return ResponseEntity.ok(new ApiResponse("readingList created successfully"));
    }

    @PutMapping("/updateReadingListName/{readingListId}/{name}")
    public ResponseEntity updateReadingListName(@AuthenticationPrincipal User user, @PathVariable  Integer readingListId,@PathVariable String name) {
        readingListService.updateReadingListName(user.getUserId(), readingListId, name);
        return ResponseEntity.ok(new ApiResponse("readingList updated successfully"));
    }

    @DeleteMapping("/deleteReadingList/{readingListId}")
    public ResponseEntity deleteReadingList(@AuthenticationPrincipal User user, @PathVariable Integer readingListId) {
        readingListService.deleteReadingList(user.getUserId(), readingListId);
        return ResponseEntity.ok(new ApiResponse("readingList deleted successfully"));
    }

    @PostMapping("/addBookToReadingList/{bookId}/{readingListId}")
    public ResponseEntity addBookToReadingList(@AuthenticationPrincipal User user,@PathVariable Integer bookId, @PathVariable Integer readingListId) {
        readingListService.addBookToReadingList(user.getUserId(),bookId, readingListId);
        return ResponseEntity.ok(new ApiResponse("Book added to readingList successfully"));
    }

    @PutMapping("/deleteBookFromReadingList/{bookId}/{readingListId}")
    public ResponseEntity deleteBookFromReadingList(@AuthenticationPrincipal User user,@PathVariable Integer bookId,@PathVariable Integer readingListId) {
        readingListService.deleteBookFromReadingList(user.getUserId(),bookId,readingListId);
        return ResponseEntity.ok(new ApiResponse("Book deleted from readingList successfully"));
    }
}
